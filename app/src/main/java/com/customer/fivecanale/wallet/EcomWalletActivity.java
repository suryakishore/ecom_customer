package com.customer.fivecanale.wallet;

import static com.customer.fivecanale.util.EcomConstants.ADD_MONEY_RC;
import static com.customer.fivecanale.util.EcomConstants.ALL_WALLET;
import static com.customer.fivecanale.util.EcomConstants.AMOUNT;
import static com.customer.fivecanale.util.EcomConstants.CARD_ID;
import static com.customer.fivecanale.util.EcomConstants.CREDIT_WALLET;
import static com.customer.fivecanale.util.EcomConstants.CURRENCY;
import static com.customer.fivecanale.util.EcomConstants.DEBIT_WALLET;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.WALLET_AMT;
import static com.customer.fivecanale.util.EcomConstants.WALLET_CODE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.wallet.WalletTransactionItemData;
import com.customer.fivecanale.addmoney.EcomAddMoneyActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityEcomWalletBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * show the wallet balance and all transactions for the user.
 */
public class EcomWalletActivity extends DaggerAppCompatActivity {
  public int mTotalCount;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  @Named(ALL_WALLET)
  EcomWalletFragment mAllTransactionFragment;
  @Inject
  @Named(DEBIT_WALLET)
  EcomWalletFragment mDebitTransactionFragment;
  @Inject
  @Named(CREDIT_WALLET)
  EcomWalletFragment mCreditTransactionFragment;
  ArrayList<WalletTransactionItemData> mWalletAllTransactionItemData = new ArrayList<>();
  ArrayList<WalletTransactionItemData> mWalletDebitTransactionItemData = new ArrayList<>();
  ArrayList<WalletTransactionItemData> mWalletCreditTransactionItemData = new ArrayList<>();
  private ActivityEcomWalletBinding mWalletBinding;
  private WalletModel mWalletModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    subScribeWalletData();
    subScribeToEmptyTransactions();
    subScribeWalletTransactionsData();
    subScribeBackData();
    subScribeMsgData();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mWalletBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_ecom_wallet);
    mWalletBinding.incHeader.tvCenterCategoryName.setText(getResources().getString(R.string.wallet));
    mWalletModel = ViewModelProviders.of(this, mViewModelFactory).get(WalletModel.class);
    mWalletBinding.setViewModel(mWalletModel);
    mWalletBinding.btnAddMoney.setOnClickListener(view -> {
      Intent intent = new Intent(this, EcomAddMoneyActivity.class);
      startActivityForResult(intent, ADD_MONEY_RC);
    });
    mWalletModel.callGetWalletApi();
  }

  /**
   * initialize the fragment manager.
   */
  private void initialize() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    EcomWalletPagerAdapter adapter = new EcomWalletPagerAdapter(fragmentManager);
    adapter.addFragment(mAllTransactionFragment, getString(R.string.allTransactions));
    adapter.addFragment(mDebitTransactionFragment, getString(R.string.debits));
    adapter.addFragment(mCreditTransactionFragment, getString(R.string.credits));
    mWalletBinding.vpWallet.setAdapter(adapter);
    mWalletBinding.tabEcomWallet.setupWithViewPager(mWalletBinding.vpWallet);
  }

  /**
   * subscribe to wallet data
   */
  private void subScribeWalletData() {
    mWalletModel.getWalletData().observe(this, walletData -> {
      mWalletBinding.tvAvailableBal.setText(walletData);
    });
  }

  /**
   * subscribe to empty transaction data
   */
  private void subScribeToEmptyTransactions() {
    mWalletModel.getEmptyTransactions().observe(this, aBoolean -> {
      initialize();
    });
  }

  /**
   * subscribe to wallet data
   */
  private void subScribeWalletTransactionsData() {
    mWalletModel.getTransactionLIveData().observe(this,
        integerArrayListPair -> {
          mTotalCount = integerArrayListPair.first;
          mWalletAllTransactionItemData.clear();
          mWalletDebitTransactionItemData.clear();
          mWalletCreditTransactionItemData.clear();
          mWalletAllTransactionItemData.addAll(integerArrayListPair.second);
          for (WalletTransactionItemData itemData : mWalletAllTransactionItemData) {
            if (itemData.getTxntype() != null && Integer.parseInt(itemData.getTxntype()) == ONE) {
              mWalletCreditTransactionItemData.add(itemData);
            } else {
              mWalletDebitTransactionItemData.add(itemData);
            }
          }
          Bundle allBundle = new Bundle();
          allBundle.putInt(WALLET_CODE, ZERO);
          allBundle.putParcelableArrayList(ALL_WALLET, mWalletAllTransactionItemData);
          mAllTransactionFragment.setArguments(allBundle);
          Bundle debitBundle = new Bundle();
          debitBundle.putInt(WALLET_CODE, ONE);
          debitBundle.putParcelableArrayList(DEBIT_WALLET, mWalletDebitTransactionItemData);
          mDebitTransactionFragment.setArguments(debitBundle);
          Bundle creditBundle = new Bundle();
          creditBundle.putInt(WALLET_CODE, TWO);
          creditBundle.putParcelableArrayList(CREDIT_WALLET, mWalletCreditTransactionItemData);
          mCreditTransactionFragment.setArguments(creditBundle);
          initialize();
        });
  }

  public boolean isLoading() {
    return mWalletModel.progressVisible.get();
  }

  public void callMoreItemsTransactions() {
    mWalletModel.callGetWalletTransactionsApi();
  }

  /**
   * subscribe to backIcon  data
   */
  private void subScribeBackData() {
    mWalletModel.setBackClick().observe(this, aBoolean -> onBackPressed());
  }

  /**
   * subscribe to backIcon  data
   */
  private void subScribeMsgData() {
    mWalletModel.getMsg().observe(this,
        msg -> Toast.makeText(this, msg, Toast.LENGTH_LONG).show());
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
          mWalletModel.callAddMoneyToWalletApi(cardId, currency, amt);
        }
      }
    }
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra(WALLET_AMT, mWalletBinding.tvAvailableBal.getText().toString());
    setResult(Activity.RESULT_OK, intent);
    finish();
  }
}