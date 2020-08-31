package com.customer.fivecanale.recentlyviewed;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.fivecanale.productlist.GridListProductsAdapter;
import com.customer.fivecanale.uiutil.SpacesItemDecoration;
import com.databinding.ActivityRecentlyViewedBinding;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/*
 * Purpose – This class is using to show all the recent viewed products.
 * @author 3Embed
 * Created on Jan 14, 2019
 * Modified on
 */
public class RecentlyViewedProductsActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityRecentlyViewedBinding mBinding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_recently_viewed);
    showLoading();
    initialize();
  }

  /**
   * Initializing all the basic resources required for this class
   */
  private void initialize() {
    RecentlyViewedViewModel viewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        RecentlyViewedViewModel.class);
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.seven_dp);
    mBinding.rvRecentlyViewedList.addItemDecoration(
        new SpacesItemDecoration(spacingInPixels));
    viewModel.getAllRecentlyViewedProducts();
    viewModel.getListDataMutableLiveData().observe(this, recentlyViewedData -> {
      hideLoading();
      mBinding.tvCatName.setText(recentlyViewedData.getCatName());
      GridListProductsAdapter adapter = new GridListProductsAdapter(
          recentlyViewedData.getCategoryData());
      mBinding.rvRecentlyViewedList.setAdapter(adapter);
      adapter.notifyDataSetChanged();
    });
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

  /**
   * This method is using to handle view click
   *
   * @param view view reference to get view id
   */
  public void onClickListener(View view) {
    finish();
  }
}