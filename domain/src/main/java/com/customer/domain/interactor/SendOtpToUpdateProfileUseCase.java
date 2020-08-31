package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.ForgotPasswordData;
import com.customer.domain.repository.SendOtpToUpdateProfileRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class SendOtpToUpdateProfileUseCase extends
    UseCase<SendOtpToUpdateProfileUseCase.RequestValues,
        SendOtpToUpdateProfileUseCase.ResponseValues> {

  private SendOtpToUpdateProfileRepository mRepository;

  @Inject
  public SendOtpToUpdateProfileUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      SendOtpToUpdateProfileRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {

    return mRepository.getOtpToUpdateProfile(requestValues.mMobNumber, requestValues.mCountryCode,
        requestValues.mMail, requestValues.mType);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mMobNumber;
    private String mCountryCode;
    private String mMail;
    private int mType;

    public RequestValues(String mobNumber, String countryCode, int type) {
      this.mMobNumber = mobNumber;
      this.mCountryCode = countryCode;
      this.mType = type;
    }

    public RequestValues(String mail, int type) {
      this.mMail = mail;
      this.mType = type;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ForgotPasswordData mData;

    public ResponseValues(ForgotPasswordData data) {
      mData = data;
    }

    public ForgotPasswordData getData() {
      return mData;
    }

    public void setData(ForgotPasswordData data) {
      mData = data;
    }
  }

}
