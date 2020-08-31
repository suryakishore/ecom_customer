package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.SignInData;
import com.customer.domain.repository.VerifyOtpRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class VerifyOTPUseCase extends
    UseCase<VerifyOTPUseCase.RequestValues, VerifyOTPUseCase.ResponseValues> {

  private VerifyOtpRepository mVerifyOtpRepository;

  @Inject
  public VerifyOTPUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      VerifyOtpRepository signInRepository) {
    super(threadExecutor, postExecutionThread);
    this.mVerifyOtpRepository = signInRepository;
  }

  @Override
  protected Single<VerifyOTPUseCase.ResponseValues> buildUseCaseObservable(
      VerifyOTPUseCase.RequestValues requestValues) {
    return mVerifyOtpRepository.verifyOtp(requestValues.mOtpId, requestValues.mOtpCode,
        requestValues.mVerifyType);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mOtpId;
    private String mOtpCode;
    private int mVerifyType;

    public RequestValues(String otpId, String otpCode, int verifyType) {
      this.mOtpId = otpId;
      this.mOtpCode = otpCode;
      this.mVerifyType = verifyType;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    public SignInData mData;

    public ResponseValues(SignInData data) {
      this.mData = data;
    }

    public SignInData getData() {
      return mData;
    }

    public void setData(SignInData data) {
      mData = data;
    }
  }
}
