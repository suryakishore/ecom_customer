package com.customer.fivecanale.boarding.register;

import static com.customer.fivecanale.util.EcomConstants.EIGHT;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.MOBILE_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.NORMAL_RESEND;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.PASSWORD_VALID_LENGTH;
import static com.customer.fivecanale.util.EcomConstants.SIGN_UP_VERIFICATION_CODE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.SendOtpUseCase;
import com.customer.domain.interactor.VerifyMobileOrMailUseCase;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.EcomUtil;

import io.reactivex.observers.DisposableSingleObserver;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

/**
 * <p>This class represents the viewModel class for forgotPassword.in this class we are listening
 * the textChanges and focus changes and onclick of the signUp activity</p>
 */
public class EcomSignUpViewModel extends ViewModel {
  public ObservableField progressVisible, backgroundColor;
  public ObservableField errorEmailMsg, errorNameMsg, errorConPassMsg, errorPassWordMsg,
          errorPhoneNumMsg, errorEmailExistMsg, errorPoneNumExistMsg;
  public ObservableField btnEnabled, fbTint, passwordShowIcon, conPasswordShowIcon;
  Boolean mIsPhoneNumValid;
  public ObservableBoolean isMinPassVisible;
  String mCountryCode, mUserPhoneNum;
  String mUserEmail = "", mUserName = "";
  private MutableLiveData<HashMap<String, Object>> mValidateForgotPassLiveData =
          new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidateTermsPrivacyPolicyLiveData = new MutableLiveData<>();
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidatePasswordShowLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidateConPasswordShowLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mValidateCheckBoxLiveData = new MutableLiveData<>();
  private VerifyMobileOrMailUseCase mVerifyMobileOrMailUseCase;
  private UseCaseHandler mHandler;
  private SendOtpUseCase mSendOtpUseCase;
  private String mUserpass = "",
          mUserConPass = "";
  private boolean mPhoneNumExist, mEmailExist;

  @Inject
  public EcomSignUpViewModel(SendOtpUseCase sendOTPUseCase,
                             VerifyMobileOrMailUseCase verifyMobileOrMailUseCase, UseCaseHandler handler) {
    this.mVerifyMobileOrMailUseCase = verifyMobileOrMailUseCase;
    this.mHandler = handler;
    this.mSendOtpUseCase = sendOTPUseCase;
    progressVisible = new ObservableField<>(FALSE);
    errorNameMsg = new ObservableField<>(FALSE);
    errorEmailMsg = new ObservableField<>(FALSE);
    errorConPassMsg = new ObservableField<>(FALSE);
    errorPassWordMsg = new ObservableField<>(FALSE);
    btnEnabled = new ObservableField<>(FALSE);
    passwordShowIcon = new ObservableField<>(FALSE);
    isMinPassVisible = new ObservableBoolean(FALSE);
    conPasswordShowIcon = new ObservableField<>(FALSE);
    backgroundColor = new ObservableField<>(FALSE);
    errorPhoneNumMsg = new ObservableField<>(FALSE);
    fbTint = new ObservableField<>(FALSE);
    errorEmailExistMsg = new ObservableField<>(FALSE);
    errorPoneNumExistMsg = new ObservableField<>(FALSE);
    initialization();
  }

  /**
   * This method is used to initialize the password show icon values through observable mData.
   */
  private void initialization() {
    mValidatePasswordShowLiveData.setValue(TRUE);
    mValidateConPasswordShowLiveData.setValue(TRUE);
    mValidateCheckBoxLiveData.setValue(FALSE);
  }

  /**
   * <p>This method is used to call the send otp api</p>
   *
   * @param name        user name
   * @param mobile      mobile number
   * @param countryCode country code
   * @param email       email
   */
  private void callSendOtpApi(String name, String mobile, String countryCode, String email) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<SendOtpUseCase.ResponseValues> disposableSingleObserver =
            new DisposableSingleObserver<SendOtpUseCase.ResponseValues>() {
              @Override
              public void onSuccess(SendOtpUseCase.ResponseValues responseValues) {
                progressVisible.set(FALSE);
                HashMap<String, Object> map = new HashMap<>();
                map.put(OTP_ID, responseValues.getData().getOtpId());
                map.put(OTP_EXPIRY_TIME, responseValues.getData().getOtpExpiryTime());
                mValidateForgotPassLiveData.postValue(map);
              }

              @Override
              public void onError(Throwable e) {
                EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
                progressVisible.set(FALSE);
                mValidateOnErrorLiveData.postValue(e.getMessage());
              }
            };
    mHandler.execute(mSendOtpUseCase,
            new SendOtpUseCase.RequestValues(mobile, countryCode, email, MOBILE_LOGIN,
                    SIGN_UP_VERIFICATION_CODE, name, NORMAL_RESEND), disposableSingleObserver);
  }

  /**
   * <p>this method is used to call the email or phoneNumber  exist api </p>
   *
   * @param mobile      mobile number
   * @param countryCode country code
   * @param email       email
   * @param verifyType  verify type whether through email phoneNumber.
   */
  private void callEmailOrPhoneExistApi(String mobile, String countryCode, String email,
                                        int verifyType) {
    progressVisible.set(true);
    DisposableSingleObserver<VerifyMobileOrMailUseCase.ResponseValues>
            disposableSingleObserver =
            new DisposableSingleObserver<VerifyMobileOrMailUseCase.ResponseValues>() {
              @Override
              public void onSuccess(VerifyMobileOrMailUseCase.ResponseValues responseValues) {
                progressVisible.set(FALSE);
                if (verifyType == EMAIL_LOGIN) {
                  mEmailExist = FALSE;
                } else {
                  mPhoneNumExist = FALSE;
                }
                fbTint.set(signUpSelect());
                btnEnabled.set(signUpSelect());
              }

              @Override
              public void onError(Throwable e) {
                progressVisible.set(FALSE);
                if (verifyType == EMAIL_LOGIN) {
                  mEmailExist = TRUE;
                  errorEmailExistMsg.set(TRUE);
                } else {
                  mPhoneNumExist = TRUE;
                  errorPoneNumExistMsg.set(TRUE);
                }
                fbTint.set(signUpSelect());
                btnEnabled.set(signUpSelect());
              }
            };
    mHandler.execute(mVerifyMobileOrMailUseCase,
            new VerifyMobileOrMailUseCase.RequestValues(countryCode, mobile, verifyType, email),
            disposableSingleObserver);
  }

  /**
   * <p>This method is used to listen when eye icon newPassword clicked.</p>
   */
  public void onEyeIconClicked() {
    mValidatePasswordShowLiveData.postValue(
            !mValidatePasswordShowLiveData.getValue());
  }

  /**
   * <p>This method is used to listen when eye icon confirmPassword clicked.</p>
   */
  public void onEyeConfirmPassIconClicked() {
    mValidateConPasswordShowLiveData.postValue(
            !mValidateConPasswordShowLiveData.getValue());
  }

  /**
   * handles text changes of name
   */
  public void onTextChangeName(CharSequence s, int start, int before, int count) {
    mUserName = s.toString().trim();
    errorNameMsg.set(FALSE);
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * handles text changes of email
   */
  public void onUserEmailChanged(CharSequence s, int start, int before, int count) {
    mUserEmail = s.toString().trim();
    errorEmailMsg.set(FALSE);
    errorEmailExistMsg.set(FALSE);
    mEmailExist = TRUE;
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * handles text changes of phone number
   */
  public void onTextChangePhoneNumber(CharSequence s, int start, int before, int count) {
    mUserPhoneNum = s.toString().trim();
    errorPhoneNumMsg.set(FALSE);
    errorPoneNumExistMsg.set(FALSE);
    mPhoneNumExist = TRUE;
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * handle text changes of password
   */
  public void onTextChangePassword(CharSequence s, int start, int before, int count) {
    mUserpass = s.toString();
    errorPassWordMsg.set(FALSE);
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * handles text changes of all_password
   */
  public void onTextChangeConfirmPassword(CharSequence s, int start, int before, int count) {
    mUserConPass = s.toString();
    errorConPassMsg.set(FALSE);
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * <p>listens when signUp button clicked.</p>
   */
  public void onSignUpClicked() {
    if (isValid()) {
      if (!mUserpass.equals(mUserConPass)) {
        mValidateOnErrorLiveData.postValue(
                ApplicationManager.getInstance().getResources().getString(
                        R.string.signUpPasswordMisMatch));
      } else {
        callSendOtpApi(
                Objects.requireNonNull(mUserName),
                Objects.requireNonNull(
                        mUserPhoneNum.replaceAll(" ",
                                "")),
                String.format("+%s", mCountryCode),
                Objects.requireNonNull(mUserEmail));
      }
    } else {
      setErrorMsg();
    }
  }

  /**
   * <p>Shows the get the mError message based on missing fields.</p>
   */
  private void setErrorMsg() {
    if (TextUtils.isEmpty(mUserName) || !EcomUtil.isName(mUserName)) {
      errorNameMsg.set(TRUE);
      return;
    }
    if (!EcomUtil.isEmail(mUserEmail)) {
      errorEmailMsg.set(TRUE);
      return;
    }
    if (!mIsPhoneNumValid) {
      errorPhoneNumMsg.set(TRUE);
      return;
    }
    if (mUserpass.length() < PASSWORD_VALID_LENGTH) {
      errorPassWordMsg.set(TRUE);
      return;
    }
    if (!mUserpass.equals(mUserConPass)) {
      errorConPassMsg.set(TRUE);
      mValidateOnErrorLiveData.postValue(ApplicationManager.getInstance().getResources().getString(
              R.string.signUpPasswordMisMatch));
      return;
    }
    if (!mValidateCheckBoxLiveData.getValue()) {
      mValidateOnErrorLiveData.postValue(ApplicationManager.getInstance().getResources().getString(
              R.string.signUpAcceptTermsAndCon));
    }
  }

  /**
   * <p>Listens when signUp button clicked.</p>
   */
  public void onCheckBoxClicked() {
    mValidateCheckBoxLiveData.setValue(!mValidateCheckBoxLiveData.getValue());
  }

  /**
   * onclick for terms and conditions
   */
  public void onTermsAndConditionsClicked() {
    mValidateTermsPrivacyPolicyLiveData.postValue(TRUE);
  }

  /**
   * onclick for privacy policy
   */
  public void onPrivacyPolicyClicked() {
    mValidateTermsPrivacyPolicyLiveData.postValue(FALSE);
  }

  /**
   * listens for email loose focus
   */
  public void onEmailFocus() {
    if (TextUtils.isEmpty(mUserEmail)) {
      return;
    }
    if (!EcomUtil.isEmail(Objects.requireNonNull(mUserEmail))) {
      errorEmailMsg.set(TRUE);
    } else {
      callEmailOrPhoneExistApi("", "",
              mUserEmail, EMAIL_LOGIN);
    }
  }

  /**
   * listens for password loose focus
   */
  public void onPasswordFocus() {
    if (TextUtils.isEmpty(mUserConPass)) {
      return;
    }
    errorPassWordMsg.set((mUserpass != null & mUserpass.length() >= EIGHT) ? FALSE : TRUE);
    passwordShowIcon.set(!TextUtils.isEmpty(mUserpass));
    isMinPassVisible.set(!TextUtils.isEmpty(mUserpass));
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * <h2>onPasswordHasFocus</h2>
   * listen for password has focus
   */
  public void onPasswordHasFocus() {
    passwordShowIcon.set(TRUE);
    isMinPassVisible.set(TRUE);
  }

  /**
   * <h2>onConfirmPasswordFocus</h2>
   * listen for confirm password loose focus
   */
  public void onConfirmPasswordFocus() {
    if (TextUtils.isEmpty(mUserConPass)) {
      return;
    }
    errorConPassMsg.set(
            !(mUserConPass != null & Objects.requireNonNull(mUserConPass).length()
                    >= PASSWORD_VALID_LENGTH));
    conPasswordShowIcon.set(!TextUtils.isEmpty(mUserConPass));
    fbTint.set(signUpSelect());
    btnEnabled.set(signUpSelect());
  }

  /**
   * listens for confirm password has focus
   */
  public void onConfirmPasswordHasFocus() {
    conPasswordShowIcon.set(TRUE);
  }

  /**
   * listens for phone number loose focus
   */
  public void onPhoneNumberFocus() {
    if (TextUtils.isEmpty(mUserPhoneNum)) {
      return;
    }
    backgroundColor.set(FALSE);
    if (mIsPhoneNumValid) {
      callEmailOrPhoneExistApi(
              mUserPhoneNum.replaceAll(" ", ""),
              String.format("+%s", mCountryCode), "",
              MOBILE_LOGIN);
    } else {
      errorPhoneNumMsg.set(TRUE);
    }
  }

  /**
   * listens for phone number has focus
   */
  public void onPhoneNumberHasFocus() {
    backgroundColor.set(TRUE);
  }

  /**
   * listens for user name loose focus
   */
  public void onNameFocus() {
    if (TextUtils.isEmpty(mUserEmail)) {
      return;
    }
    if (!EcomUtil.isName(mUserName)) {
      errorNameMsg.set(TRUE);
    }
  }

  /**
   * notify activity when PrivacyPolicy up clicked
   */
  public MutableLiveData<Boolean> onTermsAndPrivacyPolicy() {
    return mValidateTermsPrivacyPolicyLiveData;
  }

  /**
   * notifies when onError comes
   */
  public MutableLiveData<String> onError() {
    return mValidateOnErrorLiveData;
  }

  /**
   * notifies activity when forgot password page opened
   */
  MutableLiveData<HashMap<String, Object>> onStartForgotPassword() {
    return mValidateForgotPassLiveData;
  }

  /**
   * notifies activity when eye icon clicked for all_password
   */
  MutableLiveData<Boolean> onShowPasswordIconClicked() {
    return mValidatePasswordShowLiveData;
  }

  /**
   * notifies activity when eye icon clicked for confirm all_password
   */
  MutableLiveData<Boolean> onShowConPasswordIconClicked() {
    return mValidateConPasswordShowLiveData;
  }

  /**
   * <p>This method is used to get valid to check whether to select the signUp or not..</p>
   */
  private boolean isValid() {
    return !TextUtils.isEmpty(mUserName) && EcomUtil.isName(mUserName)
            && EcomUtil.isEmail(Objects.requireNonNull(mUserEmail))
            && !mEmailExist && mIsPhoneNumValid && !mPhoneNumExist
            && !TextUtils.isEmpty(mUserpass) && (mUserpass.length() >= PASSWORD_VALID_LENGTH)
            && !TextUtils.isEmpty(mUserConPass) && (mUserConPass.length() >= PASSWORD_VALID_LENGTH)
            && mValidateCheckBoxLiveData.getValue();
  }

  /**
   * <p>checks whether to enable/disable the signUp or not..</p>
   */
  private boolean signUpSelect() {
    return !TextUtils.isEmpty(mUserName) && EcomUtil.isEmail(Objects.requireNonNull(mUserEmail))
            && !mEmailExist && mIsPhoneNumValid && !mPhoneNumExist
            && !TextUtils.isEmpty(mUserpass) && (mUserpass.length() >= PASSWORD_VALID_LENGTH)
            && !TextUtils.isEmpty(mUserConPass) && (mUserConPass.length() >= PASSWORD_VALID_LENGTH);
  }
}
