package com.customer.fivecanale.wallet;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWENTY;
import static com.customer.fivecanale.util.EcomConstants.WALLET_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.util.Pair;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.WalletAddMoneyUseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import com.customer.domain.interactor.WalletTransactionsUseCase;
import com.customer.domain.model.wallet.WalletTransactionItemData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for the wallet screen
 */
public class WalletModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  private WalletAmtUseCase mWalletAmtUseCase;
  private WalletTransactionsUseCase mWalletTransactionsUseCase;
  private MutableLiveData<String> mWalletData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mEmptyTransations = new MutableLiveData<>();
  private MutableLiveData<String> mMsg = new MutableLiveData<>();
  private MutableLiveData<Boolean> mBackLiveData = new MutableLiveData<>();
  private MutableLiveData<Pair<Integer, ArrayList<WalletTransactionItemData>>>
      mTransactionLiveData = new MutableLiveData<>();
  private UseCaseHandler mHandler;
  private WalletAddMoneyUseCase mWalletAddMoneyUseCase;
  private String mWalletId = "";
  private String mPageState = "";

  @Inject
  public WalletModel(UseCaseHandler useCaseHandler, WalletAmtUseCase walletAmtUseCase,
      WalletTransactionsUseCase walletTransactionsUseCase,
      WalletAddMoneyUseCase walletAddMoneyUseCase) {
    this.mHandler = useCaseHandler;
    this.mWalletAmtUseCase = walletAmtUseCase;
    this.mWalletTransactionsUseCase = walletTransactionsUseCase;
    this.mWalletAddMoneyUseCase = walletAddMoneyUseCase;
  }

  /**
   * call the get wallet amount  api
   */
  void callGetWalletApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<WalletAmtUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<WalletAmtUseCase.ResponseValues>() {
          @Override
          public void onSuccess(WalletAmtUseCase.ResponseValues responseValues) {
            String balance = "$0.00";
            if (responseValues.getData().getWalletData() != null
                && responseValues.getData().getWalletData().size() > ZERO) {
              balance = String.format("%s %s",
                  responseValues.getData().getWalletData().get(ZERO).getCurrency(),
                  String.format("%.2f", Float.parseFloat(
                      responseValues.getData().getWalletData().get(ZERO).getBalance())));
              mWalletId = responseValues.getData().getWalletData().get(ZERO).getWalletid();
            }
            mWalletData.setValue(balance);
            EcomUtil.printLog("exe" + "mWalletId" + mWalletId);
            if (mWalletId.length() > ZERO) {
              callGetWalletTransactionsApi();
            } else {
              progressVisible.set(FALSE);
              mEmptyTransations.setValue(TRUE);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("wallet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mWalletAmtUseCase,
        new WalletAmtUseCase.RequestValues(WALLET_TYPE),
        disposableSingleObserver);
  }

  /**
   * call the get reasons order  api
   */
  void callGetWalletTransactionsApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<WalletTransactionsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<WalletTransactionsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(WalletTransactionsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mPageState = responseValues.getData().getPageState();
            if (responseValues.getData() != null && responseValues.getData().getData() != null
                && responseValues.getData().getData().size() > ZERO) {
              mTransactionLiveData.postValue(
                  Pair.create(Integer.parseInt(responseValues.getData().getTotalCount()),
                      responseValues.getData().getData()));
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Wallet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mWalletTransactionsUseCase,
        new WalletTransactionsUseCase.RequestValues(mWalletId, TWENTY, mPageState),
        disposableSingleObserver);
  }

  /**
   * call the add wallet amount  api
   */
  void callAddMoneyToWalletApi(String cardId, String currency, String amount) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<WalletAddMoneyUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<WalletAddMoneyUseCase.ResponseValues>() {
          @Override
          public void onSuccess(WalletAddMoneyUseCase.ResponseValues responseValues) {
            mMsg.setValue(String.format("%s %s %s %s",
                ApplicationManager.getInstance().getString(R.string.added), currency, amount,
                ApplicationManager.getInstance().getString(R.string.addMoneySuccess)));
            callGetWalletApi();
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("wallet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mWalletAddMoneyUseCase,
        new WalletAddMoneyUseCase.RequestValues(cardId, currency, amount),
        disposableSingleObserver);
  }

  /**
   * notify wallet data comes
   *
   * @return mutable live data of strings
   */
  MutableLiveData<String> getWalletData() {
    return mWalletData;
  }

  /**
   * notify wallet data comes
   **/
  MutableLiveData<Boolean> getEmptyTransactions() {
    return mEmptyTransations;
  }

  /**
   * notify wallet transaction data comes
   *
   * @return mutable live data of transactions
   */
  MutableLiveData<Pair<Integer, ArrayList<WalletTransactionItemData>>> getTransactionLIveData() {
    return mTransactionLiveData;
  }

  /**
   * notify wallet data comes
   *
   * @return mutable live data of strings
   */
  MutableLiveData<Boolean> setBackClick() {
    return mBackLiveData;
  }

  MutableLiveData<String> getMsg() {
    return mMsg;
  }

  @Override
  public void backButtonClickListener(View view) {
    mBackLiveData.postValue(TRUE);
  }
}
