package com.customer.fivecanale.addmoney;

import static com.customer.fivecanale.util.EcomConstants.ADD_MONEY_RC;
import static com.customer.fivecanale.util.EcomConstants.AMOUNT;
import static com.customer.fivecanale.util.EcomConstants.CARD_ID;
import static com.customer.fivecanale.util.EcomConstants.CURRENCY;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_SELECT_CARD;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.fivecanale.savedcards.SavedCardsActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityEcomAddMoneyBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.Objects;
import javax.inject.Inject;

/**
 * used to add the money to wallet
 */
public class EcomAddMoneyActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityEcomAddMoneyBinding mBinding;
  private AddMoneyViewModel mAddMoneyViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_ecom_add_money);
    initializeView();
    subScribeBackData();
  }

  /**
   * initialize views
   */
  private void initializeView() {
    mBinding.incHeader.tvCenterCategoryName.setText(getResources().getString(R.string.addMoney));
    mAddMoneyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        AddMoneyViewModel.class);
    mBinding.setViewModel(mAddMoneyViewModel);
    mBinding.btnContinue.setOnClickListener(view -> {
      if (Objects.requireNonNull(mBinding.etEnteredAmt.getText()).toString().length() > ZERO) {
        Intent intent = new Intent(EcomAddMoneyActivity.this, SavedCardsActivity.class);
        intent.putExtra(IS_TO_SELECT_CARD, TRUE);
        intent.putExtra(AMOUNT,
            String.format("%s%s", mBinding.tvCurrencySymbol.getText().toString(),
                mBinding.etEnteredAmt.getText().toString()));
        startActivityForResult(intent, ADD_MONEY_RC);
      } else {
        showError(getResources().getString(R.string.enterAmt));
      }
    });
    EcomUtil.showSoftKeyboard(mBinding.etEnteredAmt);
  }



  /**
   * <h2>showError</h2>
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mBinding.clAddMomey, error, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * subscribe to backIcon  data
   */
  private void subScribeBackData() {
    mAddMoneyViewModel.setBackClick().observe(this, aBoolean -> finish());
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      if (requestCode == ADD_MONEY_RC) {
        if (resultCode == Activity.RESULT_OK) {
          boolean isToFinish = data.getBooleanExtra(IS_TO_FINISH, FALSE);
          String cardId = data.getStringExtra(CARD_ID);
          if (isToFinish) {
            Intent intent = new Intent();
            intent.putExtra(CARD_ID, cardId);
            intent.putExtra(AMOUNT, mBinding.etEnteredAmt.getText().toString());
            intent.putExtra(CURRENCY, mBinding.tvCurrencySymbol.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
          }
        }
      }
    }
  }
}
