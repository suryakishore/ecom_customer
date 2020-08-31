package com.customer.fivecanale.landing.profile;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.WALLET_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.LogoutUseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.UserData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/** This is used to solve logical conditions of profile fragment */
public class ProfileViewModel extends ViewModel {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<String> walletAmt = new ObservableField<>();

  private MutableLiveData<ProfileUiAction> mProfileUiActionMutableLiveData =
      new MutableLiveData<>();
  private MutableLiveData<UserData> mUserDataMutableLiveData = new MutableLiveData<>();
  private LogoutUseCase mLogoutUseCase;
  private UseCaseHandler mCaseHandler;
  private WalletAmtUseCase mWalletAmtUseCase;

  @Inject
  public ProfileViewModel(LogoutUseCase logoutUseCase, WalletAmtUseCase walletAmtUseCase, UseCaseHandler handler) {
    this.mLogoutUseCase = logoutUseCase;
    this.mWalletAmtUseCase = walletAmtUseCase;
    this.mCaseHandler = handler;
  }

  /** This method is using for subscribing to User mData */
  void onProfileUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getUserDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                userData -> {
                  mUserDataMutableLiveData.postValue(userData);
                }));
  }

  /**
   * This method is using as
   */
  void subscribeToWalletAmtChangeListener() {
    mUserInfoHandler.getWalletAmtObservable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(value -> {
          if (value) {
            callGetWalletApi();
          }
        });
  }

  /**
   * call the get wallet amount  api
   */
  private void callGetWalletApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<WalletAmtUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<WalletAmtUseCase.ResponseValues>() {
          @Override
          public void onSuccess(WalletAmtUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            String balance = "";
            if (responseValues.getData().getWalletData() != null
                && responseValues.getData().getWalletData().size() > ZERO) {
              balance = String.format("%s %s",
                  responseValues.getData().getWalletData().get(ZERO).getCurrency(),
                  String.format("%.2f", Float.parseFloat(
                      responseValues.getData().getWalletData().get(ZERO).getBalance())));
              walletAmt.set(balance);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("wallet Fail" + e.getMessage());
          }
        };
    mCaseHandler.execute(mWalletAmtUseCase,
        new WalletAmtUseCase.RequestValues(WALLET_TYPE),
        disposableSingleObserver);
  }

  /**
   * This method is using to make LogOut API call
   *
   * @param ipAddress device ipAddress
   * @param lat       device Latitude
   * @param lng       device longitude
   */
  public void logout(String ipAddress, double lat, double lng) {
    DisposableSingleObserver<LogoutUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<LogoutUseCase.ResponseValues>() {
          @Override
          public void onSuccess(LogoutUseCase.ResponseValues responseValues) {
            walletAmt.set("");
          }

          @Override
          public void onError(Throwable e) {
          }
        };
    mCaseHandler.execute(
        mLogoutUseCase,
        new LogoutUseCase.RequestValues(ipAddress, lat, lng),
        disposableSingleObserver);
  }

  /** This method is using to emit the User Data */
  void getUserProfileInfo() {
    mUserInfoHandler.getUserData();
  }

  /**
   * This method is using to get user info live Data Object
   *
   * @return user info live mData object
   */
  MutableLiveData<UserData> getUserHandleLiveData() {
    return mUserDataMutableLiveData;
  }

  /**
   * This method is using to get Ui action emitter Observable
   *
   * @return Ui action Emitter observable
   */
  MutableLiveData<ProfileUiAction> getUiActionLiveData() {
    return mProfileUiActionMutableLiveData;
  }
}