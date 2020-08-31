package com.customer.fivecanale.boarding.forgotpassword;

import static com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordUiAction.FORGOT_PASSWORD;
import static com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordUiAction.OTP_FIRST_TEXT_CHANGE;
import static com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordUiAction.OTP_FOUR_TEXT_CHANGE;
import static com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordUiAction.OTP_SECOND_TEXT_CHANGE;
import static com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordUiAction.OTP_THIRD_TEXT_CHANGE;
import static com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordUiAction.RESEND;
import static com.customer.fivecanale.util.EcomConstants.ANDROID_USER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.BROWSER_NAME;
import static com.customer.fivecanale.util.EcomConstants.CONFIRM_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAN;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAT;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.CUSTOMER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.CUSTOMER_VERIFICATION_CODE;
import static com.customer.fivecanale.util.EcomConstants.DEVICE_BRAND;
import static com.customer.fivecanale.util.EcomConstants.DEVICE_MODEL;
import static com.customer.fivecanale.util.EcomConstants.DEVICE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_VERIFY;
import static com.customer.fivecanale.util.EcomConstants.E_MAIL;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.MOBILE_VERIFY;
import static com.customer.fivecanale.util.EcomConstants.NEW_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.PASSWORD_VALID_LENGTH;
import static com.customer.fivecanale.util.EcomConstants.SIGN_UP_TYPE;
import static com.customer.fivecanale.util.EcomConstants.TERMS_CONDITIONS;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_MOBILE;
import static com.customer.fivecanale.util.EcomConstants.USER_NAME;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.util.Pair;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.ForgotPasswordUseCase;
import com.customer.domain.interactor.ResetPasswordUseCase;
import com.customer.domain.interactor.SendOtpToUpdateProfileUseCase;
import com.customer.domain.interactor.SendOtpUseCase;
import com.customer.domain.interactor.VerifyOTPUseCase;
import com.customer.domain.interactor.VerifyProfileOtpUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.interactor.signup.SignUpUseCase;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.EcomUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.HashMap;
import java.util.Objects;
import javax.inject.Inject;

/**
 * <p>This class represents the viewModel class for forgotPassword.in this class we are listening
 * the textChanges and focus changes and onclick of the forgotPassword</p>
 */
public class EcomForgotPasswordViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible, errorEmailMsg, errorPhoneNum, errorNewPassMsg,
      errorCurrentPassMsg,
      errorRepeatPassMsg;
  public ObservableField btnEnabled;
  public ObservableField passWordShowIcon, currentPassWordShowIcon, rePeatPassWordShowIcon;
  public ObservableBoolean isMinPassVisible;
  @Inject
  public UserInfoHandler mUserInfoHandler;
  boolean mIsFromUpdateProfile;
  Boolean mIsPhoneNumValid;
  String mCountryCode, mProfilePic;
  boolean mIsForGotThroughEmail, mIsToForgot;
  private MutableLiveData<ForgotPasswordUiAction> mMutableLiveOnTextChangeData =
      new MutableLiveData<>();
  private MutableLiveData<Pair<Boolean, HashMap<String, Object>>> mValidateFpDialogLiveData =
      new MutableLiveData<>();
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidateOnSuccessLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidateNewPassLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidateHomeScreenData = new MutableLiveData<>();
  private MutableLiveData<ForgotPasswordUiAction> mMutableOnClickLiveData =
      new MutableLiveData<>();
  private MutableLiveData<Pair<Integer, Boolean>> mValidatePasswordEyeIconLiveData =
      new MutableLiveData<>();
  private ForgotPasswordUseCase mForgotPasswordUseCase;
  private UseCaseHandler mHandler;
  private String mDeviceId = "";
  private String mDeviceIPAddress = "";
  private VerifyOTPUseCase mVerifyOTPUseCase;
  private SignUpUseCase mSignUpUseCase;
  private ResetPasswordUseCase mResetPasswordUseCase;
  private SendOtpUseCase mSendOTPUseCase;
  private String mCurrentPassword = "", mNewPassword = "", mRePeatPassword = "";
  private String mOtpFir = "", mOtpSec = "", mOtpThi = "", mOtpFour = "";
  private String mUserEmail = "";
  private VerifyProfileOtpUseCase mVerifyProfileOtpUseCase;
  private SendOtpToUpdateProfileUseCase mSendOtpToUpdateProfileUseCase;
  private boolean mIsFromLogin, mIsToShowOtp, mVerifyOtp;
  private String mOtpId, mName, mPhoneNumber, mEmailId, mPassword;

  /**
   * @param signUpUseCase         this param is used to get the mSignUpUseCase to call the signUP
   *                              API
   * @param sendOTPUseCase        this param is used to get the sendOtpUseCase to call the sendOtp
   *                              API
   * @param forgotPasswordUseCase this param is used to get the mForgotPasswordUseCase to call the
   *                              forgotPassword API
   * @param verifyOTPUseCase      this param is used to get the mVerifyOTPUseCase to call the
   *                              verifyOTP API
   * @param resetPasswordUseCase  this param is used to get the mResetPasswordUseCase to call the
   *                              resetPassword API
   * @param handler               this param is used to execute the particular use case.
   */
  @Inject
  public EcomForgotPasswordViewModel(SignUpUseCase signUpUseCase, SendOtpUseCase sendOTPUseCase,
      ForgotPasswordUseCase forgotPasswordUseCase, VerifyOTPUseCase verifyOTPUseCase,
      ResetPasswordUseCase resetPasswordUseCase, UseCaseHandler handler,
      VerifyProfileOtpUseCase verifyProfileOtpUseCase,
      SendOtpToUpdateProfileUseCase sendOtpToUpdateProfileUseCase) {
    this.mSignUpUseCase = signUpUseCase;
    this.mSendOTPUseCase = sendOTPUseCase;
    this.mForgotPasswordUseCase = forgotPasswordUseCase;
    this.mResetPasswordUseCase = resetPasswordUseCase;
    this.mVerifyProfileOtpUseCase = verifyProfileOtpUseCase;
    this.mSendOtpToUpdateProfileUseCase = sendOtpToUpdateProfileUseCase;
    this.mHandler = handler;
    this.mVerifyOTPUseCase = verifyOTPUseCase;
    progressVisible = new ObservableField<>(FALSE);
    errorEmailMsg = new ObservableField<>(FALSE);
    isMinPassVisible = new ObservableBoolean(FALSE);
    errorPhoneNum = new ObservableField<>(FALSE);
    errorNewPassMsg = new ObservableField<>(FALSE);
    errorRepeatPassMsg = new ObservableField<>(FALSE);
    errorCurrentPassMsg = new ObservableField<>(FALSE);
    btnEnabled = new ObservableField<>(FALSE);
    passWordShowIcon = new ObservableField<>(FALSE);
    rePeatPassWordShowIcon = new ObservableField<>(FALSE);
    currentPassWordShowIcon = new ObservableField<>(FALSE);
  }

  /**
   * <P>for getting device details</P>
   *
   * @param deviceId        [Id of device]
   * @param deviceIPAddress [ip of device]
   */
  void getDeviceDetails(
      String deviceId,
      String deviceIPAddress) {
    this.mDeviceId = deviceId;
    this.mDeviceIPAddress = deviceIPAddress;
    mValidatePasswordEyeIconLiveData.setValue(Pair.create(CURRENT_PASSWORD, TRUE));
    mValidatePasswordEyeIconLiveData.setValue(Pair.create(NEW_PASSWORD, TRUE));
    mValidatePasswordEyeIconLiveData.setValue(Pair.create(CONFIRM_PASSWORD, TRUE));
  }

  /**
   * get the data from the forgot password activity
   *
   * @param isFromLogin whether we are opening from the login screen or not
   * @param isToShowOtp this activity opens for the showing the otp
   * @param otpId       opt id
   * @param name        name
   * @param phoneNumber phone number
   * @param countryCode country code
   * @param emailId     email id
   * @param password    password
   * @param verifyOtp   verify otp from login screen or update phonenumber screen.
   */
  void setIntentData(boolean isFromLogin, boolean isToShowOtp, String otpId, String name,
      String phoneNumber, String countryCode, String emailId, String password, boolean verifyOtp) {
    this.mIsFromLogin = isFromLogin;
    this.mIsToShowOtp = isToShowOtp;
    this.mOtpId = otpId;
    this.mName = name;
    this.mPhoneNumber = phoneNumber;
    this.mCountryCode = countryCode;
    this.mEmailId = emailId;
    this.mPassword = password;
    this.mVerifyOtp = verifyOtp;
  }

  /**
   * calls the forgot password api
   *
   * @param mobile      mobile number
   * @param countryCode country code
   * @param email       email
   * @param verifyType  verify type whether it is through email or phone.
   */
  void callForgotPasswordApi(String mobile, String countryCode, String email, int verifyType) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ForgotPasswordUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ForgotPasswordUseCase.ResponseValues>() {
          @SuppressLint("DefaultLocale")
          @Override
          public void onSuccess(ForgotPasswordUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            HashMap<String, Object> map = new HashMap<>();
            if (verifyType == ONE) {
              map.put(E_MAIL, email);
              mValidateFpDialogLiveData.postValue(Pair.create(TRUE, map));
            } else {
              map.put(OTP_ID, responseValues.getData().getOtpId());
              map.put(OTP_EXPIRY_TIME, responseValues.getData().getOtpExpiryTime());
              mValidateFpDialogLiveData.setValue(Pair.create(FALSE, map));
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
          }
        };
    mHandler.execute(mForgotPasswordUseCase,
        new ForgotPasswordUseCase.RequestValues(verifyType, email, countryCode, mobile,
            mDeviceId, DEVICE_BRAND, DEVICE_MODEL,
            "" + ZERO), disposableSingleObserver);
  }

  /**
   * this method is used to show the password views
   */
  void showResetPass() {
    mValidateNewPassLiveData.postValue(TRUE);
  }

  /**
   * <p>sends the otp</p>
   *
   * @param mobile      mobile number
   * @param countryCode country code.
   */
  void callSendOtpApi(String mobile, String countryCode, int type) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<SendOtpUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<SendOtpUseCase.ResponseValues>() {
          @SuppressLint("DefaultLocale")
          @Override
          public void onSuccess(SendOtpUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("SendOtp Success" + responseValues.getData().getOtpId());
            HashMap<String, Object> map = new HashMap<>();
            map.put(OTP_ID, responseValues.getData().getOtpId());
            map.put(OTP_EXPIRY_TIME, responseValues.getData().getOtpExpiryTime());
            mValidateFpDialogLiveData.setValue(Pair.create(FALSE, map));
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
          }
        };
    mHandler.execute(mSendOTPUseCase,
        new SendOtpUseCase.RequestValues(mobile, countryCode, "", MOBILE_VERIFY,
            CUSTOMER_VERIFICATION_CODE, USER_NAME, type), disposableSingleObserver);
  }

  /**
   * <p>sends the otp</p>
   *
   * @param mobile      mobile number
   * @param countryCode country code.
   */
  void callVerifyProfileOtpApi(String mobile, String otpId, String otpCode, String countryCode,
      int type) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<VerifyProfileOtpUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<VerifyProfileOtpUseCase.ResponseValues>() {
          @SuppressLint("DefaultLocale")
          @Override
          public void onSuccess(VerifyProfileOtpUseCase.ResponseValues responseValues) {
            btnEnabled.set(TRUE);
            progressVisible.set(FALSE);
            mValidateOnSuccessLiveData.postValue(TRUE);
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
            btnEnabled.set(TRUE);
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
          }
        };
    mHandler.execute(mVerifyProfileOtpUseCase,
        new VerifyProfileOtpUseCase.RequestValues(countryCode, otpId, otpCode, mobile, type),
        disposableSingleObserver);
  }

  /**
   * <P>This method is used call the verify otp api.</P>a
   *
   * @param isLogin     if it is from login or not.
   * @param otpViews    This param is used to show the otp views.
   * @param otpId       This param is represents otp id
   * @param otpCode     This param is represents the otp code.
   * @param name        This param is name of the user.
   * @param phoneNumber This param is the phoneNumber
   * @param countryCode mCountryCode
   * @param emailID     emailID
   * @param password    password
   */
  void callVerifyOtpApi(boolean isLogin, boolean otpViews, String otpId, String otpCode,
      String name, String phoneNumber, String countryCode, String emailID,
      String password) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<VerifyOTPUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<VerifyOTPUseCase.ResponseValues>() {
          @Override
          public void onSuccess(VerifyOTPUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            btnEnabled.set(TRUE);
            EcomUtil.printLog("HomeFragment suu");
            if (isLogin) {
              if (responseValues.getData().getFcmTopic() != null) {
                FirebaseMessaging.getInstance().subscribeToTopic(
                    responseValues.getData().getFcmTopic());
              }
              mValidateHomeScreenData.postValue(TRUE);
            } else if (otpViews) {
              callSignUpApi(name, phoneNumber, countryCode, emailID, password, mProfilePic);
            } else {
              mValidateNewPassLiveData.postValue(TRUE);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            btnEnabled.set(TRUE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
          }
        };
    mHandler.execute(mVerifyOTPUseCase,
        new VerifyOTPUseCase.RequestValues(otpId, otpCode,
            MOBILE_VERIFY),
        disposableSingleObserver);
  }

  /**
   * call the product details api
   */
  void callUpdateDetailsApi(String mobNumber, String countryCode) {
    DisposableSingleObserver<SendOtpToUpdateProfileUseCase.ResponseValues>
        disposableSingleObserver =
        new DisposableSingleObserver<SendOtpToUpdateProfileUseCase.ResponseValues>() {
          @Override
          public void onSuccess(SendOtpToUpdateProfileUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("REview Succ");
            HashMap<String, Object> map = new HashMap<>();
            map.put(OTP_ID, responseValues.getData().getOtpId());
            map.put(OTP_EXPIRY_TIME, responseValues.getData().getOtpExpiryTime());
            mValidateFpDialogLiveData.setValue(Pair.create(FALSE, map));
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("REview Fail" + e.getMessage());
          }
        };
    mHandler.execute(mSendOtpToUpdateProfileUseCase,
        new SendOtpToUpdateProfileUseCase.RequestValues(mobNumber, countryCode, UPDATE_MOBILE),
        disposableSingleObserver);
  }

  /**
   * <p>This method is used to call the resetPassword api</p>
   *
   * @param password new password.
   */
  void callResetPasswordApi(String password, String oldPass, int resetType) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ResetPasswordUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ResetPasswordUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ResetPasswordUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mValidateOnSuccessLiveData.postValue(TRUE);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
          }
        };
    mHandler.execute(mResetPasswordUseCase,
        new ResetPasswordUseCase.RequestValues(password, oldPass, resetType),
        disposableSingleObserver);
  }

  /**
   * <p>This method is used to call the signmUp api</p>
   *
   * @param name        name
   * @param mobile      mobile number
   * @param countryCode country code
   * @param email       email
   * @param password    password
   */
  private void callSignUpApi(String name, String mobile, String countryCode, String email,
      String password, String profilePic) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<SignUpUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<SignUpUseCase.ResponseValues>() {
          @Override
          public void onSuccess(SignUpUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mValidateOnSuccessLiveData.postValue(TRUE);
            if (responseValues.getData().getFcmTopic() != null) {
              FirebaseMessaging.getInstance().subscribeToTopic(
                  responseValues.getData().getFcmTopic());
            }
            EcomUtil.printLog("HomeFragment Success" + responseValues.getData().getFcmTopic());
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
          }
        };
    mHandler.execute(mSignUpUseCase,
        new SignUpUseCase.RequestValues(mDeviceId, mobile, countryCode, email, password,
            DEVICE_TYPE, SIGN_UP_TYPE, ANDROID_USER_TYPE, CUSTOMER_TYPE, TERMS_CONDITIONS,
            mDeviceIPAddress, name, "", profilePic, Build.MODEL, Build.MANUFACTURER,
            String.valueOf(System.currentTimeMillis()),
            BROWSER_NAME, String.valueOf(Build.VERSION.SDK_INT), String.valueOf(CURRENT_LAT),
            String.valueOf(CURRENT_LAN), ApplicationManager.getInstance().mVersion),
        disposableSingleObserver);
  }

  /**
   * <P>This method is used listen when a eye icon clicked.</P>
   */
  public void onEyeIconCurrentClicked() {
    mValidatePasswordEyeIconLiveData.setValue(
        Pair.create(CURRENT_PASSWORD, !mValidatePasswordEyeIconLiveData.getValue().second));
  }

  /**
   * <P>This method is used listen when a eye icon clicked.</P>
   */
  public void onEyeIconClicked() {
    mValidatePasswordEyeIconLiveData.setValue(
        Pair.create(NEW_PASSWORD, !mValidatePasswordEyeIconLiveData.getValue().second));
  }

  /**
   * <P>This method is used listen when a eye icon clicked for newResetPassword clicked..</P>
   */
  public void onEyeRepeatPassIconClicked() {
    mValidatePasswordEyeIconLiveData.setValue(
        Pair.create(CONFIRM_PASSWORD, !mValidatePasswordEyeIconLiveData.getValue().second));
  }

  /**
   * <P>This method is used to listen the resend text clicked.</P>
   */
  public void onTextResendClicked() {
    mMutableOnClickLiveData.postValue(RESEND);
  }

  /**
   * handle text changes of email
   */
  public void onUserEmailChanged(CharSequence s, int start, int before, int count) {
    mUserEmail = s.toString().trim();
    btnEnabled.set(EcomUtil.isEmail(s.toString()));
  }

  /**
   * handle text changes of new all_password
   */
  public void onNewPasswordChanged(CharSequence s, int start, int before, int count) {
    mNewPassword = s.toString();
    isMinPassVisible.set(!TextUtils.isEmpty(s.toString()));
    errorNewPassMsg.set(FALSE);
    btnEnabled.set(mIsFromUpdateProfile ? (mNewPassword.length() >= PASSWORD_VALID_LENGTH
        && mRePeatPassword.length() >= PASSWORD_VALID_LENGTH
        && mCurrentPassword.length() >= PASSWORD_VALID_LENGTH)
        : (mNewPassword.length() >= PASSWORD_VALID_LENGTH
            && mRePeatPassword.length() >= PASSWORD_VALID_LENGTH));
  }

  /**
   * handle text changes of new current password
   */
  public void onCurrentPasswordChanged(CharSequence s, int start, int before, int count) {
    mCurrentPassword = s.toString();
    errorCurrentPassMsg.set(FALSE);
    btnEnabled.set(mCurrentPassword.length() >= PASSWORD_VALID_LENGTH
        && mNewPassword.length() >= PASSWORD_VALID_LENGTH
        && mRePeatPassword.length() >= PASSWORD_VALID_LENGTH);
  }

  /**
   * handle text changes of repeat new all_password
   */
  public void onRepeatNewPasswordChanged(CharSequence s, int start, int before, int count) {
    mRePeatPassword = s.toString();
    errorRepeatPassMsg.set(FALSE);
    btnEnabled.set(mIsFromUpdateProfile ? (mNewPassword.length() >= PASSWORD_VALID_LENGTH
        && mRePeatPassword.length() >= PASSWORD_VALID_LENGTH
        && mCurrentPassword.length() >= PASSWORD_VALID_LENGTH)
        : (mNewPassword.length() >= PASSWORD_VALID_LENGTH
            && mRePeatPassword.length() >= PASSWORD_VALID_LENGTH));
  }

  /**
   * handle text changes of otp first
   */
  public void onTextChangeOtpFir(CharSequence s, int start, int before, int count) {
    mOtpFir = s.toString();
    mMutableLiveOnTextChangeData.postValue(OTP_FIRST_TEXT_CHANGE);
    callVerifyOtp();
  }

  /**
   * handle text changes of otp second
   */
  public void onTextChangeOtpSec(CharSequence s, int start, int before, int count) {
    mOtpSec = s.toString();
    mMutableLiveOnTextChangeData.postValue(OTP_SECOND_TEXT_CHANGE);
    callVerifyOtp();
  }

  /**
   * handle text changes of otp third
   */
  public void onTextChangeOtpThi(CharSequence s, int start, int before, int count) {
    mOtpThi = s.toString();
    mMutableLiveOnTextChangeData.postValue(OTP_THIRD_TEXT_CHANGE);
    callVerifyOtp();
  }

  /**
   * handle text changes of otp four
   */
  public void onTextChangeOtpFour(CharSequence s, int start, int before, int count) {
    mOtpFour = s.toString();
    mMutableLiveOnTextChangeData.postValue(OTP_FOUR_TEXT_CHANGE);
    callVerifyOtp();
  }

  /**
   * calling the verify otp api.
   */
  private void callVerifyOtp() {
    //btnEnabled.set(isOtpEntered());
    if (isOtpEntered()) {
      String otpCode =
          String.format("%s%s%s%s", mOtpFir,
              mOtpSec,
              mOtpThi,
              mOtpFour);
      Log.d("exe", "mVerifyOtp" + mVerifyOtp);
      if (!mVerifyOtp) {
        // btnEnabled.set(FALSE);
        callVerifyOtpApi(mIsFromLogin, mIsToShowOtp, mOtpId,
            otpCode, mName, mPhoneNumber, String.format("+%s", mCountryCode), mEmailId,
            mPassword);
      } else {
        // btnEnabled.set(FALSE);
        callVerifyProfileOtpApi(mPhoneNumber, mOtpId, otpCode,
            String.format("+%s", mCountryCode), MOBILE_VERIFY);
      }
    }
  }

  /**
   * handles text changes of phoneNumber
   */
  public void onTextChangePhoneNumber(CharSequence s, int start, int before, int count) {
    errorPhoneNum.set(FALSE);
    btnEnabled.set(mIsPhoneNumValid);
  }

  /**
   * onNewPassword loose focus.
   */
  public void onNewPasswordFocus() {
    errorNewPassMsg.set((mNewPassword != null & !(Objects.requireNonNull(mNewPassword).length()
        >= PASSWORD_VALID_LENGTH)));
    passWordShowIcon.set((mNewPassword.length() > ZERO));
    isMinPassVisible.set((mNewPassword.length() > ZERO));
  }

  /**
   * onCurrentPassword loose focus.
   */
  public void onCurrentPasswordFocus() {
    EcomUtil.printLog("length" + !(mCurrentPassword.length()
        >= PASSWORD_VALID_LENGTH));
    errorCurrentPassMsg.set(!
        (mCurrentPassword.length()
            >= PASSWORD_VALID_LENGTH));
    currentPassWordShowIcon.set((mCurrentPassword.length() > ZERO));
  }

  /**
   * onNewPassword has focus.
   */
  public void onNewPasswordHasFocus() {
    passWordShowIcon.set(TRUE);
  }

  /**
   * onCurrentPassword has focus.
   */
  public void onCurrentPasswordHasFocus() {
    currentPassWordShowIcon.set(TRUE);
  }

  /**
   * onRepeatPassword has focus.
   */
  public void onRepeatPasswordHasFocus() {
    rePeatPassWordShowIcon.set(TRUE);
  }

  /**
   * onRepeatPassword has loose focus.
   */
  public void onRepeatNewPasswordFocus() {
    errorRepeatPassMsg.set((mRePeatPassword != null
        & !(Objects.requireNonNull(mRePeatPassword).length() >= PASSWORD_VALID_LENGTH)));
    rePeatPassWordShowIcon.set((mRePeatPassword.length() > ZERO));
  }

  /**
   * This method is used listen when reset password clicked..
   */
  public void onForgotButtonClicked() {
    if (mIsForGotThroughEmail) {
      if (mUserEmail != null && !EcomUtil.isEmail(
          mUserEmail)) {
        errorEmailMsg.set(TRUE);
      } else {
        callForgotPasswordApi("", "",
            Objects.requireNonNull(mUserEmail),
            EMAIL_VERIFY);
      }
    } else {
      mMutableOnClickLiveData.setValue(FORGOT_PASSWORD);
    }
  }

  /**
   * notifies activity when Text changed
   */
  public MutableLiveData<ForgotPasswordUiAction> onTextChanged() {
    return mMutableLiveOnTextChangeData;
  }

  /*
   * notify activity when dialog opened
   */
  MutableLiveData<Pair<Boolean, HashMap<String, Object>>> onSuccessDialog() {
    return mValidateFpDialogLiveData;
  }

  /**
   * notify when onError comes
   */
  public MutableLiveData<String> onError() {
    return mValidateOnErrorLiveData;
  }

  /*
   * notify when onSuccess comes
   */

  public MutableLiveData<Boolean> onSuccess() {
    return mValidateOnSuccessLiveData;
  }

  /*
   * notify when  any onclick has happened on the view
   */
  MutableLiveData<ForgotPasswordUiAction> onClicked() {
    return mMutableOnClickLiveData;
  }

  /**
   * notify when all_password shows visible
   */
  MutableLiveData<Boolean> onSetNewPassword() {
    return mValidateNewPassLiveData;
  }

  /**
   * notify when home screen visible
   */
  MutableLiveData<Boolean> onOpenHomeScreen() {
    return mValidateHomeScreenData;
  }

  /**
   * notify activity when eye icon clicked for password
   */
  MutableLiveData<Pair<Integer, Boolean>> onShowPasswordEyeIconClicked() {
    return mValidatePasswordEyeIconLiveData;
  }

  /**
   * returns boolean true or false when otp entered
   *
   * @return true if entered ,false if not entered
   */
  private boolean isOtpEntered() {
    return mOtpFour.length() > ZERO && mOtpFir.length() > ZERO && mOtpSec.length() > ZERO
        && mOtpThi.length() > ZERO;
  }
}
