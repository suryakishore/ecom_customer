package com.customer.fivecanale.category;

import static com.customer.fivecanale.util.EcomConstants.CATEGORY_ID;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_ID;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.TEN;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import com.R;
import com.customer.domain.interactor.handler.CartHandler;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.cart.EcomCartActivity;
import com.customer.fivecanale.landing.searchscreen.SearchActivity;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.uiutil.SpacesItemDecoration;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.ActivityCatViewMoreBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds Ui for CategoryList.
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class CategoryViewMoreActivity extends DaggerAppCompatActivity implements
    CategoryViewMoreAdapter.SubCatClickListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  CartHandler mCartHandler;
  private String mCatId, mCatName;
  private ActivityCatViewMoreBinding mBinding;
  private CategoryViewMoreAdapter mCategoryViewMoreAdapter = null;
  private ArrayList<SubCategoryData> mSubCategoryDataArrayList = new ArrayList<>();
  private int mPenCount;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cat_view_more);
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.seven_dp);
    mBinding.rvCatList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    initialize();
    setCartStatus();
  }

  /**
   * This method is using to initialize all the basic resources
   */
  private void initialize() {
    showLoading();
    Intent intent = getIntent();
    if (intent != null) {
      mCatId = intent.getStringExtra(CATEGORY_ID);
      mCatName = intent.getStringExtra(CATEGORY_NAME);
    }
    mBinding.header.tvCenterCategoryName.setText(mCatName);
    mBinding.header.ivSearch.setVisibility(View.VISIBLE);
    CatViewMoreViewModel viewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        CatViewMoreViewModel.class);
    mBinding.setViewModel(viewModel);
    viewModel.onCartUpdate();
    viewModel.getCategoryData(mCatId, ZERO, TEN);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, TWO);
    mBinding.rvCatList.setLayoutManager(gridLayoutManager);
    mCategoryViewMoreAdapter = new CategoryViewMoreAdapter(
        mSubCategoryDataArrayList, mCatName, this, mCatId);
    mBinding.rvCatList.setAdapter(mCategoryViewMoreAdapter);
    viewModel.getLiveData().observe(this, categoryData -> {
      hideLoading();
      mPenCount = categoryData.getData().get(ZERO).getPenCount();
      if (categoryData.getData() != null && categoryData.getData().size() > ZERO) {
        mSubCategoryDataArrayList.addAll(categoryData.getData().get(ZERO).getSubCategory());
      }
      mCategoryViewMoreAdapter.notifyDataSetChanged();
    });
    mBinding.header.ivBack.setOnClickListener(view -> onBackPressed());
    mBinding.header.ivSearch.setOnClickListener(
        view -> startActivity(new Intent(this, SearchActivity.class)));
    mBinding.header.tvPlpCart.setVisibility(View.VISIBLE);
    mBinding.header.tvPlpCart.setOnClickListener(view -> {
      Intent cartIntent = new Intent(this, EcomCartActivity.class);
      startActivity(cartIntent);
    });
    viewModel.getClickActionLiveData().observe(this, categoryUiActions -> {
      switch (categoryUiActions) {
        case BACK:
          finish();
          break;
        case CART_COUNT:
          setCartStatus();
          break;
      }
    });
    mBinding.rvCatList.addOnScrollListener(new MyScrollListener(gridLayoutManager) {
      @Override
      protected void loadMoreItems() {
        EcomUtil.printLog("exe" + "mPenCount" + mPenCount);
        if (mSubCategoryDataArrayList.size() < mPenCount) {
          viewModel.getCategoryData(mCatId, mSubCategoryDataArrayList.size(),
              mSubCategoryDataArrayList.size() + LIMIT);
        }
      }

      @Override
      public boolean isLastPage() {
        return mBinding.shimmerLayout.getVisibility() == View.VISIBLE;
      }

      @Override
      public boolean isLoading() {
        return mBinding.shimmerLayout.getVisibility() == View.VISIBLE;
      }
    });
  }

  /**
   * Start Shimmer Effect at beginning of API call
   */
  public void showLoading() {
    mBinding.shimmerLayout.setVisibility(View.VISIBLE);
    mBinding.shimmerLayout.startShimmerAnimation();
  }

  /**
   * End Shimmer Effect at beginning of API call
   */
  public void hideLoading() {
    mBinding.shimmerLayout.stopShimmerAnimation();
    mBinding.shimmerLayout.setVisibility(View.GONE);
  }

  @Override
  public void onSubCatClicked(String catName, String subCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, catName);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    startActivity(intent);
  }

  @Override
  public void redirectToSubSubCategory(String catId, String subCatId, String subCatName) {
    Intent intent = new Intent(this, CategoryViewMoreActivity.class);
    intent.putExtra(CATEGORY_ID, catId);
    intent.putExtra(SUB_CATEGORY_ID, subCatId);
    intent.putExtra(CATEGORY_NAME, subCatName);
    startActivity(intent);
  }

  @Override
  public void onSubSubCategoryClicked(String subSubCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, mCatName);
    intent.putExtra(SUB_SUB_CATEGORY_NAME, subSubCatName);
    startActivity(intent);
  }

  /**
   * this method will set the cart total cart count which we added.
   */
  void setCartStatus() {
    new Thread(() -> {
      int count = mCartHandler.totalCartItems();
      runOnUiThread(() -> {
        mBinding.header.tvCartCount.setVisibility(
            count > ZERO ? View.VISIBLE : View.INVISIBLE);
        if (count > ZERO) {
          mBinding.header.tvCartCount.setText(String.valueOf(count));
        }
      });
    }).start();
  }
}
