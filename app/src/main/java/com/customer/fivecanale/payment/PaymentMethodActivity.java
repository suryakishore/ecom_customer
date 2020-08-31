package com.customer.fivecanale.payment;

import static com.appscrip.stripe.Constants.AUTHORIZATION;
import static com.appscrip.stripe.Constants.CARD;
import static com.appscrip.stripe.Constants.ENGLISH;
import static com.appscrip.stripe.Constants.LANGUAGE;
import static com.appscrip.stripe.Constants.USER_ID;
import static com.customer.fivecanale.util.EcomConstants.ADD_CARD_RC;
import static com.customer.fivecanale.util.EcomConstants.ADD_MONEY_RC;
import static com.customer.fivecanale.util.EcomConstants.AMOUNT;
import static com.customer.fivecanale.util.EcomConstants.BRAND_LOGO;
import static com.customer.fivecanale.util.EcomConstants.CARD_ID;
import static com.customer.fivecanale.util.EcomConstants.CARD_NUMBER;
import static com.customer.fivecanale.util.EcomConstants.CARD_PAYMENT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.CART_AMT;
import static com.customer.fivecanale.util.EcomConstants.CART_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.CASH_PAYMENT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.COMING_FROM;
import static com.customer.fivecanale.util.EcomConstants.CONFIRM_ORDER;
import static com.customer.fivecanale.util.EcomConstants.CURRENCY;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT_BY_WALLET;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.WALLET_AMT;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.appscrip.stripe.StripePaymentIntentActivity;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.addmoney.EcomAddMoneyActivity;
import com.customer.fivecanale.cart.EcomCartActivity;
import com.customer.fivecanale.savedcards.SavedCardsAdapter;
import com.customer.fivecanale.savedcards.SavedCardsData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityPaymentMethodBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * select the payment method like wallet,cards and cash delivery.
 */
public class PaymentMethodActivity extends DaggerAppCompatActivity implements SelectItem {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  ActivityPaymentMethodBinding mBinding;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private PaymentMethodViewModel mPaymentMethodViewModel;
  private ArrayList<SavedCardsData> mSavedCardsData = new ArrayList<>();
  private SavedCardsAdapter mSavedCardsAdapter;
  private int mPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    subScribeWalletData();
    subScribeSavedCards();
    subscribeSavedCardsUnSelect();
    subscribeOnClick();
    mPaymentMethodViewModel.callGetWalletApi();
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment_method);
    mPaymentMethodViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        PaymentMethodViewModel.class);
    mBinding.setViewmodel(mPaymentMethodViewModel);
    mBinding.includePaymentMethodHeader.tvTitle.setText(
        getResources().getString(R.string.allPaymentMethod));
    mBinding.tvPmAmt.setText(
        String.format("%s %s", getIntent().getStringExtra(CART_AMT).split(",")[ZERO],
            String.format("%.2f",
                Float.parseFloat(getIntent().getStringExtra(CART_AMT).split(",")[ONE]))));
    mPaymentMethodViewModel.amtToBePaid = Float.parseFloat(
        getIntent().getStringExtra(CART_AMT).split(",")[ONE]);
    mPaymentMethodViewModel.toCurrency =
        getIntent().getStringExtra(CART_AMT).split(",")[ZERO];
    if (getIntent() != null && getIntent().getStringExtra(COMING_FROM) != null) {
      mPaymentMethodViewModel.comingFrom = getIntent().getStringExtra(COMING_FROM);
    }
    mBinding.includePaymentMethodHeader.ivBackBtn.setImageResource((R.drawable.all_back));
    mSavedCardsAdapter = new SavedCardsAdapter(this::onSelectItem, mSavedCardsData, TRUE);
    mBinding.rvSavedCards.setAdapter(mSavedCardsAdapter);
  }

  /**
   * subscribe to saved cards
   */
  private void subScribeSavedCards() {
    mPaymentMethodViewModel.getSavedCardsLIveData().observe(this,
        savedCardsData -> {
          mSavedCardsData.clear();
          mSavedCardsData.addAll(savedCardsData);
          mSavedCardsAdapter.notifyDataSetChanged();
        });
  }

  /**
   * subscribe to wallet data
   */
  private void subScribeWalletData() {
    mPaymentMethodViewModel.getWalletData().observe(this, walletBal -> {
      mBinding.tvWalletAmt.setText(walletBal);
    });
  }

  /**
   * subscribe for the back button on click
   */
  private void subscribeOnClick() {
    mPaymentMethodViewModel.onClick().observe(this, paymentUiAction -> {
      switch (paymentUiAction) {
        case BACK:
          onBackPressed();
          break;
        case ADD_CARD:
          startStripePaymentAct();
          break;
        case ADD_MONEY:
          Intent addMoneyIntent = new Intent(this, EcomAddMoneyActivity.class);
          startActivityForResult(addMoneyIntent, ADD_MONEY_RC);
          break;
        case WALLET_PLUS_CARD:
          startGoToCartAct(mSavedCardsData.get(mPosition).getId(), CARD_PAYMENT_TYPE, TRUE, ONE);
          break;
        case WALLET_PLUS_CASH:
          startGoToCartAct("", CASH_PAYMENT_TYPE, TRUE, TWO);
          break;
        case WALLET:
          startGoToCartAct("", TWO, TRUE, THREE);
          break;
        case CARD:
          startGoToCartAct(mSavedCardsData.get(mPosition).getId(), CARD_PAYMENT_TYPE, FALSE, FOUR);
          break;
        case CASH:
          startGoToCartAct("", CASH_PAYMENT_TYPE, FALSE, FIVE);
          break;
        case DISABLE:
          disableViews();
          break;
        case ENABLE:
          enableViews();
          break;
        case DISABLE_CARDS:
          disableCardViews();
          break;
        case ENABLE_CARDS:
          enableCardViews();
          break;
      }
    });
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  private void showErrorMsg(String msg) {
    Snackbar.make(mBinding.clPaymentMethod, msg, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * it will open the cart activity
   */
  private void startGoToCartAct(String cardId, int paymentType, boolean payByWallet, int payment) {
    Intent intent = new Intent(this, EcomCartActivity.class);
    Bundle bundle = new Bundle();
    if (mSavedCardsData != null && mSavedCardsData.size() > ZERO) {
      bundle.putString(CARD_ID, cardId);
      bundle.putString(BRAND_LOGO, mSavedCardsData.get(mPosition).getBrand());
      bundle.putString(CARD_NUMBER, mSavedCardsData.get(mPosition).getLast4());
    }
    bundle.putFloat(WALLET_AMT, mPaymentMethodViewModel.walletBal);
    bundle.putFloat(AMOUNT, mPaymentMethodViewModel.amtToBePaid);
    bundle.putInt(PAYMENT_TYPE, paymentType);
    bundle.putInt(PAYMENT, payment);
    bundle.putString(CURRENCY, getIntent().getStringExtra(CART_AMT).split(",")[ZERO]);
    bundle.putBoolean(PAYMENT_BY_WALLET, payByWallet);
    bundle.putBoolean(CONFIRM_ORDER, TRUE);
    intent.putExtras(bundle);
    switch (mPaymentMethodViewModel.comingFrom) {
      case ORDER_SCREEN:
        setResult(RESULT_OK, intent);
        finish();
        break;

      default:
        startActivity(intent);
        break;
    }
  }

  /**
   * start payment act
   */
  private void startStripePaymentAct() {
    Intent intent = new Intent(this, StripePaymentIntentActivity.class);
    intent.putExtra(USER_ID, mUserInfoHandler.userId());
    intent.putExtra(LANGUAGE, ENGLISH);
    intent.putExtra(AUTHORIZATION, mUserInfoHandler.getToken());
    startActivityForResult(intent, ADD_CARD_RC);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      if (requestCode == ADD_MONEY_RC) {
        if (resultCode == Activity.RESULT_OK) {
          String cardId = data.getStringExtra(CARD_ID);
          String amt = data.getStringExtra(AMOUNT);
          String currency = data.getStringExtra(CURRENCY);
          mPaymentMethodViewModel.callAddMoneyToWalletApi(cardId, currency, amt);
        }
      } else if (requestCode == ADD_CARD_RC) {
        if (resultCode == RESULT_OK) {
          String cardToken = data.getStringExtra(CARD);
          EcomUtil.printLog("exe" + "cardToken" + cardToken);
          mPaymentMethodViewModel.getCards();
        }
      }
    }
  }

  @Override
  public void onSelectItem(int position) {
    mPosition = position;
    SavedCardsData savedCardsData = mSavedCardsData.get(position);
    mPaymentMethodViewModel.mIsChecked = savedCardsData.isChecked();
    if (mPaymentMethodViewModel.mIsChecked) {
      mPaymentMethodViewModel.payOnDeliveryState.set(FALSE);
      mPaymentMethodViewModel.btnEnabled.set(TRUE);
    } else {
      mPaymentMethodViewModel.selectCardButtonStatus();
    }
  }

  /**
   * subscribe selected card management
   */
  private void subscribeSavedCardsUnSelect() {
    mPaymentMethodViewModel.getDisSelectLIveData().observe(this, aBoolean -> {
      SavedCardsData savedCardsData = mSavedCardsData.get(mPosition);
      savedCardsData.setChecked(FALSE);
      mSavedCardsAdapter.notifyItemChanged(mPosition);
      mPaymentMethodViewModel.mIsChecked = FALSE;
    });
  }

  /**
   * disable views.
   */
  private void disableViews() {
    mSavedCardsAdapter.enabled(FALSE);
    mSavedCardsAdapter.notifyDataSetChanged();
    mBinding.tvPMPayOnDel.setEnabled(FALSE);
  }

  /**
   * disable views.
   */
  private void disableCardViews() {
    mSavedCardsAdapter.enabled(FALSE);
    mSavedCardsAdapter.notifyDataSetChanged();
  }

  /**
   * disable card views.
   */
  private void enableCardViews() {
    mSavedCardsAdapter.enabled(TRUE);
    mSavedCardsAdapter.notifyDataSetChanged();
  }

  /**
   * enable card views.
   */
  private void enableViews() {
    mSavedCardsAdapter.enabled(TRUE);
    mSavedCardsAdapter.notifyDataSetChanged();
    mBinding.tvPMPayOnDel.setEnabled(TRUE);
  }
}
