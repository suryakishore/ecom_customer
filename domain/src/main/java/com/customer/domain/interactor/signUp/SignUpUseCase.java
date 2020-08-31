package com.customer.domain.interactor.signup;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.model.signup.SignUpData;
import com.customer.domain.repository.SignUpRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class SignUpUseCase
    extends UseCase<SignUpUseCase.RequestValues, SignUpUseCase.ResponseValues> {
  private SignUpRepository signUpRepository;

  @Inject
  public SignUpUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      SignUpRepository signUpRepository) {
    super(threadExecutor, postExecutionThread);
    this.signUpRepository = signUpRepository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return signUpRepository.signUp(requestValues.mDeviceId,
        requestValues.mMobile, requestValues.mCountryCode, requestValues.mEmail,
        requestValues.mPassword,
        requestValues.mDeviceType, requestValues.customerType,
        requestValues.mSignUpType, requestValues.mUserType, requestValues.mTermsAndCond,
        requestValues.mIpAddress, requestValues.mFirstName, requestValues.mLastName,
        requestValues.mProfilePicture, requestValues.mDeviceModel, requestValues.mDeviceMake,
        requestValues.mDeviceTime, requestValues.mBrowserName, requestValues.mDeviceOsVersion,
        requestValues.mLatitude, requestValues.mLongitude, requestValues.mAppVersion);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mDeviceId;
    private String mMobile;
    private String mCountryCode;
    private String mEmail;
    private String mPassword;
    private int mDeviceType;
    private int mSignUpType;
    private int mUserType;
    private int mTermsAndCond;
    private String mIpAddress;
    private String mFirstName;
    private String mLastName;
    private String mProfilePicture;
    private int customerType;
    private String mDeviceModel;
    private String mDeviceMake;
    private String mDeviceTime;
    private String mBrowserName;
    private String mDeviceOsVersion;
    private String mLatitude;
    private String mLongitude;
    private String mAppVersion;

    public RequestValues(String deviceId, String mobile, String countryCode, String email,
        String password, int deviceType, int signUpType, int userType, int customerType,
        int termsAndCond,
        String ipAddress, String firstName, String lastName, String profilePicture,
        String deviceModel, String deviceMake, String deviceTime,
        String browserName, String deviceOsVersion, String latitude, String longitude,
        String appVersion) {
      this.mDeviceId = deviceId;
      this.mMobile = mobile;
      this.mCountryCode = countryCode;
      this.mEmail = email;
      this.mPassword = password;
      this.mDeviceType = deviceType;
      this.mSignUpType = signUpType;
      this.mUserType = userType;
      this.customerType = customerType;
      this.mTermsAndCond = termsAndCond;
      this.mIpAddress = ipAddress;
      this.mFirstName = firstName;
      this.mLastName = lastName;
      this.mProfilePicture = profilePicture;
      this.mDeviceModel = deviceModel;
      this.mDeviceMake = deviceMake;
      this.mAppVersion = appVersion;
      this.mBrowserName = browserName;
      this.mDeviceTime = deviceTime;
      this.mDeviceOsVersion = deviceOsVersion;
      this.mLatitude = latitude;
      this.mLongitude = longitude;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    public SignUpData mData;

    public ResponseValues(SignUpData data) {
      this.mData = data;
    }

    public SignUpData getData() {
      return mData;
    }

    public void setData(SignUpData data) {
      mData = data;
    }
  }
}
