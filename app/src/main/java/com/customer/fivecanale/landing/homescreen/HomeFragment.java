package com.customer.fivecanale.landing.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.home.CategoryData;
import com.customer.domain.model.home.ListData;
import com.customer.fivecanale.allbrands.AllBrandsActivity;
import com.customer.fivecanale.allcategory.AllCategoryActivity;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.category.CategoryViewMoreActivity;
import com.customer.fivecanale.landing.homescreen.adapters.CategoriesAdapter.CategoryOnClickListener;
import com.customer.fivecanale.landing.homescreen.adapters.HomeAdapter;
import com.customer.fivecanale.landing.homescreen.adapters.HomeSubCatAdapter.SubCatClickListener;
import com.customer.fivecanale.landing.searchscreen.SearchActivity;
import com.customer.fivecanale.notifications.NotificationsActivity;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.recentlyviewed.RecentlyViewedProductsActivity;
import com.customer.fivecanale.trending.TrendingProductsActivity;
import com.customer.fivecanale.uiutil.AnimUtil;
import com.customer.fivecanale.uiutil.barcodescanning.BarCodePreviewActivity;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.customer.fivecanale.util.EcomConstants.BRAND_NAME;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_ID;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.HEADER;
import static com.customer.fivecanale.util.EcomConstants.ITEM_CACHE_SIZE;
import static com.customer.fivecanale.util.EcomConstants.NOTIFICATION_COUNT;
import static com.customer.fivecanale.util.EcomConstants.OFFER_ID_VAL;
import static com.customer.fivecanale.util.EcomConstants.OFFER_NAME;
import static com.customer.fivecanale.util.EcomConstants.OFFER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.PREFETCH_COUNT;
import static com.customer.fivecanale.util.EcomConstants.SUB_CAT;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CAT;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

/*
 * Purpose – This class Holds All the Ui Items which will be displayed in HomePage.
 * @author 3Embed
 * Created on Nov 05, 2019
 * Modified on
 */
public class HomeFragment extends DaggerFragment implements CategoryOnClickListener,
        HomeAdapter.HomeAdapterClickListener, SubCatClickListener {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private HomeViewModel mHomeViewModel;
  private HomeAdapter mHomeAdapter;
  private ArrayList<ListData> mHomeData = new ArrayList<>();
  private FragmentHomeBinding mBinding;
  private LinearLayoutManager mLinearLayoutManager;

  @Inject
  public HomeFragment() {
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    View view = mBinding.getRoot();
    showLoading();
    initialize();
    subscribeHomeData();
    subscribeNotificationCountData();
    pagination();
    return view;
  }

  /**
   * Does Initialization of basic activity resources
   */
  private void initialize() {
    mLinearLayoutManager = new LinearLayoutManager(getContext());
    mBinding.searchHeader.etHomeSearch.setFocusableInTouchMode(false);
    mBinding.searchHeader.etHomeSearch.setCursorVisible(false);
    mBinding.searchHeader.etHomeSearch.setClickable(true);
    mBinding.searchHeader.etHomeSearch.setFocusable(true);
    NOTIFICATION_COUNT = mUserInfoHandler.getNotificationCount();
    setNotificationCount(NOTIFICATION_COUNT);
    mBinding.searchHeader.ivSearchBarCode.setOnClickListener(
            view -> startActivity(new Intent(getActivity(), BarCodePreviewActivity.class)));
    mBinding.searchHeader.ivSearchNotification.setOnClickListener(view -> {
      if (mUserInfoHandler.isUserLoggedIn()) {
        mUserInfoHandler.setNotificationCount(ZERO);
        NOTIFICATION_COUNT = mUserInfoHandler.getNotificationCount();
        startActivity(new Intent(getActivity(), NotificationsActivity.class));
        mBinding.searchHeader.tvNotificationCount.setVisibility(View.GONE);
      } else {
        startBoardingAct();
      }
    });
    mBinding.searchHeader.etHomeSearch.setOnClickListener(
            view -> startActivity(new Intent(getActivity(), SearchActivity.class)));
    mBinding.loadMoreProgress.setVisibility(View.GONE);
    mHomeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HomeViewModel.class);
    mBinding.setViewModel(mHomeViewModel);
    mHomeAdapter = new HomeAdapter(mHomeData, this);
    mBinding.rvHome.setAdapter(mHomeAdapter);
    mLinearLayoutManager.setSmoothScrollbarEnabled(true);
    mLinearLayoutManager.setItemPrefetchEnabled(true);
    mLinearLayoutManager.setInitialPrefetchItemCount(PREFETCH_COUNT);
    mBinding.rvHome.setHasFixedSize(true);
    mBinding.rvHome.setLayoutManager(mLinearLayoutManager);
    mBinding.rvHome.setItemViewCacheSize(ITEM_CACHE_SIZE);
    mBinding.rvHome.setDrawingCacheEnabled(true);
    mBinding.rvHome.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    mHomeViewModel.callHomeApi();
    mHomeViewModel.onNotificationCountUpdate();
    mBinding.srlHomeReload.setOnRefreshListener(() -> {
      mHomeViewModel.refreshHomePage();
      showLoading();
      mBinding.srlHomeReload.setRefreshing(false);
    });
  }

  /**
   * This method is using to start shimmer animation
   */
  public void showLoading() {
    mBinding.shimmerLayout.setVisibility(View.VISIBLE);
    mBinding.shimmerLayout.startShimmerAnimation();
  }

  /**
   * This method is using to stop shimmer animation
   */
  public void hideLoading() {
    mBinding.shimmerLayout.stopShimmerAnimation();
    mBinding.shimmerLayout.setVisibility(View.GONE);
  }

  /**
   * Subscribing to HomePage Data from API
   */
  private void subscribeHomeData() {
    mHomeViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(),
            homePageData -> {
              mHomeData.clear();
              mHomeData.addAll(homePageData);
              hideLoading();
              mBinding.rvHome.setVisibility(View.VISIBLE);
              mHomeAdapter.notifyDataSetChanged();
            });
  }

  /**
   * Subscribing to HomePage Data from API
   */
  private void subscribeNotificationCountData() {
    mHomeViewModel.getCount().observe(getActivity(), count -> {
      setNotificationCount(count);
    });
  }

  /**
   * Redirecting to AllCategoryActivity for all category onClick
   */
  @Override
  public void categoryOnClick() {
    startActivity(new Intent(getActivity(), AllCategoryActivity.class));
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void catViewMoreOnClickListener(String catId, String catName) {
    Intent intent = new Intent(getActivity(), CategoryViewMoreActivity.class);
    intent.putExtra(CATEGORY_NAME, catName);
    intent.putExtra(CATEGORY_ID, catId);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void onHomeSubCatClickListener(String catName, String subCatName) {
    onSubCatClicked(catName, subCatName);
  }

  /*handles the pagination of items on recycler view */
  private void pagination() {
    mBinding.rvHome.addOnScrollListener(
            new MyScrollListener(mLinearLayoutManager) {
              @Override
              protected void loadMoreItems() {
                mHomeViewModel.callHomeSubCatApi();
              }

              @Override
              public boolean isLastPage() {
                return false;
              }

              @Override
              public boolean isLoading() {
                return false;
              }
            });
  }

  /**
   * set notification count
   */
  private void setNotificationCount(int count) {
    mUserInfoHandler.setNotificationCount(count);
    if (count > ZERO) {
      mBinding.searchHeader.tvNotificationCount.setVisibility(View.VISIBLE);
      mBinding.searchHeader.tvNotificationCount.setText(String.valueOf(count));
    } else {
      mBinding.searchHeader.tvNotificationCount.setVisibility(View.GONE);
    }
  }

  @Override
  public void onBannerClickListener(CategoryData categoryData) {
    switch (categoryData.getType()) {
      case OFFER_TYPE:
        onCategoryOfferBannerClickListener(categoryData.getName(), categoryData.getOfferName());
        break;
      case SUB_CAT:
        onSubCatClicked(categoryData.getCatName(), categoryData.getName());
        break;
      case SUB_SUB_CAT:
        onSubSubCatClicked(categoryData.getCatName(), categoryData.getSubCatName(),
                categoryData.getName());
        break;
    }
  }

  @Override
  public void onSubCatClicked(String catName, String subCatName) {
    Intent intent = new Intent(getActivity(), ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, catName);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  private void onSubSubCatClicked(String catName, String subCatName, String subSubCatName) {
    Intent intent = new Intent(getActivity(), ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, catName);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    intent.putExtra(SUB_SUB_CATEGORY_NAME, subSubCatName);
    startActivity(intent);
  }

  @Override
  public void onBrandItemClickListener(String brandName) {
    Intent intent = new Intent(getActivity(), ProductListingActivity.class);
    intent.putExtra(BRAND_NAME, brandName);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void onCategoryOfferBannerClickListener(String offerId, String offerName) {
    Intent intent = new Intent(getActivity(), ProductListingActivity.class);
    intent.putExtra(OFFER_ID_VAL, offerId);
    intent.putExtra(OFFER_NAME, offerName);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void onTodayDealsClickListener(String headerName) {
    Intent intent = new Intent(getActivity(), TrendingProductsActivity.class);
    intent.putExtra(HEADER, headerName);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void onSubCategoryProductsViewMoreClickListener(String catName, String subCatName) {
    onSubCatClicked(catName, subCatName);
  }

  @Override
  public void onRecentlyViewedViewMoreClick() {
    startActivity(new Intent(getActivity(), RecentlyViewedProductsActivity.class));
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void viewMoreAllBrandsClick() {
    startActivity(new Intent(getActivity(), AllBrandsActivity.class));
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  /**
   * open the login activity screen
   */
  private void startBoardingAct() {
    Intent intent = new Intent(getActivity(), EcomLoginActivity.class);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }
}