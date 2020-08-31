package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.SignInData;
import com.customer.domain.repository.SignInRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class SignInUseCase
    extends UseCase<SignInUseCase.RequestValues, SignInUseCase.ResponseValues> {
  private SignInRepository mRepository;

  @Inject
  public SignInUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      SignInRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<SignInUseCase.ResponseValues> buildUseCaseObservable(
      RequestValues requestValues) {
    return mRepository.signIn(requestValues.mMobile, requestValues.mCountryCode,
        requestValues.mEmail, requestValues.mPassword, requestValues.mVerifyType,
        requestValues.mLoginType, requestValues.mDeviceId, requestValues.mDeviceType,
        requestValues.mIpAddress, requestValues.mGoogleId, requestValues.mFacebookId,
        requestValues.mDeviceModel, requestValues.mDeviceMake,
        requestValues.mBrowserName, requestValues.mDeviceOsVersion, requestValues.mLatitude,
        requestValues.mLongitude, requestValues.mAppVersion
    );
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mMobile;
    private String mCountryCode;
    private String mEmail;
    private String mPassword;
    private int mVerifyType;
    private int mLoginType;
    private String mDeviceId;
    private int mDeviceType;
    private String mIpAddress;
    private String mGoogleId;
    private String mFacebookId;
    private String mDeviceModel;
    private String mDeviceMake;
    private String mBrowserName;
    private String mDeviceOsVersion;
    private String mLatitude;
    private String mLongitude;
    private String mAppVersion;

    public RequestValues(String mobile, String countryCode, String email, String password,
        int verifyType, int loginType, String deviceId, int deviceType, String ipAddress,
        String googleId, String facebookId, String deviceMake, String deviceModel,
        String browserName,
        String deviceOsVersion, String latitude, String longitude, String appVersion) {
      this.mMobile = mobile;
      this.mCountryCode = countryCode;
      this.mEmail = email;
      this.mPassword = password;
      this.mVerifyType = verifyType;
      this.mLoginType = loginType;
      this.mDeviceId = deviceId;
      this.mDeviceType = deviceType;
      this.mIpAddress = ipAddress;
      this.mGoogleId = googleId;
      this.mFacebookId = facebookId;
      this.mDeviceMake = deviceMake;
      this.mDeviceModel = deviceModel;
      this.mBrowserName = browserName;
      this.mDeviceOsVersion = deviceOsVersion;
      this.mLatitude = latitude;
      this.mLongitude = longitude;
      this.mAppVersion = appVersion;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private SignInData mData;

    public ResponseValues(SignInData data) {
      this.mData = data;
    }

    public SignInData getData() {
      return mData;
    }

    public void setData(SignInData data) {
      this.mData = data;
    }
  }
}
