package com.customer.fivecanale.allbrands;

import static com.customer.fivecanale.util.EcomConstants.BRAND_NAME;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TWENTY;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import com.R;
import com.customer.domain.model.home.CategoryData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.uiutil.AnimUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.ActivityAllBrandsBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;

/**
 * This activity will show all the brands
 */
public class AllBrandsActivity extends DaggerAppCompatActivity implements SelectItem {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private AllBrandsViewModel mBrandsViewModel;
  private ActivityAllBrandsBinding mActivityAllBrandsBinding;
  private ArrayList<CategoryData> mCategoryData = new ArrayList<>();
  private AllBrandsAdapter mAllBrandsAdapter;
  private int mPenCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subScribeToAllBrandsData();
    subScribeToBack();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mActivityAllBrandsBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_all_brands);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mBrandsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AllBrandsViewModel.class);
    mActivityAllBrandsBinding.setViewmodel(mBrandsViewModel);
    EcomUtil.hideSoftKeyboard(mActivityAllBrandsBinding.etAllBrandsSearch);
    mActivityAllBrandsBinding.incHeader.tvCenterCategoryName.setText(
        getResources().getString(R.string.allBrands));
    mAllBrandsAdapter = new AllBrandsAdapter(mCategoryData, this::onSelectItem);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, THREE);
    mActivityAllBrandsBinding.rvAllBrands.setLayoutManager(gridLayoutManager);
    mActivityAllBrandsBinding.rvAllBrands.setAdapter(mAllBrandsAdapter);
    mBrandsViewModel.callGetAllBrandsApi(String.valueOf(ZERO), String.valueOf(TWENTY));
    mActivityAllBrandsBinding.rvAllBrands.addOnScrollListener(
        new MyScrollListener(gridLayoutManager) {
          @Override
          protected void loadMoreItems() {
            if (mCategoryData.size() < mPenCount) {
              mBrandsViewModel.callGetAllBrandsApi(
                  String.valueOf(mCategoryData.size()),
                  String.valueOf(mCategoryData.size() + LIMIT));
            }
          }

          @Override
          public boolean isLastPage() {
            return mBrandsViewModel.progressVisible.get();
          }

          @Override
          public boolean isLoading() {
            return mBrandsViewModel.progressVisible.get();
          }
        });
  }

  /**
   * subscribe to all brands data
   */
  private void subScribeToAllBrandsData() {
    mBrandsViewModel.getAllBrandsData().observe(this,
        integerArrayListPair -> {
          mPenCount = integerArrayListPair.first;
          mCategoryData.clear();
          mCategoryData.addAll(integerArrayListPair.second);
          mAllBrandsAdapter.notifyDataSetChanged();
        });
  }

  @Override
  public void onSelectItem(int position) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(BRAND_NAME, mCategoryData.get(position).getName());
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(this));
  }

  /**
   * subscribe for back icon click.
   */
  private void subScribeToBack() {
    mBrandsViewModel.onBackClicked().observe(this, aBoolean -> finish());
  }
}
