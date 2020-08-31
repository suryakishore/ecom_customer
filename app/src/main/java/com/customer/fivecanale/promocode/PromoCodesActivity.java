package com.customer.fivecanale.promocode;

import static com.customer.fivecanale.util.EcomConstants.CART_ID;
import static com.customer.fivecanale.util.EcomConstants.CITY_ID;
import static com.customer.fivecanale.util.EcomConstants.COUNTRY_ID;
import static com.customer.fivecanale.util.EcomConstants.PROMO_CODE;
import static com.customer.fivecanale.util.EcomConstants.PROMO_CODE_ID;
import static com.customer.fivecanale.util.EcomConstants.STORE_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.promocode.PromoCodeData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityPromoCodesBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;

/**
 * This class is used to select the promo codes which are available.
 */
public class PromoCodesActivity extends DaggerAppCompatActivity implements SelectItem {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private PromoCodesViewModel mPromoCodesViewModel;
  private ActivityPromoCodesBinding mPromoCodesBinding;
  private ArrayList<PromoCodeData> mPromoCodeData = new ArrayList<>();
  private PromoCodesAdapter mPromoCodesAdapter;
  private int mPenCount;
  private String mPromoId = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subScribeToPromoCodesData();
    subScribeToCross();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mPromoCodesBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_promo_codes);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mPromoCodesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        PromoCodesViewModel.class);
    mPromoCodesBinding.setViewmodel(mPromoCodesViewModel);
    EcomUtil.hideSoftKeyboard(mPromoCodesBinding.etPromoCode);
    mPromoCodesBinding.includePromoCodeHeader.tvTitle.setText(
        getResources().getString(R.string.promocode));
    mPromoCodesAdapter = new PromoCodesAdapter(this::onSelectItem, mPromoCodeData);
    mPromoCodesBinding.rvPromoCodes.setAdapter(mPromoCodesAdapter);
    mPromoCodesViewModel.callGetAllPromoCodesApi(getIntent().getStringExtra(COUNTRY_ID),
        getIntent().getStringExtra(CITY_ID), getIntent().getStringExtra(CART_ID),
        getIntent().getStringExtra(STORE_ID));
    mPromoCodesBinding.tvApply.setOnClickListener(v -> {
      if (!Objects.requireNonNull(mPromoCodesBinding.etPromoCode.getText()).toString().isEmpty()) {
        Intent data = new Intent();
        data.putExtra(PROMO_CODE, mPromoCodesBinding.etPromoCode.getText().toString());
        data.putExtra(PROMO_CODE_ID, mPromoId);
        setResult(Activity.RESULT_OK, data);
        finish();
      }
    });
  }

  /**
   * subscribe to promo codes data
   */
  private void subScribeToPromoCodesData() {
    mPromoCodesViewModel.getAllPromoCodesData().observe(this,
        promoCodeData -> {
          mPromoCodeData.clear();
          mPromoCodeData.addAll(promoCodeData);
          mPromoCodesAdapter.notifyDataSetChanged();
        });
  }

  /**
   * subscribe for cross icon click.
   */
  private void subScribeToCross() {
    mPromoCodesViewModel.onCrossClicked().observe(this, value -> finish());
  }

  @Override
  public void onSelectItem(int position) {
    mPromoCodesBinding.etPromoCode.setText(mPromoCodeData.get(position).getCode());
    mPromoId = mPromoCodeData.get(position).getPromo_id();
    mPromoCodesBinding.etPromoCode.setSelection(Objects.requireNonNull(
        mPromoCodesBinding.etPromoCode.getText()).toString().length());
  }
}
