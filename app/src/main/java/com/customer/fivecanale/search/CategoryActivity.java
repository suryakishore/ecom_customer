package com.customer.fivecanale.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.R;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.search.SubCategoryAdapter.SubCatClickListener;
import com.customer.fivecanale.search.SubSubCategoryAdapter.SubSubCatItemClickListener;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityCategoryBinding;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.customer.fivecanale.util.EcomConstants.CATEGORY_LIST;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_NAME;

/*
 * Purpose – This class Holds the UI for category List in Search flow.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class CategoryActivity extends DaggerAppCompatActivity implements SubCatClickListener,
        SubSubCatItemClickListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityCategoryBinding mBinding;
  private String mCategoryName;
  private CategoryViewModel mCategoryViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_category);
    initialize();
  }

  /**
   * This method is using to initialize all the basic resources of the activity
   */
  private void initialize() {
    mCategoryViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
            CategoryViewModel.class);
    mBinding.setViewmodel(mCategoryViewModel);
    Intent intent = getIntent();
    ArrayList<SubCategoryData> subCategoryData = intent.getParcelableArrayListExtra(CATEGORY_LIST);
    EcomUtil.printLog("exe" + "subCategoryData" + subCategoryData);
    mCategoryName = intent.getStringExtra(CATEGORY_NAME);
    SubCategoryAdapter adapter = new SubCategoryAdapter(subCategoryData, this);
    mBinding.rvSubCatList.setAdapter(adapter);
    mBinding.header.tvCenterCategoryName.setText(mCategoryName);
    adapter.notifyDataSetChanged();
    mCategoryViewModel.onBackClick().observe(this, aBoolean -> onBackPressed());
  }

  @Override
  public void subSubCatClickListener(String subSubCatName, String subCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    intent.putExtra(CATEGORY_NAME, mCategoryName);
    intent.putExtra(SUB_SUB_CATEGORY_NAME, subSubCatName);
    startActivity(intent);
  }

  @Override
  public void catOnClick(String subCatName) {
    Intent intent = new Intent(this, ProductListingActivity.class);
    intent.putExtra(SUB_CATEGORY_NAME, subCatName);
    intent.putExtra(CATEGORY_NAME, mCategoryName);
    startActivity(intent);
  }
}