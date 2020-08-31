package com.customer.fivecanale.cancelorder;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.OFFER_PRICE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_NAME;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_PRICE;
import static com.customer.fivecanale.util.EcomConstants.REASON;
import static com.customer.fivecanale.util.EcomConstants.SELECT_REASON;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.fivecanale.selectreason.SelectReasonActivity;
import com.databinding.ActivityCancelOrderOrProductBinding;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/**
 * This activity is used to cancel a particular product with reason.
 */
public class CancelOrderOrProductActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityCancelOrderOrProductBinding mActivityCancelOrderOrProductBinding;
  private CancelOrderOrProductViewModel mCancelOrderOrProductViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeOnClick();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mActivityCancelOrderOrProductBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_cancel_order_or_product);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mCancelOrderOrProductViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        CancelOrderOrProductViewModel.class);
    mActivityCancelOrderOrProductBinding.setViewModel(mCancelOrderOrProductViewModel);
    mActivityCancelOrderOrProductBinding.includeCartHeader.tvTitle.setText(
        getResources().getString(R.string.cancelProduct));
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String productPrice = extras.getString(PRODUCT_PRICE);
      String offerPrice = extras.getString(OFFER_PRICE);
      mCancelOrderOrProductViewModel.orderId = extras.getString(ORDER_ID);
      mCancelOrderOrProductViewModel.productName.set(extras.getString(PRODUCT_NAME));
      mCancelOrderOrProductViewModel.imageUrl.set(extras.getString(PRODUCT_IMAGE));
      if (productPrice != null && !productPrice.isEmpty()) {
        mCancelOrderOrProductViewModel.productPrice.set(productPrice);
      }
      if (offerPrice != null && !offerPrice.isEmpty()) {
        mCancelOrderOrProductViewModel.productActualPrice.set(offerPrice);
      } else {
        mCancelOrderOrProductViewModel.offerViews.set(FALSE);
      }
    }
    mActivityCancelOrderOrProductBinding.etCancelOrderReason.setFocusable(FALSE);
  }

  /**
   * subscribe for the  on click
   */
  private void subscribeOnClick() {
    mCancelOrderOrProductViewModel.onClick().observe(this, cancelOrderUiAction -> {
      switch (cancelOrderUiAction) {
        case CROSS:
          finish();
          break;
        case SELECT_REASON:
          Intent intent = new Intent(this, SelectReasonActivity.class);
          startActivityForResult(intent, SELECT_REASON);
          break;
        case SUBMIT_REQUEST:
          Intent submitIntent = new Intent();
          submitIntent.putExtra(IS_TO_FINISH, TRUE);
          setResult(RESULT_OK, submitIntent);
          finish();
          break;
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == SELECT_REASON) {
        if (data != null) {
          String reason = data.getStringExtra(REASON);
          mActivityCancelOrderOrProductBinding.etCancelOrderReason.setText(reason);
          mCancelOrderOrProductViewModel.reason = reason;
          mActivityCancelOrderOrProductBinding.tvSubmitRequest.setEnabled(TRUE);
        }
      }
    }
  }
}
