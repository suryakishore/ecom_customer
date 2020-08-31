package com.customer.fivecanale.landing.profile.profiledetails.updateprofile;

import static com.customer.fivecanale.util.EcomConstants.COMING_FROM;
import static com.customer.fivecanale.util.EcomConstants.COUNTRY_CODE;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IN;
import static com.customer.fivecanale.util.EcomConstants.IS_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.OTP_VIEWS;
import static com.customer.fivecanale.util.EcomConstants.PHONE_NUMBER;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_NAME;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_PROFILE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_RC;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_VALUE;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.fivecanale.boarding.forgotpassword.EcomForgotPasswordActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityUpdateProfileBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.Objects;
import javax.inject.Inject;

/** Used for updating the user profile details */
public class UpdateProfileActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  private UpdateProfileViewModel mViewModel;
  private ActivityUpdateProfileBinding mBinding;
  private int mUpdateType, mCountryCode;
  private String mUpdateValue;
  private boolean mIsValidPhoneNum;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initialize();
    subscribeOpenForgotPassword();
    subscribeOnSuccess();
  }

  /** this method is using to Initialization of all the variables */
  private void initialize() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
    mBinding.ccpGetNumber.registerCarrierNumberEditText(mBinding.etProfileDetailsMob);
    mViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpdateProfileViewModel.class);
    mBinding.setViewModel(mViewModel);
    if (getIntent() != null) {
      mUpdateType = getIntent().getIntExtra(UPDATE_TYPE, UPDATE_NAME);
      mUpdateValue = getIntent().getStringExtra(UPDATE_VALUE);
      mCountryCode = getIntent().getIntExtra(COUNTRY_CODE, IN);
    }
    mBinding.btnUpdate.setEnabled(true);
    EcomUtil.printLog("mUpdateValue" + mUpdateValue + "mUpdateType" + mUpdateType);
    mBinding.ccpGetNumber.setPhoneNumberValidityChangeListener(
        isValidNumber -> {
          mIsValidPhoneNum = isValidNumber;
          EcomUtil.printLog("mIsValidPhoneNum" + mIsValidPhoneNum);
          mBinding.btnUpdate.setEnabled(mIsValidPhoneNum);
        });
    mViewModel
        .getUpdateType()
        .observe(
            this,
            updateType -> {
              switch (updateType) {
                case MAIL:
                  mBinding.header.tvTitle.setText(getResources().getString(R.string.changeMail));
                  mBinding.btnUpdate.setText(getResources().getString(R.string.changeMail));
                  mBinding.tilProfileDetailsEMail.setVisibility(View.VISIBLE);
                  mBinding.tvMailText.setVisibility(View.VISIBLE);
                  mBinding.etProfileDetailsEMail.setText(mUpdateValue);
                  break;
                case NAME:
                  mBinding.header.tvTitle.setText(getResources().getString(R.string.changeName));
                  mBinding.btnUpdate.setText(getResources().getString(R.string.changeName));
                  mBinding.tilProfileDetailsName.setVisibility(View.VISIBLE);
                  mBinding.etProfileDetailsName.setText(mUpdateValue);
                  break;
                case MOBILE:
                  mBinding.header.tvTitle.setText(getResources().getString(R.string.changePhone));
                  mBinding.btnUpdate.setText(getResources().getString(R.string.changePhone));
                  mBinding.ccpGetNumber.setVisibility(View.VISIBLE);
                  mBinding.ccpGetNumber.setCountryForPhoneCode(mCountryCode);
                  mBinding.tilProfileDetailsMob.setVisibility(View.VISIBLE);
                  if (mUpdateValue != null) {
                    mBinding.etProfileDetailsMob.setText(mUpdateValue.trim());
                  }
                  break;
                case BACK_BUTTON:
                  finish();
                  break;
                case PHONE_UPDATE:
                  Intent intent = new Intent(this, EcomForgotPasswordActivity.class);
                  intent.putExtra(COMING_FROM, UPDATE_PROFILE);
                  startActivity(intent);
              }
            });
    mViewModel.showUpdateTypeUi(mUpdateType, mCountryCode);
  }

  /**
   * <p>this method is used to subscribe to open the forgotPassword screen.</p>
   */
  private void subscribeOpenForgotPassword() {
    mViewModel.onVisibleOtpViews().observe(this,
        oTpHashMap -> {
          if (oTpHashMap != null) {
            openForgotPassword(oTpHashMap.get(OTP_ID),
                Integer.parseInt(
                    Objects.requireNonNull(oTpHashMap.get(OTP_EXPIRY_TIME))));
          }
        });
  }

  /**
   * <p>This method is used to open the forgot password screen.</p>
   */
  private void openForgotPassword(String otpId, int otpExpiryTime) {
    Intent intent = new Intent(this, EcomForgotPasswordActivity.class);
    intent.putExtra(IS_LOGIN, FALSE);
    intent.putExtra(OTP_ID, otpId);
    intent.putExtra(UPDATE_PROFILE, TRUE);
    intent.putExtra(OTP_EXPIRY_TIME, otpExpiryTime);
    intent.putExtra(OTP_VIEWS, TRUE);
    intent.putExtra(PHONE_NUMBER,
        Objects.requireNonNull(mBinding.etProfileDetailsMob.getText()).toString().replaceAll(
            " ", ""));
    intent.putExtra(COUNTRY_CODE, mBinding.ccpGetNumber.getSelectedCountryCode());
    startActivityForResult(intent, UPDATE_RC);
  }

  /**
   * <p>This method is register for onSuccess method</p>
   */
  private void subscribeOnSuccess() {
    mViewModel.onSuccess().observe(this, value -> {
      if (value) {
        onSuccess();
      }
    });
  }

  /**
   * <p>This method is used to show the success dialog for 2 seconds.</p>
   */
  public void onSuccess() {
    EcomUtil.hideKeyboard(this);
    mBinding.vgUpdateProfile.setVisibility(View.GONE);
    mBinding.successUpdateProfile.clSuccess.setVisibility(View.VISIBLE);
    mBinding.successUpdateProfile.sucecssTv.setText(
        getResources().getString(R.string.updateProfileEmailSuccTitle));
    mBinding.successUpdateProfile.tvDesc.setText(
        getResources().getString(R.string.updateProfileEmailSuccDesc));
    ((Animatable) mBinding.successUpdateProfile.successTick.getDrawable()).start();
    CountDownTimer countDownTimer = new CountDownTimer(THOUSAND * TWO, THOUSAND) {
      @Override
      public void onTick(long millisUntilFinished) {
      }

      @Override
      public void onFinish() {
        finish();
      }
    };
    countDownTimer.start();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      switch (requestCode) {
        case UPDATE_RC:
          if (resultCode == RESULT_OK) {
            boolean isToFinish = data.getBooleanExtra(IS_TO_FINISH, FALSE);
            if (isToFinish) {
              finish();
            }
          }
          break;
      }
    }
  }
}