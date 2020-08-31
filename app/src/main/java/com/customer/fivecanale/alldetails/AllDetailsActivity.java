package com.customer.fivecanale.alldetails;

import static com.customer.fivecanale.util.EcomConstants.ATTRIBUTES_DATA;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_FROM_CART;
import static com.customer.fivecanale.util.EcomConstants.ITEM_NAME;
import static com.customer.fivecanale.util.EcomConstants.OFFER_PRICE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_PRICE;
import static com.customer.fivecanale.util.EcomConstants.SPECIFICATIONS;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.getcart.CartAttributesData;
import com.customer.domain.model.pdp.AttributesData;
import com.customer.fivecanale.pdp.adapters.SpecificationsAdapter;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityAllDetailsBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.inject.Inject;

/**
 * This activity will describes about the different kinds of the attributes for the product.
 */
public class AllDetailsActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityAllDetailsBinding mBinding;
  private AllDetailsViewModel mAllDetailsViewModel;
  private ArrayList<AttributesData> mAttributesData = new ArrayList<>();
  private ArrayList<CartAttributesData> mCartAttributesData = new ArrayList<>();
  private AllDetailsAdapter mAllDetailsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeCrossClicked();
    getIntentData();
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_details);
  }

  /**
   * This method is used initialize the viewModel class for this activity.
   */
  private void initializeViewModel() {
    mAllDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        AllDetailsViewModel.class);
    mBinding.setViewModel(mAllDetailsViewModel);
  }

  /**
   * get the intent mData from pdp
   */
  private void getIntentData() {
    Intent intent = getIntent();
    boolean isFromCart = intent.getBooleanExtra(IS_FROM_CART, FALSE);
    String title = intent.getStringExtra(SPECIFICATIONS);
    mBinding.tvAllDetTitle.setText(title);
    if (isFromCart) {
      mBinding.vgAllDet.setVisibility(View.GONE);
      ArrayList<CartAttributesData> attributesIntentData = intent.getParcelableArrayListExtra(
          ATTRIBUTES_DATA);
      if (attributesIntentData != null && attributesIntentData.size() > ZERO) {
        mCartAttributesData.addAll(attributesIntentData);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          Predicate<CartAttributesData> condition =
              cartAttributesData -> cartAttributesData.getValue() != null && cartAttributesData.getValue().equals("");
          mCartAttributesData.removeIf(condition);
        }
        SpecificationsAdapter specificationsAdapter = new SpecificationsAdapter(
            mCartAttributesData);
        mBinding.rvAllDetAttributes.setAdapter(specificationsAdapter);
      }
    } else {
      mAllDetailsAdapter = new AllDetailsAdapter(mAttributesData);
      mBinding.rvAllDetAttributes.setAdapter(mAllDetailsAdapter);
      ArrayList<AttributesData> attributesIntentData = intent.getParcelableArrayListExtra(
          ATTRIBUTES_DATA);
      if (attributesIntentData != null && attributesIntentData.size() > ZERO) {
        mAttributesData.addAll(attributesIntentData);
        mAllDetailsAdapter.notifyDataSetChanged();
      }
      String productName = intent.getStringExtra(ITEM_NAME);
      String productPrice = intent.getStringExtra(PRODUCT_PRICE);
      String offerPrice = intent.getStringExtra(OFFER_PRICE);
      String productImage = intent.getStringExtra(PRODUCT_IMAGE);
      String productColor = intent.getStringExtra(PRODUCT_COLOUR);
      if (productImage != null && !productImage.isEmpty()) {
        mAllDetailsViewModel.mProductImage.set(productImage.trim());
      }
      if (productName != null && !productName.isEmpty()) {
        mBinding.tvAllDetProductName.setText(productName);
      }
      if (productPrice != null && !productPrice.isEmpty()) {
        mBinding.tvAllDetProductActualPrice.setText(productPrice);
      }
      if (productColor != null && !productColor.isEmpty()) {
        mBinding.tvAllDetProductColor.setText(productColor);
      }
      if (offerPrice != null && !offerPrice.isEmpty()) {
        mBinding.tvAllDetOfferPrice.setText(offerPrice);
      } else {
        mBinding.vgAllDetOfferPrice.setVisibility(View.GONE);
      }
    }
  }

  /**
   * subscribe for clicks on the cross icon.
   */
  private void subscribeCrossClicked() {
    mAllDetailsViewModel.onCrossClicked().observe(this, aBoolean -> {
      if (aBoolean) {
        onBackPressed();
      }
    });
  }
}