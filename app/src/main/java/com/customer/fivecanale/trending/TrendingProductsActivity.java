package com.customer.fivecanale.trending;

import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.HEADER;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.R;
import com.customer.fivecanale.landing.homescreen.adapters.HomeSubCatAdapter;
import com.customer.fivecanale.landing.searchscreen.SearchActivity;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.databinding.ActivityTrendingBinding;

import dagger.android.support.DaggerAppCompatActivity;

import javax.inject.Inject;

/*
 * Purpose – This class Holds All the Ui Items For Today deals Offer products.
 * @author 3Embed
 * Created on Dec 25, 2019
 * Modified on
 */
public class TrendingProductsActivity extends DaggerAppCompatActivity implements
        HomeSubCatAdapter.SubCatClickListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private TrendingProductsViewModel mViewModel;
  private ActivityTrendingBinding mBinding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_trending);
    initialize();
  }

  /**
   * Initializing all the basic resources required for this class
   */
  private void initialize() {
    if (getIntent() != null) {
      String header = getIntent().getStringExtra(HEADER);
      if (header != null) {
        mBinding.includeTrendingHeader.tvLeftCategoryName.setVisibility(header.equals(
                getResources().getString(R.string.todayDeal)) ? View.VISIBLE : View.INVISIBLE);
        mBinding.includeTrendingHeader.tvCenterCategoryName.setVisibility(header.equals(
                getResources().getString(R.string.todayDeal)) ? View.INVISIBLE : View.VISIBLE);
        mBinding.includeTrendingHeader.tvLeftCategoryName.setText(header);
        mBinding.includeTrendingHeader.tvCenterCategoryName.setText(header);
      }
      mBinding.includeTrendingHeader.ivSearch.setVisibility(View.VISIBLE);
    }
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
            TrendingProductsViewModel.class);
    mBinding.setViewModel(mViewModel);
    mBinding.includeTrendingHeader.ivSearch.setOnClickListener(
            view -> startActivity(new Intent(TrendingProductsActivity.this, SearchActivity.class)));
    mViewModel.getEventMutableLiveData().observe(this, trendingUiEvent -> finish());
    mViewModel.getProductsLiveData().observe(this, homeSubCategoryData -> {
      HomeSubCatAdapter adapter = new HomeSubCatAdapter(homeSubCategoryData,
              TrendingProductsActivity.this);
      mBinding.rvTrendingProducts.setAdapter(adapter);
    });
    mViewModel.getTodayDeals();
  }

  @Override
  public void onHomeSubCatClickListener(String catName, String subCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(CATEGORY_NAME, catName);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    startActivity(intent);
  }
}