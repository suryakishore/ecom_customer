package com.customer.fivecanale.payment;

import static com.appscrip.stripe.Constants.ENGLISH;
import static com.customer.fivecanale.payment.PaymentUiAction.ADD_CARD;
import static com.customer.fivecanale.payment.PaymentUiAction.ADD_MONEY;
import static com.customer.fivecanale.payment.PaymentUiAction.BACK;
import static com.customer.fivecanale.payment.PaymentUiAction.CARD;
import static com.customer.fivecanale.payment.PaymentUiAction.CASH;
import static com.customer.fivecanale.payment.PaymentUiAction.DISABLE;
import static com.customer.fivecanale.payment.PaymentUiAction.ENABLE;
import static com.customer.fivecanale.payment.PaymentUiAction.ENABLE_CARDS;
import static com.customer.fivecanale.payment.PaymentUiAction.WALLET;
import static com.customer.fivecanale.payment.PaymentUiAction.WALLET_PLUS_CARD;
import static com.customer.fivecanale.payment.PaymentUiAction.WALLET_PLUS_CASH;
import static com.customer.fivecanale.util.EcomConstants.EMPTY_STRING;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.POINT_ZERO;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.WALLET_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.appscrip.stripe.AccountsDelegate;
import com.appscrip.stripe.UserAccounts;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.TransactionEstimateUseCase;
import com.customer.domain.interactor.WalletAddMoneyUseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.savedcards.SavedCardsData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.observers.DisposableSingleObserver;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

/**
 * provides the logical part of payment screen
 */
public class PaymentMethodViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> payOnDeliveryState = new ObservableField<>(FALSE);
  public ObservableField<Boolean> payFromWallet = new ObservableField<>(FALSE);
  public ObservableField<Boolean> btnEnabled = new ObservableField<>(FALSE);
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  @Inject
  public UserInfoHandler mUserInfoHandler;
  boolean mIsChecked;
  float amtToBePaid;
  String toCurrency;
  String comingFrom = EMPTY_STRING;
  float walletBal;
  private MutableLiveData<PaymentUiAction> mClick = new MutableLiveData<>();
  private WalletAmtUseCase mWalletAmtUseCase;
  private ArrayList<SavedCardsData> mSavedCardsData = new ArrayList<>();
  private MutableLiveData<String> mWalletData = new MutableLiveData<>();
  private MutableLiveData<ArrayList<SavedCardsData>> mSavedCardsLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mPaymentDisSelectData = new MutableLiveData<>();
  private UseCaseHandler mHandler;
  private WalletAddMoneyUseCase mWalletAddMoneyUseCase;
  private TransactionEstimateUseCase mTransactionEstimateUseCase;
  private boolean mIsToCallWallet = TRUE;

  @Inject
  public PaymentMethodViewModel(UseCaseHandler useCaseHandler,
      WalletAddMoneyUseCase walletAddMoneyUseCase, WalletAmtUseCase walletAmtUseCase,
      TransactionEstimateUseCase transactionEstimateUseCase) {
    mWalletAddMoneyUseCase = walletAddMoneyUseCase;
    mWalletAmtUseCase = walletAmtUseCase;
    mTransactionEstimateUseCase = transactionEstimateUseCase;
    mHandler = useCaseHandler;
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
            mIsToCallWallet = FALSE;
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
   * call the get wallet amount  api
   */
  void callGetWalletApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<WalletAmtUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<WalletAmtUseCase.ResponseValues>() {
          @Override
          public void onSuccess(WalletAmtUseCase.ResponseValues responseValues) {
            String balance = "";
            if (responseValues.getData().getWalletData() != null
                && responseValues.getData().getWalletData().size() > ZERO) {
              walletBal = Float.parseFloat(
                  responseValues.getData().getWalletData().get(ZERO).getBalance());
              balance = String.format("%s %s",
                  responseValues.getData().getWalletData().get(ZERO).getCurrency(),
                  String.format("%.2f", walletBal));
              EcomUtil.printLog("exe" + "currency" + responseValues.getData().getWalletData().get(
                  ZERO).getCurrency());
              if (toCurrency != null && Float.parseFloat(
                  responseValues.getData().getWalletData().get(ZERO).getBalance()) != POINT_ZERO) {
                if (!responseValues.getData().getWalletData().get(ZERO).getCurrency().equals(
                    toCurrency)) {
                  callGetWalletEstimateApi(
                      responseValues.getData().getWalletData().get(ZERO).getCurrency(), toCurrency,
                      Float.parseFloat(
                          responseValues.getData().getWalletData().get(ZERO).getBalance())
                  );
                }
              } else {
                if (balance.length() > ZERO) {
                  mWalletData.setValue(balance);
                }
              }
            }
            if (mIsToCallWallet) {
              getCards();
            } else {
              progressVisible.set(FALSE);
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
   * call the get wallet amount  api
   */
  private void callGetWalletEstimateApi(String fromCurrency, String toCurrency, float amount) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<TransactionEstimateUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<TransactionEstimateUseCase.ResponseValues>() {
          @Override
          public void onSuccess(TransactionEstimateUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog(
                "exe" + "wallet Sucess" + responseValues.getData().getEstimateAmount());
            if (responseValues.getData() != null) {
              walletBal =
                  Float.parseFloat(responseValues.getData().getEstimateAmount());
              String balance = String.format("%s %s",
                  toCurrency,
                  String.format("%.2f", walletBal));
              mWalletData.setValue(balance);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("wallet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mTransactionEstimateUseCase,
        new TransactionEstimateUseCase.RequestValues(fromCurrency, toCurrency, amount),
        disposableSingleObserver);
  }

  /**
   * used to get the cards
   */
  void getCards() {
    progressVisible.set(TRUE);
    UserAccounts.INSTANCE.getCards(ApplicationManager.getInstance(), mUserInfoHandler.getToken(),
        ENGLISH, mUserInfoHandler.userId(), new AccountsDelegate() {
          @Override
          public void onSuccess(@NotNull Object successData) {
            progressVisible.set(FALSE);
            JSONArray jsonArray = (JSONArray) successData;
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<SavedCardsData>>() {
            }.getType();
            mSavedCardsData = gson.fromJson(jsonArray.toString(), listType);
            mSavedCardsLiveData.postValue(mSavedCardsData);
          }

          @Override
          public void onFailure(@NotNull String failure) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("exe" + "failure" + failure);
          }
        });
  }

  @Override
  public void backButtonClickListener(View view) {
    mClick.postValue(BACK);
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
   * listens change address clicked.
   */
  public void continueClick() {
    if (payFromWallet.get() && payOnDeliveryState.get()) {
      mClick.postValue(WALLET_PLUS_CASH);
    } else if (payFromWallet.get() && mIsChecked) {
      mClick.postValue(WALLET_PLUS_CARD);
    } else if (mIsChecked) {
      mClick.postValue(CARD);
    } else if (payFromWallet.get()) {
      mClick.postValue(WALLET);
    } else if (payOnDeliveryState.get()) {
      mClick.postValue(CASH);
    }
  }

  /**
   * set the  button status on select the card
   */
  void selectCardButtonStatus() {
    if (walletBal < amtToBePaid) {
      btnEnabled.set(payFromWallet.get()
          && payOnDeliveryState.get() ? TRUE : FALSE);
    } else {
      btnEnabled.set(payFromWallet.get()
          || payOnDeliveryState.get() ? TRUE : FALSE);
    }
  }

  /**
   * listens add money clicked.
   */
  public void addMoneyClick() {
    mClick.postValue(ADD_MONEY);
  }

  /**
   * listens add money clicked.
   */
  public void addCardClick() {
    mClick.postValue(ADD_CARD);
  }

  /**
   * listens change address clicked.
   */
  public void payOnDeliveryClick() {
    if (mIsChecked) {
      mPaymentDisSelectData.setValue(TRUE);
      if (amtToBePaid < walletBal) {
        mClick.setValue(ENABLE_CARDS);
      }
    }
    payOnDeliveryState.set(payOnDeliveryState.get() ? FALSE : TRUE);
    if (amtToBePaid > walletBal) {
      mClick.setValue(ENABLE_CARDS);
    }
    btnEnabled.set(payOnDeliveryState.get());
  }

  /**
   * listens change address clicked.
   */
  public void walletClick() {
    if (mIsChecked) {
      if (amtToBePaid < walletBal) {
        mPaymentDisSelectData.setValue(TRUE);
      }
    }
    if (amtToBePaid < walletBal) {
      payOnDeliveryState.set(FALSE);
    }
    payFromWallet.set(payFromWallet.get() ? FALSE : TRUE);
    if (payFromWallet.get()) {
      mClick.setValue(amtToBePaid < walletBal ? DISABLE : ENABLE);
    } else {
      mClick.setValue(ENABLE);
    }
    if (amtToBePaid > walletBal) {
      if (payOnDeliveryState.get()) {
        btnEnabled.set(TRUE);
      }
    } else {
      btnEnabled.set(payFromWallet.get() || payOnDeliveryState.get());
    }
  }

  /**
   * notify saved cards data comes
   *
   * @return mutable live data of saved cards
   */
  MutableLiveData<ArrayList<SavedCardsData>> getSavedCardsLIveData() {
    return mSavedCardsLiveData;
  }

  /**
   * notify saved cards data comes
   *
   * @return mutable live data of saved cards
   */
  MutableLiveData<Boolean> getDisSelectLIveData() {
    return mPaymentDisSelectData;
  }

  /**
   * returns live data clicking.
   */
  MutableLiveData<PaymentUiAction> onClick() {
    return mClick;
  }
}