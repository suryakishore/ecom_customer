package com.customer.fivecanale.savedcards;

import static com.appscrip.stripe.Constants.AUTHORIZATION;
import static com.appscrip.stripe.Constants.CARD;
import static com.appscrip.stripe.Constants.ENGLISH;
import static com.appscrip.stripe.Constants.LANGUAGE;
import static com.appscrip.stripe.Constants.USER_ID;
import static com.customer.fivecanale.util.EcomConstants.ADD_CARD_RC;
import static com.customer.fivecanale.util.EcomConstants.AMOUNT;
import static com.customer.fivecanale.util.EcomConstants.CARD_ID;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_SELECT_CARD;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.appscrip.stripe.StripePaymentIntentActivity;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivitySavedCardsBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * used to show all added cards
 */
public class SavedCardsActivity extends DaggerAppCompatActivity implements SelectItem,
    CustomDialogUtil.DialogCallBackListener {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivitySavedCardsBinding mBinding;
  private SavedCardViewModel mSavedCardViewModel;
  private ArrayList<SavedCardsData> mSavedCardsData = new ArrayList<>();
  private SavedCardsAdapter mSavedCardsAdapter;
  private boolean mIsToSelectCard;
  private int mPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_saved_cards);
    initializeView();
    subScribeSavedCards();
    subScribeBackClick();
  }

  /**
   * initialize views
   */
  private void initializeView() {
    mIsToSelectCard = getIntent().getBooleanExtra(IS_TO_SELECT_CARD, FALSE);
    mBinding.incHeader.tvCenterCategoryName.setText(
        mIsToSelectCard ? getResources().getString(R.string.selectCard)
            : getResources().getString(R.string.debitOrCreditCard));
    mBinding.btnAddMoney.setVisibility(mIsToSelectCard ? View.VISIBLE : View.GONE);
    mBinding.btnAddNewCard.setVisibility(mIsToSelectCard ? View.GONE : View.VISIBLE);
    mSavedCardViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        SavedCardViewModel.class);
    mBinding.setViewModel(mSavedCardViewModel);
    mBinding.btnAddNewCard.setOnClickListener(view -> {
      startStripePaymentAct();
    });
    mBinding.tvAddCard.setOnClickListener(view -> {
      startStripePaymentAct();
    });
    mBinding.btnAddMoney.setOnClickListener(view -> {
      CustomDialogUtil.showDialog(TWO, this, getIntent().getStringExtra(AMOUNT), this);
    });
    mSavedCardsAdapter = new SavedCardsAdapter(this::onSelectItem, mSavedCardsData,
        mIsToSelectCard);
    mBinding.rvSavedCards.setAdapter(mSavedCardsAdapter);
    mSavedCardViewModel.getCards();
  }

  /**
   * subscribe to saved cards
   */
  private void subScribeSavedCards() {
    mSavedCardViewModel.getSavedCardsLIveData().observe(this,
        savedCardsData -> {
          mSavedCardsData.clear();
          mSavedCardsData.addAll(savedCardsData);
          mSavedCardsAdapter.notifyDataSetChanged();
          mBinding.vgNoCardsFound.setVisibility(
              mSavedCardsData.size() > ZERO ? View.GONE : View.VISIBLE);
          mBinding.tvAddCard.setVisibility(mIsToSelectCard ? View.VISIBLE : View.GONE);
          if (mSavedCardsData.size() > ZERO) {
            mBinding.rvSavedCards.setVisibility(View.VISIBLE);
            mBinding.tvSavedCardsTxt.setVisibility(mIsToSelectCard ? View.GONE : View.VISIBLE);
          } else {
            mBinding.rvSavedCards.setVisibility(View.GONE);
          }
        });
  }

  /**
   * subscribe for back icon
   */
  private void subScribeBackClick() {
    mSavedCardViewModel.onBackClickLiveData().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean value) {
        finish();
      }
    });
  }

  /**
   * start payment act
   */
  private void startStripePaymentAct() {
    Intent intent = new Intent(SavedCardsActivity.this, StripePaymentIntentActivity.class);
    intent.putExtra(USER_ID, mUserInfoHandler.userId());
    intent.putExtra(LANGUAGE, ENGLISH);
    intent.putExtra(AUTHORIZATION, mUserInfoHandler.getToken());
    startActivityForResult(intent, ADD_CARD_RC);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADD_CARD_RC) {
      if (resultCode == RESULT_OK) {
        if (data != null) {
          String cardToken = data.getStringExtra(CARD);
          EcomUtil.printLog("exe" + "cardToken" + cardToken);
          mSavedCardViewModel.getCards();
        }
      }
    }
  }

  @Override
  public void onSelectItem(int position) {
    mPosition = position;
    if (mIsToSelectCard) {
      SavedCardsData savedCardsData = mSavedCardsData.get(mPosition);
      mBinding.btnAddMoney.setEnabled(savedCardsData.isChecked());
    } else {
      CustomDialogUtil.showDialog(ONE, this, "", this);
    }
  }

  @Override
  public void onLogOutClickListener() {
    if (mIsToSelectCard) {
      SavedCardsData savedCardsData = mSavedCardsData.get(mPosition);
      Intent intent = new Intent();
      intent.putExtra(IS_TO_FINISH, TRUE);
      intent.putExtra(CARD_ID, savedCardsData.getId());
      setResult(Activity.RESULT_OK, intent);
      finish();
    } else {
      mSavedCardViewModel.deleteCard(mSavedCardsData.get(mPosition).getId());
    }
  }
}
