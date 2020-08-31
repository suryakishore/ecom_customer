package com.customer.fivecanale.productlist;

import static com.customer.fivecanale.util.EcomConstants.APPLIED_FILTERS_LIST;
import static com.customer.fivecanale.util.EcomConstants.BRAND_NAME;
import static com.customer.fivecanale.util.EcomConstants.BRAND_TYPE;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.EMPTY;
import static com.customer.fivecanale.util.EcomConstants.FILTER_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.IN_TEXT;
import static com.customer.fivecanale.util.EcomConstants.NONE;
import static com.customer.fivecanale.util.EcomConstants.OFFER_ID;
import static com.customer.fivecanale.util.EcomConstants.OFFER_ID_VAL;
import static com.customer.fivecanale.util.EcomConstants.OFFER_NAME;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PEN_COUNT;
import static com.customer.fivecanale.util.EcomConstants.SEARCH_QUERY;
import static com.customer.fivecanale.util.EcomConstants.SEARCH_TYPE;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import com.R;
import com.customer.domain.interactor.handler.CartHandler;
import com.customer.domain.model.common.ProductsData;
import com.customer.fivecanale.cart.EcomCartActivity;
import com.customer.fivecanale.filter.ProductFilterActivity;
import com.customer.fivecanale.landing.searchscreen.SearchActivity;
import com.customer.fivecanale.sort.SortBottomSheet;
import com.customer.fivecanale.uiutil.SpacesItemDecoration;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.ActivityProductListBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

/*
 * Purpose – This class is using to show Product Listing and applying sort and filter on all the
 * products which listed in this page.
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class ProductListingActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  SortBottomSheet mSortBottomSheet;
  @Inject
  CartHandler mCartHandler;
  private ActivityProductListBinding mBinding;
  private ProductListViewModel mViewModel;
  private String mCatName;
  private String mSubCatName, mSubSubCatName = "";
  private String mSearchQuery;
  private ArrayList<ProductsData> mProductsData = new ArrayList<>();
  private int mPenCount;
  private GridListProductsAdapter mAdapter;
  private HashMap<Integer, Set<String>> mFilterValues;
  private String mBrandName;
  private int mPage = ONE;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initialize();
  }

  /*
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_product_list);
  }

  /**
   * Initializing all the basic resources required for this class
   */
  private void initialize() {
    EcomUtil.printLog("exe" + "ProductListingActivity");
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProductListViewModel.class);
    mBinding.setViewModel(mViewModel);
    mViewModel.onCartUpdate();
    mAdapter = new GridListProductsAdapter(mProductsData);
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.seven_dp);
    mBinding.rvProductList.addItemDecoration(
        new SpacesItemDecoration(spacingInPixels));
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, TWO);
    mBinding.rvProductList.setLayoutManager(gridLayoutManager);
    mBinding.rvProductList.setAdapter(mAdapter);
    mViewModel.getProductListLiveData().observe(this,
        integerArrayListPair -> {
          assert integerArrayListPair.second != null;
          hideLoading();
//          mProductsData.clear();
          mProductsData.addAll(integerArrayListPair.second);
          mAdapter.notifyDataSetChanged();
          mPenCount = integerArrayListPair.first;
        });
    subscribeOnClicks();
    String catName = "";
    if (getIntent() != null && !TextUtils.isEmpty(
        getIntent().getStringExtra(SUB_SUB_CATEGORY_NAME))) {
      showLoading();
      mCatName = getIntent().getStringExtra(CATEGORY_NAME);
      mSubCatName = getIntent().getStringExtra(SUB_CATEGORY_NAME);
      mSubSubCatName = getIntent().getStringExtra(SUB_SUB_CATEGORY_NAME);
      catName = mSubSubCatName;
      mViewModel.getSubSubCatFilterProduct(mCatName, mSubCatName, mSubSubCatName, NONE,
          String.valueOf(mPage));
    } else if (getIntent() != null && !TextUtils.isEmpty(
        getIntent().getStringExtra(CATEGORY_NAME))) {
      showLoading();
      mCatName = getIntent().getStringExtra(CATEGORY_NAME);
      mSubCatName = getIntent().getStringExtra(SUB_CATEGORY_NAME);
      catName = mSubCatName;
      mViewModel.getSubCatFilterProduct(mCatName, mSubCatName, mSubSubCatName, NONE,
          String.valueOf(mPage));
    } else if (getIntent() != null && !TextUtils.isEmpty(
        getIntent().getStringExtra(SEARCH_QUERY))) {
      mSearchQuery = getIntent().getStringExtra(SEARCH_QUERY);
      catName = mSearchQuery;
      mViewModel.setSearchQuery(mSearchQuery);
      mViewModel.getBrandProduct(mSearchQuery, SEARCH_TYPE, getIntent().getStringExtra(IN_TEXT));
    } else if (getIntent() != null && !TextUtils.isEmpty(
        getIntent().getStringExtra(BRAND_NAME))) {
      mBrandName = getIntent().getStringExtra(BRAND_NAME);
      mViewModel.setBrandName(mBrandName);
      catName = mBrandName;
      mViewModel.getBrandProduct(mBrandName, BRAND_TYPE, EMPTY);
    } else if (getIntent() != null && !TextUtils.isEmpty(
        getIntent().getStringExtra(OFFER_ID_VAL))) {
      catName = getIntent().getStringExtra(OFFER_NAME);
      String offerId = getIntent().getStringExtra(OFFER_ID_VAL);
      mViewModel.setOfferId(offerId);
      mViewModel.getBrandProduct(offerId, OFFER_ID, EMPTY);
    }
    mBinding.incPlpHeader.tvCatName.setText(catName);
    setCartStatus();
    mBinding.incPlpHeader.tvPlpCart.setOnClickListener(view -> {
      Intent intent = new Intent(ProductListingActivity.this, EcomCartActivity.class);
      startActivity(intent);
    });
    mBinding.incPlpHeader.tvPlpSearch.setOnClickListener(
        view -> startActivity(new Intent(this, SearchActivity.class)));
    mBinding.incPlpHeader.ivBack.setOnClickListener(view -> finish());
    mBinding.rvProductList.addOnScrollListener(new MyScrollListener(gridLayoutManager) {
      @Override
      protected void loadMoreItems() {
        EcomUtil.printLog("exe" + "size" + mProductsData.size() + "mPenCount" + mPenCount);
        if (getIntent() != null && !TextUtils.isEmpty(
            getIntent().getStringExtra(SUB_SUB_CATEGORY_NAME))) {
          if (mProductsData.size() < mPenCount) {
            showLoading();
            mPage += ONE;
            mViewModel.getSubSubCatFilterProduct(mCatName, mSubCatName, mSubSubCatName, NONE,
                String.valueOf(mPage));
          }
        } else if (getIntent() != null && !TextUtils.isEmpty(
            getIntent().getStringExtra(CATEGORY_NAME))) {
          if (mProductsData.size() < mPenCount) {
            showLoading();
            mPage += ONE;
            mViewModel.getSubCatFilterProduct(mCatName, mSubCatName, mSubSubCatName, NONE,
                String.valueOf(mPage));
          }
        }
      }

      @Override
      public boolean isLastPage() {
        return mBinding.productListShimmer.getVisibility() == View.VISIBLE;
      }

      @Override
      public boolean isLoading() {
        return mBinding.productListShimmer.getVisibility() == View.VISIBLE;
      }
    });
  }

  /**
   * Subscribing to all the view click listener actions
   */
  private void subscribeOnClicks() {
    mViewModel.onClick().observe(this, loginUiAction -> {
      switch (loginUiAction) {
        case SORT:
          Bundle bundle = new Bundle();
          bundle.putString(CATEGORY_NAME, mCatName);
          bundle.putString(SUB_CATEGORY_NAME, mSubCatName);
          bundle.putString(SUB_SUB_CATEGORY_NAME, mSubSubCatName);
          bundle.putString(SEARCH_QUERY, mSearchQuery);
          mSortBottomSheet.setArguments(bundle);
          mSortBottomSheet.show(getSupportFragmentManager(), mSortBottomSheet.getTag());
          break;
        case FILTER:
          Intent intent = new Intent(this, ProductFilterActivity.class);
          intent.putExtra(CATEGORY_NAME, mCatName);
          intent.putExtra(PEN_COUNT, mPenCount);
          intent.putExtra(SUB_CATEGORY_NAME, mSubCatName);
          intent.putExtra(SUB_SUB_CATEGORY_NAME, mSubSubCatName);
          intent.putExtra(BRAND_NAME, mBrandName);
          intent.putExtra(SEARCH_QUERY, mSearchQuery);
          intent.putExtra(APPLIED_FILTERS_LIST, mFilterValues);
          startActivityForResult(intent, FILTER_REQ_CODE);
          break;
        case HIDE_SHIMMER:
          hideLoading();
          break;
        case BACK:
          onBackPressed();
          break;
        case CART_UPDATE:
          setCartStatus();
          break;
      }
    });
  }

  @Override
  protected void onStop() {
    super.onStop();
    mViewModel.setDisposableSingleObserver();
  }

  @SuppressLint("DefaultLocale")
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == FILTER_REQ_CODE && data != null) {
      mPenCount = data.getIntExtra(PEN_COUNT, ZERO);
      mFilterValues = (HashMap<Integer, Set<String>>) data.getSerializableExtra(
          APPLIED_FILTERS_LIST);
      mViewModel.filterValues = mFilterValues;
      mBinding.tvFilterCount.setVisibility(
          mFilterValues != null && mFilterValues.size() > 0 ? View.VISIBLE : View.INVISIBLE);
      mBinding.tvFilterCount.setText(
          mFilterValues != null ? String.format("(%d)", mFilterValues.size())
              : String.format("%d", ZERO));
      showLoading();
      mViewModel.createFilterObservable(NONE, mFilterValues);
    }
  }

  /**
   * this method will set the cart total cart count which we added.
   */
  void setCartStatus() {
    new Thread(() -> {
      int count = mCartHandler.totalCartItems();
      runOnUiThread(() -> {
        mBinding.incPlpHeader.tvCartCount.setVisibility(
            count > ZERO ? View.VISIBLE : View.INVISIBLE);
        if (count > ZERO) {
          mBinding.incPlpHeader.tvCartCount.setText(String.valueOf(count));
        }
      });
    }).start();
  }

  /**
   * Start Shimmer Effect at beginning of API call
   */
  public void showLoading() {
    mBinding.productListShimmer.setVisibility(View.VISIBLE);
    mBinding.productListShimmer.startShimmerAnimation();
  }

  /**
   * End Shimmer Effect at beginning of API call
   */
  public void hideLoading() {
    mBinding.productListShimmer.stopShimmerAnimation();
    mBinding.productListShimmer.setVisibility(View.GONE);
  }
}