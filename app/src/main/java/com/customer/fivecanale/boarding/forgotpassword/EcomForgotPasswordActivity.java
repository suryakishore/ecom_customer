package com.customer.fivecanale.boarding.forgotpassword;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.customer.fivecanale.util.EcomConstants.CONFIRM_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.COUNTRY_CODE;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_ID;
import static com.customer.fivecanale.util.EcomConstants.E_MAIL;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FORGOT_PASSWORD_RESEND;
import static com.customer.fivecanale.util.EcomConstants.IS_EMAIL;
import static com.customer.fivecanale.util.EcomConstants.IS_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.IS_SIGN_UP;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FORGOT;
import static com.customer.fivecanale.util.EcomConstants.LOGIN_RESEND;
import static com.customer.fivecanale.util.EcomConstants.MOBILE_RESET_TYPE;
import static com.customer.fivecanale.util.EcomConstants.MOBILE_VERIFY;
import static com.customer.fivecanale.util.EcomConstants.NAME;
import static com.customer.fivecanale.util.EcomConstants.NEW_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.NORMAL_RESEND;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.OTP_VIEWS;
import static com.customer.fivecanale.util.EcomConstants.PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.PHONE_NUMBER;
import static com.customer.fivecanale.util.EcomConstants.PROFILE_PIC;
import static com.customer.fivecanale.util.EcomConstants.RESETPASS;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_PROFILE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtilBuilder;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityEcomForgotPasswordBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.HashMap;
import java.util.Objects;
import javax.inject.Inject;

/**
 * Gives the new mPassword inputs for reset the mPassword through mail
 * or
 * mPhoneNumber.
 */
public class EcomForgotPasswordActivity extends DaggerAppCompatActivity implements
    ForgotPasswordCallBack.ForgotPasswordEmailDialogCallBack {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  UserInfoHandler userInfoHandler;
  private EcomForgotPasswordViewModel mEcomForgotPasswordViewModel;
  private ActivityEcomForgotPasswordBinding mBinding;
  private String mOtpId = "";
  private CountDownTimer mCountDownTimer;
  private boolean mIsValidPhoneNum;
  private boolean mIsToShowOtp;
  private boolean mIsFromLogin;
  private boolean mIsFromSignUp, mIsFromUpdateProfile, mIsToResetPass;
  private String mPhoneNumber, mCountryCode, mPassword, mName, mEmailId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeOnClicks();
    subscribeOnTextChange();
    subscribeForgotPasswordDialog();
    subscribeOnError();
    subscribeOnSuccess();
    subscribeOnSetNewPassword();
    subscribeToHomeScreen();
    subscribePasswordShowEyeIconClicked();
  }

  /**
   * <p>Listens the onclick of the all views which are register under onclick
   * in the xml file.</p>
   */
  private void subscribeOnClicks() {
    mEcomForgotPasswordViewModel.onClicked().observe(this, forgotPasswordUiAction -> {
      switch (forgotPasswordUiAction) {
        case FORGOT_PASSWORD:
          forgotPasswordClicked();
          break;
        case RESEND:
          if (mIsToShowOtp) {
            EcomUtil.printLog("mIsToShowOtp" + mIsToShowOtp);
            if (mIsFromLogin) {
              mEcomForgotPasswordViewModel.callSendOtpApi(mPhoneNumber,
                  String.format("+%s", mCountryCode), LOGIN_RESEND);
            } else if (mIsFromUpdateProfile) {
              mEcomForgotPasswordViewModel.callUpdateDetailsApi(mPhoneNumber,
                  String.format("+%s", mCountryCode));
            } else {
              mEcomForgotPasswordViewModel.callSendOtpApi(mPhoneNumber,
                  String.format("+%s", mCountryCode), NORMAL_RESEND);
            }
          } else {
            EcomUtil.printLog("mIsToShowOtp" + "else");
            mEcomForgotPasswordViewModel.callSendOtpApi(
                Objects.requireNonNull(
                    mBinding.etForGotPassPhoneNumber.getText()).toString().replaceAll(
                    " ",
                    ""), String.format("+%s", mBinding.ccpGetNumber.getSelectedCountryCode()),
                FORGOT_PASSWORD_RESEND);
          }
          break;
      }
    });
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_ecom_forgot_password);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity and also get the intent
   * mData from another activities.</p>
   */
  private void initializeViewModel() {
    mEcomForgotPasswordViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(
            EcomForgotPasswordViewModel.class);
    mBinding.setViewModel(mEcomForgotPasswordViewModel);//used to connect mBinding to viewModel
    mBinding.ccpGetNumber.registerCarrierNumberEditText(mBinding.etForGotPassPhoneNumber);
    mBinding.ccpGetNumber.setPhoneNumberValidityChangeListener(
        isValidNumber -> {
          mIsValidPhoneNum = isValidNumber;
          mEcomForgotPasswordViewModel.mIsPhoneNumValid = isValidNumber;
          mEcomForgotPasswordViewModel.mCountryCode =
              mBinding.ccpGetNumber.getSelectedCountryCode();
        });
    boolean isEmail = getIntent().getBooleanExtra(IS_EMAIL, FALSE);
    mIsFromSignUp = getIntent().getBooleanExtra(IS_SIGN_UP, FALSE);
    mIsToShowOtp = getIntent().getBooleanExtra(OTP_VIEWS, FALSE);
    boolean isToForgot = getIntent().getBooleanExtra(IS_TO_FORGOT, FALSE);
    mIsFromUpdateProfile = getIntent().getBooleanExtra(UPDATE_PROFILE, FALSE);
    Log.d("exe", "mIsFromUpdateProfile" + mIsFromUpdateProfile);
    mIsToResetPass = getIntent().getBooleanExtra(RESETPASS, FALSE);
    mEcomForgotPasswordViewModel.mProfilePic = getIntent().getStringExtra(PROFILE_PIC);
    mEcomForgotPasswordViewModel.mIsForGotThroughEmail = isEmail;
    mEcomForgotPasswordViewModel.mIsToForgot = isToForgot;
    mEcomForgotPasswordViewModel.mIsFromUpdateProfile = mIsToResetPass;
    if (isEmail) {
      mBinding.tiForgotPassEmail.setVisibility(View.VISIBLE);
      mBinding.vgResetPassPhoneNum.setVisibility(View.GONE);
      mBinding.fpCollapsing.setTitle(
          getResources().getString(R.string.resetPasswordWhatYourMailId));
      mBinding.tvForGotPassDescText.setText(
          getResources().getString(R.string.resetPasswordMailIdDecs));
      mBinding.tvForgotPass.setText(getResources().getString(R.string.resetPasswordSendMail));
      mBinding.etForgotPassEmail.requestFocus();
    } else if (mIsToResetPass) {
      mEcomForgotPasswordViewModel.showResetPass();
      mBinding.vgResetPassPhoneNum.setVisibility(View.GONE);
    } else if (mIsToShowOtp) {
      String otpId = getIntent().getStringExtra(OTP_ID);
      mEmailId = getIntent().getStringExtra(EMAIL_ID);
      mPhoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
      mCountryCode = getIntent().getStringExtra(COUNTRY_CODE);
      mPassword = getIntent().getStringExtra(PASSWORD);
      mName = getIntent().getStringExtra(NAME);
      mIsFromLogin = getIntent().getBooleanExtra(IS_LOGIN, FALSE);
      int otpExpiryTime = getIntent().getIntExtra(OTP_EXPIRY_TIME, ZERO);
      mBinding.tvForgotPass.setText(
          mIsFromUpdateProfile ? getResources().getString(R.string.allDone)
              : (mIsFromLogin ? getResources().getString(R.string.login)
                  : getResources().getString(R.string.signUp)));
      showOtpViews(otpId, mPhoneNumber, mCountryCode, otpExpiryTime);
    } else {
      showPhoneNumberViews();
    }
    //getting device details
    mEcomForgotPasswordViewModel.getDeviceDetails(EcomUtil.getDeviceId(this),
        userInfoHandler.getIpAddress());
    setSupportActionBar(mBinding.toolbar);
    if (SDK_INT >= LOLLIPOP) {
      mBinding.toolbar.setNavigationIcon(
          getResources().getDrawable(R.drawable.all_back, null));
    }
    mBinding.fpCollapsing.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);
    mBinding.toolbar.setNavigationOnClickListener(view -> {
      if ((mBinding.forGotPassResetPass.clResetPassword.getVisibility() == View.VISIBLE
          && !mIsToResetPass) || (
          !mIsFromUpdateProfile && !mIsFromSignUp
              && !mIsFromLogin && mBinding.forGotPassOtpViews.clOtpViews.getVisibility()
              == View.VISIBLE)) {
        showPhoneNumberViews();
      } else {
        finish();
      }
    });
    mEcomForgotPasswordViewModel.setIntentData(mIsFromLogin, mIsToShowOtp, mOtpId, mName,
        mPhoneNumber, mCountryCode, mEmailId, mPassword, mIsFromUpdateProfile);
  }

  /**
   * <p>This method is used to set the mPhoneNumber views if the user is reset the mPassword through
   * phone number.</p>
   */
  private void showPhoneNumberViews() {
    mBinding.vgResetPassPhoneNum.setVisibility(View.VISIBLE);
    mBinding.tiForgotPassEmail.setVisibility(View.GONE);
    mBinding.forGotPassResetPass.clResetPassword.setVisibility(View.GONE);
    mBinding.forGotPassOtpViews.clOtpViews.setVisibility(View.GONE);
    mBinding.vgResetPassResend.setVisibility(View.GONE);
    mBinding.forGotPassResetPass.ivFpEyeNewPassword.setVisibility(View.GONE);
    mBinding.forGotPassResetPass.ivFpEyeRepeatNewPassword.setVisibility(View.GONE);
    mBinding.fpCollapsing.setTitle(getResources().getString(R.string.resetPasswordYourPhoneNum));
    mBinding.fpAppBar.setExpanded(TRUE);
    mBinding.tvForGotPassDescText.setText(
        getResources().getString(R.string.resetPasswordPhoneDecs));
    mBinding.tvForgotPass.setText(getResources().getString(R.string.allContinue));
    setFocus(mBinding.etForGotPassPhoneNumber);
    mBinding.viewPhoneNum.setBackgroundColor(getResources().getColor(R.color.allAppColor));
    if (mCountDownTimer != null) {
      mCountDownTimer.cancel();
      mCountDownTimer = null;
    }
    mBinding.tvForgotPass.setEnabled(mIsValidPhoneNum);
  }

  /**
   * <p>This method is used to listen the text change of the edit text</p>
   */
  private void subscribeOnTextChange() {
    mEcomForgotPasswordViewModel.onTextChanged().observe(this,
        forgotPasswordUiAction -> {
          switch (forgotPasswordUiAction) {
            case OTP_FIRST_TEXT_CHANGE:
              if (Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassFir.getText()).toString().length()
                  == ONE) {
                setFocus(mBinding.forGotPassOtpViews.etResetPassSec);
              } else {
                setFocus(mBinding.forGotPassOtpViews.etResetPassFir);
              }
              break;
            case OTP_SECOND_TEXT_CHANGE:
              if (Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassSec.getText()).toString().length()
                  == ONE) {
                setFocus(mBinding.forGotPassOtpViews.etResetPassThi);
              } else {
                setFocus(mBinding.forGotPassOtpViews.etResetPassSec);
              }
              if (mBinding.forGotPassOtpViews.etResetPassSec.getText().toString().isEmpty()) {
                setFocus(mBinding.forGotPassOtpViews.etResetPassFir);
              }
              break;
            case OTP_THIRD_TEXT_CHANGE:
              if (Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassThi.getText()).toString().length()
                  == ONE) {
                setFocus(mBinding.forGotPassOtpViews.etResetPassFour);
              } else {
                setFocus(mBinding.forGotPassOtpViews.etResetPassThi);
              }
              if (mBinding.forGotPassOtpViews.etResetPassThi.getText().toString().isEmpty()) {
                setFocus(mBinding.forGotPassOtpViews.etResetPassSec);
              }
              break;
            case OTP_FOUR_TEXT_CHANGE:
              if (Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassFour.getText()).toString().isEmpty()) {
                setFocus(mBinding.forGotPassOtpViews.etResetPassThi);
              }
              break;
            default:
              break;
          }
        });
  }

  /**
   * This method is used to listen the onclick of the forgot mPassword button.
   */
  private void forgotPasswordClicked() {
    if (mBinding.vgResetPassPhoneNum.getVisibility() == View.VISIBLE) {
      if (mEcomForgotPasswordViewModel.mIsToForgot) {
        mEcomForgotPasswordViewModel.callForgotPasswordApi(
            Objects.requireNonNull(
                mBinding.etForGotPassPhoneNumber.getText()).toString().replaceAll(
                " ", ""),
            String.format("+%s", mBinding.ccpGetNumber.getSelectedCountryCode()), "",
            MOBILE_VERIFY);
      } else {
        mEcomForgotPasswordViewModel.callSendOtpApi(
            Objects.requireNonNull(
                mBinding.etForGotPassPhoneNumber.getText()).toString().replaceAll(
                " ", ""),
            String.format("+%s", mBinding.ccpGetNumber.getSelectedCountryCode()), NORMAL_RESEND);
      }
    } else if (mBinding.forGotPassOtpViews.clOtpViews.getVisibility() == View.VISIBLE) {
      //verify otp Api
      String otpCode =
          String.format("%s%s%s%s", Objects.requireNonNull(
              mBinding.forGotPassOtpViews.etResetPassFir.getText()).toString(),
              Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassSec.getText()).toString(),
              Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassThi.getText()).toString(),
              Objects.requireNonNull(
                  mBinding.forGotPassOtpViews.etResetPassFour.getText()).toString());
      if (!mIsFromUpdateProfile) {
        mEcomForgotPasswordViewModel.callVerifyOtpApi(mIsFromLogin, mIsToShowOtp, mOtpId,
            otpCode, mName, mPhoneNumber, String.format("+%s", mCountryCode), mEmailId,
            mPassword);
      } else {
        mEcomForgotPasswordViewModel.callVerifyProfileOtpApi(mPhoneNumber, mOtpId, otpCode,
            String.format("+%s", mCountryCode), MOBILE_VERIFY);
      }
    } else {
      //set  new all_password
      if (mIsToResetPass && TextUtils.isEmpty(
          Objects.requireNonNull(
              mBinding.forGotPassResetPass.etForgotPassCurrentPass.getText()).toString())) {
        mBinding.forGotPassResetPass.etForgotPassCurrentPass.setError(
            getResources().getString(R.string.enterValidPassword));
      } else if (TextUtils.isEmpty(
          Objects.requireNonNull(
              mBinding.forGotPassResetPass.etForgotPassNewPass.getText()).toString())) {
        mBinding.forGotPassResetPass.etForgotPassNewPass.setError(
            getResources().getString(R.string.enterValidPassword));
      } else if (TextUtils.isEmpty(
          Objects.requireNonNull(
              mBinding.forGotPassResetPass.etForgotPassRepeatNewPass.getText()).toString())) {
        mBinding.forGotPassResetPass.etForgotPassRepeatNewPass.setError(
            getResources().getString(R.string.enterValidPassword));
      } else if (!mBinding.forGotPassResetPass.etForgotPassNewPass.getText().toString().equals(
          mBinding.forGotPassResetPass.etForgotPassRepeatNewPass.getText().toString())) {
        showError(getResources().getString(R.string.signUpPasswordMisMatch));
      } else {
        if (mIsToResetPass) {
          mEcomForgotPasswordViewModel.callResetPasswordApi(
              mBinding.forGotPassResetPass.etForgotPassNewPass.getText().toString(),
              Objects.requireNonNull(
                  mBinding.forGotPassResetPass.etForgotPassCurrentPass.getText()).toString(),
              NEW_PASSWORD);
        } else {
          mEcomForgotPasswordViewModel.callResetPasswordApi(
              mBinding.forGotPassResetPass.etForgotPassNewPass.getText().toString(), "",
              MOBILE_RESET_TYPE);
        }
      }
    }
  }

  /**
   * <p>This method is used to show the success dialog of the forgot mPassword.</p>
   */
  private void subscribeForgotPasswordDialog() {
    mEcomForgotPasswordViewModel.onSuccessDialog().observe(this, booleanHashMapPair -> {
      HashMap<String, Object> hashMap = booleanHashMapPair.second;
      if (booleanHashMapPair.first != null && booleanHashMapPair.first && hashMap != null) {
        emailSuccessDialog((String) hashMap.get(E_MAIL));
      } else {
        if (booleanHashMapPair.second != null) {
          if (mIsToShowOtp) {
            showOtpViews((String) hashMap.get(OTP_ID), mPhoneNumber, mCountryCode,
                (int) (
                    Objects.requireNonNull(hashMap.get(OTP_EXPIRY_TIME))));
          } else {
            showOtpViews((String) hashMap.get(OTP_ID),
                Objects.requireNonNull(
                    mBinding.etForGotPassPhoneNumber.getText()).toString(),
                mBinding.ccpGetNumber.getSelectedCountryCode(),
                (int) (
                    Objects.requireNonNull(hashMap.get(OTP_EXPIRY_TIME))));
          }
        }
      }
    });
  }

  /**
   * shows the success dialog if forgot password reset email link has been sent
   *
   * @param second this param is used to show the message for the success dialog.
   */
  private void emailSuccessDialog(String second) {
    CustomDialogUtilBuilder.CustomDialogBuilder customDialogUtilBuilder =
        new CustomDialogUtilBuilder.CustomDialogBuilder(this, this, TWO);
    customDialogUtilBuilder.setMailId(second);
    customDialogUtilBuilder.buildCustomDialog();
  }

  /**
   * shows the OTP views in which user enters the otp obtained from mobile
   *
   * @param otpId         this param is used to get the otp id from server and  we are sending this
   *                      mOtpId when we are verifying the  mOtpId.
   * @param phoneNumber   this mPhoneNumber is which is used to reset the mPassword.
   * @param countryCode   country code for the phone number.
   * @param otpExpiryTime otp expiry time which is coming from server
   */
  private void showOtpViews(String otpId, String phoneNumber, String countryCode,
      int otpExpiryTime) {
    EcomUtil.showKeyboard(this, mBinding.clForgotPassword);
    this.mOtpId = otpId;
    mBinding.fpCollapsing.setTitle(getResources().getString(R.string.forgotPasswordVerfyPhoneNum));
    mBinding.fpAppBar.setExpanded(TRUE);
    mBinding.tvForgotPass.setEnabled(FALSE);
    StringBuilder str = new StringBuilder(
        getResources().getString(R.string.forgotPasswordVerfyPhoneNumMsg));
    mBinding.tvForGotPassDescText.setText(str.append(" ").append("+").append(countryCode).append(
        phoneNumber));
    mBinding.forGotPassOtpViews.clOtpViews.setVisibility(View.VISIBLE);
    mBinding.vgResetPassResend.setVisibility(View.VISIBLE);
    mBinding.vgResetPassPhoneNum.setVisibility(View.GONE);
    mBinding.forGotPassOtpViews.etResetPassSec.setText("1");
    mBinding.forGotPassOtpViews.etResetPassThi.setText("1");
    mBinding.forGotPassOtpViews.etResetPassFour.setText("1");
    mBinding.forGotPassOtpViews.etResetPassFir.setText("1");
    mBinding.forGotPassOtpViews.etResetPassFir.requestFocus();
    mBinding.forGotPassOtpViews.etResetPassFir.setFocusable(TRUE);
    mBinding.forGotPassOtpViews.etResetPassFir.setCursorVisible(TRUE);
    mBinding.tvResetPassResend.setEnabled(FALSE);
    mBinding.tvResetPassCodeTxt.setText(
        getResources().getString(R.string.resetPasswordResendCodeIn));
    mBinding.tvResetPassResend.setTextColor(getResources().getColor(R.color.colorRed));
    if (mCountDownTimer != null) {
      mCountDownTimer.cancel();
    }
    mCountDownTimer = new CountDownTimer(otpExpiryTime * THOUSAND, THOUSAND) {
      public void onTick(long millisUntilFinished) {
        //here you can have your logic to set text to edit text
        mBinding.tvResetPassResend.setText(EcomUtil.getTimerValue(millisUntilFinished));
      }

      public void onFinish() {
        mCountDownTimer.cancel();
        mBinding.tvResetPassCodeTxt.setText(getResources().getString(R.string.didntGetCode));
        mBinding.tvResetPassResend.setText(getResources().getString(R.string.resend));
        mBinding.tvResetPassResend.setTextColor(
            getResources().getColor(R.color.allEastBayColor));
        mBinding.tvResetPassResend.setEnabled(TRUE);
      }
    }.start();
    //default otp 1111 automatically call api
    mEcomForgotPasswordViewModel.callVerifyOtpApi(mIsFromLogin, mIsToShowOtp, mOtpId,
        "1111", mName, mPhoneNumber, String.format("+%s", mCountryCode), mEmailId,
        mPassword);
  }

  /**
   * This method is used show the snackBar message to the user.
   */
  private void subscribeOnError() {
    mEcomForgotPasswordViewModel.onError().observe(this, this::showError);
  }

  /**
   * This method is used to finish the present activity and open the backStack activity
   * i.e.HomeActivity.
   */
  private void subscribeToHomeScreen() {
    mEcomForgotPasswordViewModel.onOpenHomeScreen().observe(this, aBoolean -> {
      Intent intent = new Intent();
      intent.putExtra(IS_TO_FINISH, TRUE);
      setResult(RESULT_OK, intent);
      finish();
    });
  }

  /**
   * <p>This method is register for onSuccess method</p>
   */
  private void subscribeOnSuccess() {
    mEcomForgotPasswordViewModel.onSuccess().observe(this, value -> {
      if (value) {
        onSuccess();
      }
    });
  }

  /**
   * <p>This method is for registering to hide or show icon to password editText. </p>
   */
  private void subscribePasswordShowEyeIconClicked() {
    mEcomForgotPasswordViewModel.onShowPasswordEyeIconClicked().observe(this,
        integerBooleanPair -> {
          if (integerBooleanPair.first != null && integerBooleanPair.second != null) {
            switch (integerBooleanPair.first) {
              case CURRENT_PASSWORD:
                mBinding.forGotPassResetPass.ivFpEyeCurrentPassword.setImageDrawable(
                    !integerBooleanPair.second ? getResources().getDrawable(
                        R.drawable.all_hide_password)
                        : getResources().getDrawable(R.drawable.all_show_password));
                mBinding.forGotPassResetPass.etForgotPassCurrentPass.setTransformationMethod(
                    !integerBooleanPair.second ? null : new PasswordTransformationMethod());
                mBinding.forGotPassResetPass.etForgotPassCurrentPass.setSelection(
                    Objects.requireNonNull(
                        mBinding.forGotPassResetPass.etForgotPassCurrentPass.getText()).length());
                break;
              case NEW_PASSWORD:
                mBinding.forGotPassResetPass.ivFpEyeNewPassword.setImageDrawable(
                    !integerBooleanPair.second ? getResources().getDrawable(
                        R.drawable.all_hide_password)
                        : getResources().getDrawable(R.drawable.all_show_password));
                mBinding.forGotPassResetPass.etForgotPassNewPass.setTransformationMethod(
                    !integerBooleanPair.second ? null : new PasswordTransformationMethod());
                mBinding.forGotPassResetPass.etForgotPassNewPass.setSelection(
                    Objects.requireNonNull(
                        mBinding.forGotPassResetPass.etForgotPassNewPass.getText()).length());
                break;
              case CONFIRM_PASSWORD:
                mBinding.forGotPassResetPass.ivFpEyeRepeatNewPassword.setImageDrawable(
                    !integerBooleanPair.second ? getResources().getDrawable(
                        R.drawable.all_hide_password)
                        : getResources().getDrawable(R.drawable.all_show_password));
                mBinding.forGotPassResetPass.etForgotPassRepeatNewPass.setTransformationMethod(
                    !integerBooleanPair.second ? null : new PasswordTransformationMethod());
                mBinding.forGotPassResetPass.etForgotPassRepeatNewPass.setSelection(
                    Objects.requireNonNull(
                        mBinding.forGotPassResetPass.etForgotPassRepeatNewPass.getText()).length());
                break;
            }
          }
        });
  }

  /**
   * <p>This method is for registering to show the newPassword fields.</p>
   */
  private void subscribeOnSetNewPassword() {
    mEcomForgotPasswordViewModel.onSetNewPassword().observe(this, value -> {
      if (value) {
        if (mCountDownTimer != null) {
          mCountDownTimer.cancel();
          mCountDownTimer = null;
        }
        mBinding.tvForgotPass.setEnabled(FALSE);
        if (mBinding.vgResetPassResend.getVisibility() == View.VISIBLE) {
          mBinding.vgResetPassResend.setVisibility(View.GONE);
        }
        mBinding.tvForGotPassDescText.setText("");
        mBinding.fpCollapsing.setTitle(
            mIsToResetPass ? getResources().getString(R.string.changePassword)
                : getResources().getString(R.string.enterNewPassword));
        mBinding.tvForgotPass.setText(
            mIsToResetPass ? getResources().getString(R.string.changePassword)
                : getResources().getString(R.string.forgotPasswordReset));
        mBinding.forGotPassOtpViews.clOtpViews.setVisibility(View.GONE);
        mBinding.vgResetPassPhoneNum.setVisibility(View.GONE);
        mBinding.fpAppBar.setExpanded(!mIsToResetPass);
        if (mIsToResetPass) {
          mBinding.forGotPassResetPass.tiForgotPassCurrentPass.setVisibility(View.VISIBLE);
          mBinding.forGotPassResetPass.etForgotPassCurrentPass.requestFocus();
          mBinding.forGotPassResetPass.etForgotPassCurrentPass.setText("");
          mBinding.forGotPassResetPass.ivFpEyeCurrentPassword.setVisibility(View.VISIBLE);
          mBinding.forGotPassResetPass.tiForgotPassRepeatNewPass.setHint(
              getResources().getString(R.string.forgotPasswordConfirmNewPass));
        }
        mBinding.forGotPassResetPass.clResetPassword.setVisibility(View.VISIBLE);
      }
    });
  }

  /**
   * <p>This method is used to show the snackBar message for mError message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mBinding.clForgotPassword, error, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * <p>This method is used to show the success dialog for 2 seconds.</p>
   */
  public void onSuccess() {
    EcomUtil.hideSoftKeyboard(mBinding.successForGotPass.clSuccess);
    if (mIsToShowOtp) {
      if (mIsFromUpdateProfile) {
        mBinding.successForGotPass.sucecssTv.setText(
            getResources().getString(R.string.forgotPasswordPhoneNumberChanged));
        mBinding.successForGotPass.tvDesc.setVisibility(View.GONE);
      } else if (mIsToResetPass) {
        mBinding.successForGotPass.sucecssTv.setText(
            getResources().getString(R.string.forgotPasswordPasswordChanged));
        mBinding.successForGotPass.tvDesc.setVisibility(View.GONE);
      } else {
        mBinding.successForGotPass.sucecssTv.setText(
            getResources().getString(R.string.forgotPasswordSuccessTitle));
        mBinding.successForGotPass.tvDesc.setText(
            getResources().getString(R.string.forgotPasswordSuccessDesc));
      }
    }
    mBinding.fpAppBar.setVisibility(View.GONE);
    mBinding.successForGotPass.clSuccess.setVisibility(View.VISIBLE);
    ((Animatable) mBinding.successForGotPass.successTick.getDrawable()).start();
    CountDownTimer countDownTimer = new CountDownTimer(THOUSAND * TWO, THOUSAND) {
      @Override
      public void onTick(long millisUntilFinished) {
      }

      @Override
      public void onFinish() {
        EcomUtil.printLog("mIsToShowOtp" + mIsToShowOtp);
        if (mIsToShowOtp || mIsToResetPass) {
          Intent intent = new Intent();
          intent.putExtra(IS_TO_FINISH, TRUE);
          setResult(RESULT_OK, intent);
          finish();
        } else {
          finish();
        }
      }
    };
    countDownTimer.start();
  }

  @Override
  public void showLoginPage() {
    finish();
  }

  @Override
  public void onBackPressed() {
    if ((mBinding.forGotPassResetPass.clResetPassword.getVisibility() == View.VISIBLE
        && !mIsToResetPass) || (
        !mIsFromUpdateProfile && !mIsFromSignUp && !mIsFromLogin
            && mBinding.forGotPassOtpViews.clOtpViews.getVisibility() == View.VISIBLE)) {
      showPhoneNumberViews();
    } else {
      finish();
    }
  }

  /**
   * sets the focus for all edit texts
   *
   * @param editText edit text references
   */
  private void setFocus(EditText editText) {
    editText.requestFocus();
    editText.setFocusable(TRUE);
    editText.setCursorVisible(
        TRUE);
  }
}
