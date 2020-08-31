package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.VerifyProfileOtpRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class VerifyProfileOtpUseCase extends
    UseCase<VerifyProfileOtpUseCase.RequestValues,
        VerifyProfileOtpUseCase.ResponseValues> {

  private VerifyProfileOtpRepository mRepository;

  @Inject
  public VerifyProfileOtpUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, VerifyProfileOtpRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.verifyProfileOtp(requestValues.mCountryCode, requestValues.mOtpId,
        requestValues.mOtpCode, requestValues.mMobile, requestValues.mEmail, requestValues.mType);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mCountryCode;
    private String mOtpId;
    private String mOtpCode;
    private String mMobile;
    private String mEmail;
    private int mType;

    public RequestValues(String countryCode, String otpId, String otpCode, String mobile,
        int type) {
      this.mCountryCode = countryCode;
      this.mOtpId = otpId;
      this.mOtpCode = otpCode;
      this.mMobile = mobile;
      this.mType = type;
    }

    public RequestValues(String otpId, String otpCode, String email, int type) {
      this.mOtpId = otpId;
      this.mOtpCode = otpCode;
      this.mEmail = email;
      this.mType = type;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CommonData mData;

    public ResponseValues(CommonData data) {
      this.mData = data;
    }

    public CommonData getData() {
      return mData;
    }

    public void setData(CommonData data) {
      this.mData = data;
    }
  }
}
