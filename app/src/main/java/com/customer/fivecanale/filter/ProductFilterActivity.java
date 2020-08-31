package com.customer.fivecanale.filter;

import static com.customer.fivecanale.util.EcomConstants.APPLIED_FILTERS_LIST;
import static com.customer.fivecanale.util.EcomConstants.BRAND_NAME;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.NONE;
import static com.customer.fivecanale.util.EcomConstants.PEN_COUNT;
import static com.customer.fivecanale.util.EcomConstants.SEARCH_QUERY;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.filter.FilterListData;
import com.customer.fivecanale.filter.FilterCategoryAdapter.FilterCatClickListener;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.databinding.ActivityProductFilterBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

/*
 * Purpose – This class Holds All the Ui of Filter and here you can apply filter to all products.
 * @author 3Embed
 * Created on Dec 05, 2019
 * Modified on
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ProductFilterActivity extends DaggerAppCompatActivity implements
    FilterCatClickListener, FilterSubCatAdapter.OnFilteredDataSelected,
    CustomDialogUtil.SimpleAlertDialogClickHandler {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private HashMap<Integer, Set<String>> mFilterMap;
  private ProductFilterViewModel mFilterViewModel;
  private ArrayMap<Integer, FilterSubCatAdapter> mAdapterList = new ArrayMap<>();
  private ActivityProductFilterBinding mBinding;
  private int mPenCount;
  private boolean mIsClearFilter = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_product_filter);
    showLoading();
    initialize();
    subScribeLiveData();
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
   * This method is using to Initialize basic resources
   */
  private void initialize() {
    mFilterViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        ProductFilterViewModel.class);
    mBinding.setViewModel(mFilterViewModel);
    mBinding.incHeader.tvClearFilter.setVisibility(View.VISIBLE);
    mBinding.incHeader.tvCenterCategoryName.setText(
        getResources().getString(R.string.productFilterHeader));
    mFilterViewModel.getFilterParam();
    if (getIntent() != null) {
      mPenCount = getIntent().getIntExtra(PEN_COUNT, ZERO);
      mBinding.tvProductCount.setText(String.valueOf(mPenCount));
      mFilterViewModel.getFilterParams(getIntent().getStringExtra(CATEGORY_NAME),
          getIntent().getStringExtra(SUB_CATEGORY_NAME),
          getIntent().getStringExtra(SUB_SUB_CATEGORY_NAME),
          getIntent().getStringExtra(SEARCH_QUERY), getIntent().getStringExtra(BRAND_NAME));
      mFilterMap = (HashMap<Integer, Set<String>>)
          getIntent().getSerializableExtra(APPLIED_FILTERS_LIST);
      mFilterViewModel.setPreviousFilerParam(mFilterMap);
      if (mFilterMap == null) {
        mFilterMap = new HashMap<>();
      }
    }
    mBinding.incHeader.tvClearFilter.setOnClickListener(view -> {
      CustomDialogUtil.basicAlertDialog(this, this, getString(R.string.alert),
          getString(R.string.clearFilterWarning), NONE);
    });
    mBinding.btnFilterApply.setOnClickListener(
        view -> setActivityResult());
  }

  /**
   * This method is using to Subscribe to get Filter Param API response
   */
  private void subScribeLiveData() {
    mFilterViewModel.getFilterParamLiveData().observe(this, filterLists -> {
      FilterCategoryAdapter adapter = new FilterCategoryAdapter(filterLists);
      hideLoading();
      mBinding.rvFilterCat.setAdapter(adapter);
      onCategoryClickListener(filterLists.get(ZERO).getData(),
          filterLists.get(ZERO).getFilterType());
    });
    mFilterViewModel.getFilterUiActionLiveData().observe(this, filterUiActon -> finish());
    mFilterViewModel.getProductListLiveData().observe(this, mCount -> {
      mPenCount = mCount;
      mBinding.tvProductCount.setText(String.valueOf(mPenCount));
    });
  }

  @Override
  public void onCategoryClickListener(ArrayList<FilterListData> filterListData, int type) {
    if (mAdapterList.containsKey(type)) {
      mBinding.rvFilterSubCat.setAdapter(mAdapterList.get(type));
    } else {
      FilterSubCatAdapter adapter = new FilterSubCatAdapter(filterListData, type, this);
      mBinding.rvFilterSubCat.setAdapter(adapter);
      mAdapterList.put(type, adapter);
    }
  }

  /**
   * This method is using to send back the filter result mData
   */
  public void setActivityResult() {
    if (mIsClearFilter) {
      mFilterMap.clear();
    }
    Intent data = new Intent();
    data.putExtra(APPLIED_FILTERS_LIST, mFilterMap);
    data.putExtra(PEN_COUNT, mPenCount);
    setResult(RESULT_OK, data);
    finish();
  }

  @Override
  public void onFilteredDataSelectedListener(int name, Set<String> filterList) {
    if (filterList == null || filterList.size() <= 0) {
      mFilterMap.remove(name);
    } else {
      mFilterMap.put(name, filterList);
    }
  }

  @Override
  public void onOkClickListener(int type) {
    mIsClearFilter = true;
    mFilterViewModel.clearFilter();
  }
}