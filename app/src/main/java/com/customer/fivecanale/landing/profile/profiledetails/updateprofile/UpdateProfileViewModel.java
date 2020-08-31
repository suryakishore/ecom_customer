package com.customer.fivecanale.landing.profile.profiledetails.updateprofile;

import static com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateType.BACK_BUTTON;
import static com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateType.MAIL;
import static com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateType.MOBILE;
import static com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateType.NAME;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_MAIL;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_MOBILE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_NAME;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.SendOtpToUpdateProfileUseCase;
import com.customer.domain.interactor.UpdateProfileUseCase;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.HashMap;
import javax.inject.Inject;

/** Adds the login for update profile details screen */
public class UpdateProfileViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> nameValidation = new ObservableField<>(false);
  public ObservableField<Boolean> mailValidation = new ObservableField<>(false);
  public ObservableField<Boolean> isEnableButton = new ObservableField<>(false);
  public ObservableField<String> errorMsg;
  private UpdateProfileUseCase mUpdateProfileUseCase;
  private UseCaseHandler mUseCaseHandler;
  private MutableLiveData<UpdateType> mMutableLiveData = new MutableLiveData<>();
  private MutableLiveData<HashMap<String, String>> mHashMapMutableLiveData =
      new MutableLiveData<>();
  private String mValueToUpdate;
  private int mUpdateType;
  private int mCountryCode;
  private SendOtpToUpdateProfileUseCase mSendOtpToUpdateProfileUseCase;
  private MutableLiveData<Boolean> mValidateOnSuccessLiveData = new MutableLiveData<>();

  @Inject
  public UpdateProfileViewModel(SendOtpToUpdateProfileUseCase sendOtpToUpdateProfileUseCase,
      UpdateProfileUseCase updateProfileUseCase,
      UseCaseHandler handler) {
    this.mUseCaseHandler = handler;
    this.mUpdateProfileUseCase = updateProfileUseCase;
    this.mSendOtpToUpdateProfileUseCase = sendOtpToUpdateProfileUseCase;
    errorMsg = new ObservableField<>();
  }

  /** This Method is using to Update User mData to Server */
  public void updateName(View view) {
    String mail = null, name = null, phNum = null;
    switch (mUpdateType) {
      case UPDATE_MAIL:
        mail = mValueToUpdate;
        break;
      case UPDATE_NAME:
        name = mValueToUpdate;
        break;
      case UPDATE_MOBILE:
        phNum = mValueToUpdate;
        //  mMutableLiveData.postValue(PHONE_UPDATE);
        break;
    }
    switch (mUpdateType) {
      case UPDATE_NAME:
        DisposableSingleObserver<UpdateProfileUseCase.ResponseValues> disposableSingleObserver =
            new DisposableSingleObserver<UpdateProfileUseCase.ResponseValues>() {
              @Override
              public void onSuccess(UpdateProfileUseCase.ResponseValues responseValues) {
                mMutableLiveData.postValue(BACK_BUTTON);
              }

              @Override
              public void onError(Throwable e) {
                errorMsg.set(e.getMessage());
                EcomUtil.printLog("UpdateProfile Fail" + e.getMessage());
              }
            };
        mUseCaseHandler.execute(
            mUpdateProfileUseCase,
            new UpdateProfileUseCase.RequestValues(name, "", "", phNum, mail),
            disposableSingleObserver);
        break;
      case UPDATE_MOBILE:
        if (phNum != null) {
          callUpdateDetailsApi(phNum.replace(" ", ""), String.format("+%s", mCountryCode), "");
        }
        break;
      case UPDATE_MAIL:
        callUpdateDetailsApi("", "", mail);
        break;
    }
  }

  /**
   * This Method is Using for user mData validation
   *
   * @param s changed text from Edit Text
   */
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    mValueToUpdate = s.toString().trim();
    switch (mUpdateType) {
      case UPDATE_NAME:
        nameValidation.set(TextUtils.isEmpty(mValueToUpdate));
        isEnableButton.set(!TextUtils.isEmpty(mValueToUpdate));
        break;
      case UPDATE_MAIL:
        mailValidation.set(!EcomUtil.isEmail(mValueToUpdate));
        isEnableButton.set(EcomUtil.isEmail(mValueToUpdate));
        break;
    }
  }

  /**
   * call the product details api
   */
  private void callUpdateDetailsApi(String mobNumber, String countryCode, String mail) {
    DisposableSingleObserver<SendOtpToUpdateProfileUseCase.ResponseValues>
        disposableSingleObserver =
        new DisposableSingleObserver<SendOtpToUpdateProfileUseCase.ResponseValues>() {
          @SuppressLint("DefaultLocale")
          @Override
          public void onSuccess(SendOtpToUpdateProfileUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("REview Succ");
            if (mUpdateType == UPDATE_MOBILE) {
              HashMap<String, String> map = new HashMap<>();
              map.put(OTP_ID, responseValues.getData().getOtpId());
              map.put(OTP_EXPIRY_TIME,
                  String.format("%d", responseValues.getData().getOtpExpiryTime()));
              mHashMapMutableLiveData.postValue(map);
            } else {
              mValidateOnSuccessLiveData.setValue(TRUE);
            }
          }

          @Override
          public void onError(Throwable e) {
            errorMsg.set(e.getMessage());
            EcomUtil.printLog("REview Fail" + e.getMessage());
          }
        };
    if (mUpdateType == UPDATE_MOBILE) {
      mUseCaseHandler.execute(mSendOtpToUpdateProfileUseCase,
          new SendOtpToUpdateProfileUseCase.RequestValues(mobNumber, countryCode, UPDATE_MOBILE),
          disposableSingleObserver);
    } else {
      mUseCaseHandler.execute(mSendOtpToUpdateProfileUseCase,
          new SendOtpToUpdateProfileUseCase.RequestValues(mail, UPDATE_MAIL),
          disposableSingleObserver);
    }
  }

  /**
   * This method is using to decide which UI to show to user
   *
   * @param profileUpdateType Ui type to show
   */
  void showUpdateTypeUi(int profileUpdateType, int countryCode) {
    this.mUpdateType = profileUpdateType;
    this.mCountryCode = countryCode;
    switch (profileUpdateType) {
      case UPDATE_NAME:
        mMutableLiveData.postValue(NAME);
        break;
      case UPDATE_MOBILE:
        mMutableLiveData.postValue(MOBILE);
        break;
      case UPDATE_MAIL:
        mMutableLiveData.postValue(MAIL);
        break;
    }
  }

  /** getting User action Mutable to subscribe */
  MutableLiveData<UpdateType> getUpdateType() {
    return mMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mMutableLiveData.postValue(BACK_BUTTON);
  }

  /**
   * <h2>onVisibleOtpViews</h2>
   * notify activity when phone icon clicked
   */
  MutableLiveData<HashMap<String, String>> onVisibleOtpViews() {
    return mHashMapMutableLiveData;
  }

  /*
   * notify when onSuccess comes
   */
  public MutableLiveData<Boolean> onSuccess() {
    return mValidateOnSuccessLiveData;
  }
}