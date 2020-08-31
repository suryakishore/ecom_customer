package com.customer.fivecanale.boarding.login;

import static com.customer.fivecanale.boarding.login.LoginUiAction.CREATE_ACCOUNT;
import static com.customer.fivecanale.boarding.login.LoginUiAction.CROSS_ICON;
import static com.customer.fivecanale.boarding.login.LoginUiAction.FACEBOOK_LOGIN;
import static com.customer.fivecanale.boarding.login.LoginUiAction.FINISH;
import static com.customer.fivecanale.util.EcomConstants.BROWSER_NAME;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAN;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAT;
import static com.customer.fivecanale.util.EcomConstants.DEVICE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FB_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.GOOGLE_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.MOBILE_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.PASSWORD_VALID_LENGTH;
import static com.customer.fivecanale.util.EcomConstants.PHONE_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.SignInUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.EcomUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.HashMap;
import java.util.Objects;
import javax.inject.Inject;

/**
 * <h1>EcomLoginViewModel</h1>
 * <p>This class represents the viewModel class for forgotPassword.in this class we are listening
 * the textChanges and focus changes and onclick of the login activity</p>
 */
public class EcomLoginViewModel extends ViewModel {
  public ObservableField progressVisible, backgroundColor, passwordShowIcon;
  public ObservableField errorEmailMsg;
  public ObservableField btnEnabled, errorPhoneNumMsg;
  boolean mIsValidPhoneNum;
  private String mDeviceId = "";
  private String mDeviceIpAddress = "";
  private MutableLiveData<HashMap<String, String>> mHashMapMutableLiveData =
      new MutableLiveData<>();
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private MutableLiveData<LoginUiAction> mLiveData = new MutableLiveData<LoginUiAction>();
  private MutableLiveData<Boolean> mValidateShowLiveData = new MutableLiveData<>();
  private SignInUseCase mSignInUseCase;
  private UseCaseHandler mHandler;
  @Inject
  public UserInfoHandler mUserInfoHandler;
  private String mUserEmail = "", mUserPassword = "";

  /**
   * <h1>EcomLoginViewModel</h1>
   *
   * @param signInUseCase this param is used to get the mSignInUseCase to call the signIn API
   * @param handler       this param is used to execute the particular use case.
   */
  @Inject
  public EcomLoginViewModel(SignInUseCase signInUseCase, UseCaseHandler handler) {
    this.mSignInUseCase = signInUseCase;
    this.mHandler = handler;
    initialization();
    progressVisible = new ObservableField<>(FALSE);
    errorEmailMsg = new ObservableField<>(FALSE);
    btnEnabled = new ObservableField<>(FALSE);
    errorPhoneNumMsg = new ObservableField<>(FALSE);
    backgroundColor = new ObservableField<>(FALSE);
    passwordShowIcon = new ObservableField<>(FALSE);
  }

  /**
   * for getting device details
   *
   * @param deviceId        [Id of device]
   * @param deviceIpAddress [IP of device]
   */
  void getDeviceDetails(String deviceId, String deviceIpAddress) {
    this.mDeviceId = deviceId;
    this.mDeviceIpAddress = deviceIpAddress;
  }

  /**
   * <h2>initialization</h2>
   * <p>This method is used to initialize the observable values</p>
   */
  private void initialization() {
    mValidateShowLiveData.setValue(TRUE);
  }

  /**
   * <h2>callEmailOrPhoneNumSignInApi</h2>
   * <p>calls login API</p>
   *
   * @param mobile      mobile number
   * @param countryCode country code
   * @param email       email
   * @param password    password
   * @param verifyType  verify type
   */
  void callEmailOrPhoneNumSignInApi(String mobile, String countryCode, String email,
      String password, int verifyType, int loginType, String googleId, String facebookId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<SignInUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<SignInUseCase.ResponseValues>() {
          @SuppressLint("DefaultLocale")
          @Override
          public void onSuccess(SignInUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Mobile success" + responseValues.getData().getOtpId());
            switch (verifyType) {
              case MOBILE_LOGIN:
                HashMap<String, String> map = new HashMap<>();
                map.put(OTP_ID, responseValues.getData().getOtpId());
                map.put(OTP_EXPIRY_TIME,
                    String.format("%d", responseValues.getData().getOtpExpiryTime()));
                mHashMapMutableLiveData.postValue(map);
                break;
              case EMAIL_LOGIN:
                FirebaseMessaging.getInstance().subscribeToTopic(
                    responseValues.getData().getFcmTopic());
                mLiveData.postValue(CROSS_ICON);
                break;
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            if (loginType == GOOGLE_LOGIN || loginType == FB_LOGIN) {
              mLiveData.postValue(CREATE_ACCOUNT);
            } else {
              mValidateOnErrorLiveData.postValue(
                  mobile.isEmpty() ? String.valueOf(EMAIL_DIALOG_TYPE)
                      : String.valueOf(PHONE_DIALOG_TYPE));
            }
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
          }
        };
    mHandler.execute(mSignInUseCase,
        new SignInUseCase.RequestValues(mobile, countryCode, email, password, verifyType,
            loginType, mDeviceId, DEVICE_TYPE, mDeviceIpAddress, googleId, facebookId,
            Build.MANUFACTURER, Build.MODEL, BROWSER_NAME,
            String.valueOf(Build.VERSION.SDK_INT), String.valueOf(CURRENT_LAT),
            String.valueOf(CURRENT_LAN), ApplicationManager.getInstance().mVersion),
        disposableSingleObserver);
  }

  /**
   * <h2>onUserEmailChanged</h2>
   * handle text changes of email
   */
  public void onUserEmailChanged(CharSequence s, int start, int before, int count) {
    mUserEmail = s.toString();
    errorEmailMsg.set(FALSE);
    btnEnabled.set(
        EcomUtil.isEmail(mUserEmail) && (mUserPassword.length() >= PASSWORD_VALID_LENGTH));
  }

  /**
   * <h2>onEmailFocus</h2>
   * handle focus loose of email
   */
  public void onEmailFocus() {
    if (!EcomUtil.isEmail(
        Objects.requireNonNull(mUserEmail))) {
      errorEmailMsg.set(TRUE);
    }
  }

  /**
   * <h2>onPasswordFocus</h2>
   * handle focus loose of password
   */
  public void onPasswordFocus() {
    passwordShowIcon.set((mUserPassword != null && mUserPassword.length() > ZERO));
  }

  /**
   * <h2>onPasswordHasFocus</h2>
   * handle has focus of password
   */
  public void onPasswordHasFocus() {
    passwordShowIcon.set(TRUE);
  }

  /**
   * <h2>onPhoneNumberFocus</h2>
   * handle focus loose of phone number
   */
  public void onPhoneNumberFocus() {
    backgroundColor.set(FALSE);
  }

  /**
   * <h2>onPhoneNumberHasFocus</h2>
   * handle has focus of phone number
   */
  public void onPhoneNumberHasFocus() {
    backgroundColor.set(TRUE);
  }

  /**
   * <h2>onTextChangePhoneNumber</h2>
   * handle text changes of phone number
   */
  public void onTextChangePhoneNumber(CharSequence s, int start, int before, int count) {
    errorPhoneNumMsg.set(FALSE);
    btnEnabled.set(mIsValidPhoneNum);
  }

  /**
   * <h2>onTextChangePassword</h2>
   * handle text changes of password
   */
  public void onTextChangePassword(CharSequence s, int start, int before, int count) {
    mUserPassword = s.toString();
    btnEnabled.set(mUserPassword.length() >= PASSWORD_VALID_LENGTH && EcomUtil.isEmail(
        Objects.requireNonNull(mUserEmail)));
  }

  /**
   * <h2>onLoginButtonClicked</h2>
   * <p>this method is used listen when login button clicked.</p>
   */
  public void onLoginButtonClicked() {
    mLiveData.postValue(LoginUiAction.LOGIN);
  }

  /**
   * <h2>onPhoneIconClicked</h2>
   * <p>this method is used listen when phone icon clicked.</p>
   */
  public void onPhoneIconClicked() {
    mLiveData.postValue(LoginUiAction.PHONE_LOGIN);
  }

  /**
   * <h2>onForgotPasswordClicked</h2>
   * <p>this method is used listen when forgot password icon clicked.</p>
   */
  public void onForgotPasswordClicked() {
    mLiveData.postValue(LoginUiAction.FORGOT_PASSWORD);
  }

  /**
   * <h2>onCrossIconClicked</h2>
   * <p>this method is used listen when cross icon clicked.</p>
   */
  public void onCrossIconClicked() {
    mLiveData.postValue(FINISH);
  }

  /**
   * <p>this method is used listen when google icon clicked.</p>
   */
  public void onGoogleLoginClicked() {
    mLiveData.postValue(LoginUiAction.GOOGLE_LOGIN);
  }

  /**
   * <p>this method is used listen when facebook icon clicked.</p>
   */
  public void onFaceBookLoginClicked() {
    mLiveData.postValue(FACEBOOK_LOGIN);
  }

  /**
   * <h2>onCreateNewAccountClicked</h2>
   * <p>this method is used listen when create new  text clicked.</p>
   */
  public void onCreateNewAccountClicked() {
    mLiveData.postValue(CREATE_ACCOUNT);
  }

  /**
   * <h2>onTextShowClicked</h2>
   * <p>this method is used listen when show text clicked.</p>
   */
  public void onTextShowClicked() {
    mValidateShowLiveData.setValue(!mValidateShowLiveData.getValue());
  }

  /**
   * <h2>onVisibleOtpViews</h2>
   * notify activity when phone icon clicked
   */
  MutableLiveData<HashMap<String, String>> onVisibleOtpViews() {
    return mHashMapMutableLiveData;
  }

  /**
   * <h2>onClick</h2>
   * notify activity when a view is  clicked
   */
  public MutableLiveData<LoginUiAction> onClick() {
    return mLiveData;
  }

  /**
   * <h2>onShowTextClicked</h2>
   * notify activity when show text clicked
   */
  MutableLiveData<Boolean> onShowTextClicked() {
    return mValidateShowLiveData;
  }

  /**
   * <h2>onError</h2>
   * notify when onError comes
   */
  MutableLiveData<String> onError() {
    return mValidateOnErrorLiveData;
  }
}
