package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.ForgotPasswordData;
import com.customer.domain.repository.SendOtpRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class SendOtpUseCase extends
    UseCase<SendOtpUseCase.RequestValues, SendOtpUseCase.ResponseValues> {

  private SendOtpRepository mRepository;

  @Inject
  public SendOtpUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      SendOtpRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<SendOtpUseCase.ResponseValues> buildUseCaseObservable(
      RequestValues requestValues) {
    return mRepository.sendOTP(requestValues.mMobile, requestValues.mCountryCode,
        requestValues.mEmail, requestValues.mVerifyType, requestValues.mTriggeredBy,
        requestValues.mUserName, requestValues.mType
    );
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mMobile;

    private String mCountryCode;

    private String mEmail;

    private int mVerifyType;

    private String mTriggeredBy;

    private String mUserName;
    private int mType;

    public RequestValues(String mobile, String countryCode, String email, int verifyType,
        String triggeredBy, String userName, int type) {
      this.mMobile = mobile;
      this.mCountryCode = countryCode;
      this.mEmail = email;
      this.mVerifyType = verifyType;
      this.mTriggeredBy = triggeredBy;
      this.mUserName = userName;
      this.mType = type;
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
