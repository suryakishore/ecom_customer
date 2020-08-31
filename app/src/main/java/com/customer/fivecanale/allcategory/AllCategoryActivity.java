package com.customer.fivecanale.allcategory;

import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.ActivityAllCategoryBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds All category UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class AllCategoryActivity extends DaggerAppCompatActivity implements
    SubCategoryAdapter.SubCatClickListener, CategoryAdapter.CategoryClickHandler {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  AllCategoryViewModel mViewModel;
  private CategoryAdapter mCategoryAdapter;
  private SubCategoryAdapter mAdapter;
  private ActivityAllCategoryBinding mBinding;
  private String mSelectedCategoryName;
  private LinearLayoutManager mLinearLayoutManager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_category);
    mLinearLayoutManager = new LinearLayoutManager(this);
    mBinding.rvCategory.setLayoutManager(mLinearLayoutManager);
    showLoading();
    initialize();
    pagination();
  }

  /*handles the pagination of items on recycler view */
  private void pagination() {
    mBinding.rvCategory.addOnScrollListener(
        new MyScrollListener(mLinearLayoutManager) {
          @Override
          protected void loadMoreItems() {
            mViewModel.callProductCategoryApi();
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
   * THis method is using to do all the basic resource initialization
   */
  private void initialize() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AllCategoryViewModel.class);
    mBinding.setViewModel(mViewModel);
    mViewModel.getLiveData().observe(this, homePageData -> {
      hideLoading();
      mCategoryAdapter = new CategoryAdapter(homePageData.getData(), this);
      mBinding.rvCategory.setAdapter(mCategoryAdapter);
      onCatClickListener(homePageData.getData().get(ZERO).getSubCategory(),
          homePageData.getData().get(ZERO).getCatName());
    });
    mViewModel.getActionsMutableLiveData().observe(this, allCategoryUiActions -> finish());
    mViewModel.callProductCategoryApi();
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

  @Override
  public void onCatClickListener(ArrayList<SubCategoryData> subCategoryData, String catName) {
    mSelectedCategoryName = catName;
    mAdapter = new SubCategoryAdapter(subCategoryData, this, catName);
    mBinding.rvSubCatList.setAdapter(mAdapter);
  }

  @Override
  public void onSubCatClickListener(String catName, String subCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, catName);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    startActivity(intent);
  }

  @Override
  public void subSubCatClickListener(String subSubCatName, String subCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, mSelectedCategoryName);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    intent.putExtra(SUB_SUB_CATEGORY_NAME, subSubCatName);
    startActivity(intent);
  }
}