package com.customer.fivecanale.boarding.register;

import static com.customer.fivecanale.util.EcomConstants.COUNTRY_CODE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_ID;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IN;
import static com.customer.fivecanale.util.EcomConstants.IS_EMAIL;
import static com.customer.fivecanale.util.EcomConstants.IS_SIGN_UP;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.NAME;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.OTP_VIEWS;
import static com.customer.fivecanale.util.EcomConstants.PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.PHONE_NUMBER;
import static com.customer.fivecanale.util.EcomConstants.PROFILE_PIC;
import static com.customer.fivecanale.util.EcomConstants.SIGN_UP_RC;
import static com.customer.fivecanale.util.EcomConstants.TERMS;
import static com.customer.fivecanale.util.EcomConstants.TERMS_PRIVACY_API;
import static com.customer.fivecanale.util.EcomConstants.TITLE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.fivecanale.boarding.forgotpassword.EcomForgotPasswordActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.webview.WebViewAct;
import com.databinding.ActivityEcomSignUpBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.Objects;
import javax.inject.Inject;

/**
 * <P>This class is used to register a user.</P>
 */
public class EcomSignUpActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private boolean mPhoneNumValid;
  private ActivityEcomSignUpBinding mBinding;
  private EcomSignUpViewModel mEcomSignUpViewModel;
  private String mProfilePic;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribePasswordShowIconClicked();
    subscribeConPasswordShowIconClicked();
    subscribeForTermsAndPrivacyPolicy();
    subscribeOnError();
    subscribeToForgotPasswordPage();
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_ecom_sign_up);
  }

  /**
   * <p>this method is used to initialize the viewModel</p>
   */
  private void initializeViewModel() {
    mEcomSignUpViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(EcomSignUpViewModel.class);
    mBinding.setViewModel(mEcomSignUpViewModel);
    mBinding.ccpGetNumber.registerCarrierNumberEditText(mBinding.etSignUpPhoneNumber);
    mBinding.ccpGetNumber.setPhoneNumberValidityChangeListener(
        isValidNumber -> {
          mPhoneNumValid = isValidNumber;
          mEcomSignUpViewModel.mIsPhoneNumValid = isValidNumber;
          mEcomSignUpViewModel.mCountryCode = mBinding.ccpGetNumber.getSelectedCountryCode();
          mBinding.etSignUpPhoneNumber.setFilters(
              mPhoneNumValid
                  && !Objects.requireNonNull(
                  mBinding.etSignUpPhoneNumber.getText()).toString().isEmpty()
                  ?
                  new InputFilter[]{new InputFilter.LengthFilter(
                      mBinding.etSignUpPhoneNumber.getText().toString().length())}
                  : new InputFilter[]{});
        });
    setSupportActionBar(mBinding.toolbar);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mBinding.toolbar.setNavigationIcon(
          getResources().getDrawable(R.drawable.all_back, null));
    }
    mBinding.signUpCollapsing.setTitle(getResources().getString(R.string.signUp));
    mBinding.signUpCollapsing.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);
    mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    Intent intent = getIntent();
    if (intent.getStringExtra(EMAIL_ID) != null && !intent.getStringExtra(EMAIL_ID).isEmpty()) {
      mEcomSignUpViewModel.mUserEmail = intent.getStringExtra(EMAIL_ID);
      mEcomSignUpViewModel.mUserName = intent.getStringExtra(NAME);
      mProfilePic = intent.getStringExtra(PROFILE_PIC);
      mBinding.etSignUpEmail.setText(mEcomSignUpViewModel.mUserEmail);
      mBinding.etSignUpName.setText(mEcomSignUpViewModel.mUserName);
      mBinding.etSignUpName.requestFocus();
      mBinding.etSignUpName.setFocusable(TRUE);
    }
    if (intent.getStringExtra(PHONE_NUMBER) != null && !intent.getStringExtra(
        PHONE_NUMBER).isEmpty()) {
      EcomUtil.printLog("exe" + "signUp" + intent.getStringExtra(PHONE_NUMBER));
      mBinding.ccpGetNumber.setCountryForPhoneCode(intent.getIntExtra(COUNTRY_CODE, IN));
      mBinding.etSignUpPhoneNumber.setText(intent.getStringExtra(PHONE_NUMBER));
      mEcomSignUpViewModel.mIsPhoneNumValid = TRUE;
      mEcomSignUpViewModel.mUserPhoneNum = intent.getStringExtra(PHONE_NUMBER);
      mEcomSignUpViewModel.mCountryCode = mBinding.ccpGetNumber.getSelectedCountryCode();
    }
  }

  /**
   * <p>Opens the forgot password activity.</p>
   */
  private void subscribeToForgotPasswordPage() {
    mEcomSignUpViewModel.onStartForgotPassword().observe(this,
        OtpHashMap -> {
          if (OtpHashMap != null) {
            startForgotPassword((String) OtpHashMap.get(OTP_ID),
                (int) Objects.requireNonNull(OtpHashMap.get(OTP_EXPIRY_TIME)));
          }
        });
  }

  /**
   * <p>opens the forgot password activity.</p>
   */
  private void startForgotPassword(String otpId, int otpExpiryTime) {
    Intent intent = new Intent(this, EcomForgotPasswordActivity.class);
    intent.putExtra(IS_EMAIL, FALSE);
    intent.putExtra(IS_SIGN_UP, TRUE);
    intent.putExtra(EMAIL_ID,
        Objects.requireNonNull(mBinding.etSignUpEmail.getText()).toString());
    intent.putExtra(OTP_ID, otpId);
    intent.putExtra(OTP_EXPIRY_TIME, otpExpiryTime);
    intent.putExtra(OTP_VIEWS, TRUE);
    intent.putExtra(PHONE_NUMBER,
        Objects.requireNonNull(mBinding.etSignUpPhoneNumber.getText()).toString().replaceAll(
            " ", ""));
    intent.putExtra(COUNTRY_CODE, mBinding.ccpGetNumber.getSelectedCountryCode());
    intent.putExtra(PASSWORD,
        Objects.requireNonNull(mBinding.etSignUpPassword.getText()).toString());
    intent.putExtra(NAME, Objects.requireNonNull(mBinding.etSignUpName.getText()).toString());
    intent.putExtra(PROFILE_PIC, mProfilePic);
    startActivityForResult(intent, SIGN_UP_RC);
  }

  /**
   * <p>this method is to register for showing snackBar message</p>
   */
  private void subscribeOnError() {
    mEcomSignUpViewModel.onError().observe(this, this::showError);
  }

  /**
   * <p>this method is to register for showing snackBar message</p>
   */
  private void subscribeForTermsAndPrivacyPolicy() {
    mEcomSignUpViewModel.onTermsAndPrivacyPolicy().observe(this, value -> startWebViewAct(value));
  }

  /**
   * start the web view activity
   */
  private void startWebViewAct(boolean value) {
    Intent intent = new Intent(this, WebViewAct.class);
    intent.putExtra(TITLE, value ? getResources().getString(R.string.termsAndConditions)
        : getResources().getString(R.string.signUpPrivacyPolicy));
    intent.putExtra(TERMS_PRIVACY_API, TRUE);
    intent.putExtra(TERMS, value);
    startActivity(intent);
  }

  /**
   * <p>This method is for registering to hide or show icon to Password editText. </p>
   */
  private void subscribePasswordShowIconClicked() {
    mEcomSignUpViewModel.onShowPasswordIconClicked().observe(this, aBoolean -> {
      mBinding.ivSignUpPaswordShow.setImageDrawable(
          (!aBoolean) ? getResources().getDrawable(R.drawable.all_hide_password)
              : getResources().getDrawable(R.drawable.all_show_password));
      mBinding.etSignUpPassword.setTransformationMethod(
          (!aBoolean) ? null : new PasswordTransformationMethod());
      mBinding.etSignUpPassword.setSelection(
          Objects.requireNonNull(mBinding.etSignUpPassword.getText()).length());
    });
  }

  /**
   * <p>This method is for registering to hide or show icon to Confirm Password editText. </p>
   */
  private void subscribeConPasswordShowIconClicked() {
    mEcomSignUpViewModel.onShowConPasswordIconClicked().observe(this, aBoolean -> {
      mBinding.ivSignUpConPaswordShow.setImageDrawable(
          (!aBoolean) ? getResources().getDrawable(R.drawable.all_hide_password)
              : getResources().getDrawable(R.drawable.all_show_password));
      mBinding.etSignUpConPassword.setTransformationMethod(
          (!aBoolean) ? null : new PasswordTransformationMethod());
      mBinding.etSignUpConPassword.setSelection(
          Objects.requireNonNull(mBinding.etSignUpConPassword.getText()).length());
    });
  }

  /**
   * <p>Shows the snackBar with mError message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mBinding.clSignUp, error, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null && resultCode == RESULT_OK) {
      if (requestCode == SIGN_UP_RC) {
        boolean isToFinish = data.getBooleanExtra(IS_TO_FINISH, FALSE);
        if (isToFinish) {
          Intent intent = new Intent();
          intent.putExtra(IS_TO_FINISH, TRUE);
          setResult(RESULT_OK, intent);
          finish();
        }
      }
    }
  }
}
