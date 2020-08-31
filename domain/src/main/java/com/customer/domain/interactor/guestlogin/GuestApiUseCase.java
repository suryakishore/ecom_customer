package com.customer.domain.interactor.guestlogin;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.model.guestsignin.GuestSignInData;
import com.customer.domain.repository.GuestLogInRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GuestApiUseCase extends UseCase<GuestApiUseCase.RequestValues,
    GuestApiUseCase.ResponseValues> {
  private GuestLogInRepository guestLogInRepository;

  @Inject
  public GuestApiUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GuestLogInRepository guestLogInRepository) {
    super(threadExecutor, postExecutionThread);
    this.guestLogInRepository = guestLogInRepository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return guestLogInRepository.guestSignIn(requestValues.mDeviceId,
        requestValues.mAppVersion, requestValues.mDeviceType, requestValues.mDeviceMake,
        requestValues.mDeviceModel, requestValues.mDeviceOsVersion,requestValues.mIpAddress,requestValues.mBrowserName,requestValues.mBrowserVersion,
        requestValues.mDeviceTime,requestValues.mLatitude,requestValues.mLongitude
        );
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mDeviceId;
    private int mDeviceType;
    private String mDeviceMake;
    private String mDeviceModel;
    private String mAppVersion;
    private String mDeviceOsVersion;
    private String mIpAddress;
    private String mBrowserName;
    private String mBrowserVersion;
    private String mDeviceTime;
    private String mLatitude;
    private String mLongitude;

    public RequestValues(String deviceId, String appVersion, int deviceType, String deviceMake,
        String deviceModel, String deviceOsVersion,String ipAddress, String browserName,
        String browserVersion, String deviceTime,String latitude,String longitude) {
      this.mDeviceId = deviceId;
      this.mDeviceType = deviceType;
      this.mDeviceMake = deviceMake;
      this.mDeviceModel = deviceModel;
      this.mAppVersion = appVersion;
      this.mDeviceOsVersion = deviceOsVersion;
      this.mIpAddress=ipAddress;
      this.mBrowserName=browserName;
      this.mBrowserVersion=browserVersion;
      this.mDeviceTime=deviceTime;
      this.mLatitude=latitude;
      this.mLongitude=longitude;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private GuestSignInData mData;

    public ResponseValues(GuestSignInData data) {
      this.mData = data;
    }

    public GuestSignInData getData() {
      return mData;
    }

    public void setData(GuestSignInData data) {
      mData = data;
    }
  }
}
