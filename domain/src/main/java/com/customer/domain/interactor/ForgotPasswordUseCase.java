package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.ForgotPasswordData;
import com.customer.domain.repository.ForgotPasswordRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ForgotPasswordUseCase extends
    UseCase<ForgotPasswordUseCase.RequestValues, ForgotPasswordUseCase.ResponseValues> {
  private ForgotPasswordRepository mRepository;

  @Inject
  public ForgotPasswordUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      ForgotPasswordRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.forgotPassword(requestValues.mVerifyType, requestValues.mEmail,
        requestValues.mCountryCode,
        requestValues.mMobile, requestValues.mDeviceId, requestValues.mDeviceMake,
        requestValues.mDeviceModel, requestValues.mDeviceType);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private int mVerifyType;

    private String mEmail;

    private String mCountryCode;

    private String mMobile;

    private String mDeviceId;

    private String mDeviceMake;

    private String mDeviceModel;

    private String mDeviceType;

    public RequestValues(int verifyType, String email, String countryCode, String mobile,
        String deviceId, String deviceMake, String deviceModel, String deviceType) {
      this.mVerifyType = verifyType;
      this.mEmail = email;
      this.mCountryCode = countryCode;
      this.mMobile = mobile;
      this.mDeviceId = deviceId;
      this.mDeviceMake = deviceMake;
      this.mDeviceModel = deviceModel;
      this.mDeviceType = deviceType;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private ForgotPasswordData mData;

    public ResponseValues(ForgotPasswordData data) {
      this.mData = data;
    }

    public ForgotPasswordData getData() {
      return mData;
    }

    public void setData(ForgotPasswordData data) {
      this.mData = data;
    }
  }
}
