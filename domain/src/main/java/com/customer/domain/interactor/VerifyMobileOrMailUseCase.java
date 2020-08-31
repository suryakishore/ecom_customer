package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.VerifyMobileOrMailData;
import com.customer.domain.repository.VerifyMobileOrMailRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class VerifyMobileOrMailUseCase extends
    UseCase<VerifyMobileOrMailUseCase.RequestValues, VerifyMobileOrMailUseCase.ResponseValues> {

  private VerifyMobileOrMailRepository mRepository;

  @Inject
  public VerifyMobileOrMailUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      VerifyMobileOrMailRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<VerifyMobileOrMailUseCase.ResponseValues> buildUseCaseObservable(
      RequestValues requestValues) {
    return mRepository.signIn(requestValues.mCountryCode, requestValues.mMobile,
        requestValues.mVerifyType, requestValues.mEmail);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mCountryCode;

    private String mMobile;

    private int mVerifyType;

    private String mEmail;

    public RequestValues(String countryCode, String mobile, int verifyType, String email) {
      this.mCountryCode = countryCode;
      this.mMobile = mobile;
      this.mVerifyType = verifyType;
      this.mEmail = email;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private VerifyMobileOrMailData mData;

    public ResponseValues(VerifyMobileOrMailData data) {
      this.mData = data;
    }

    public VerifyMobileOrMailData getData() {
      return mData;
    }

    public void setData(VerifyMobileOrMailData data) {
      this.mData = data;
    }
  }
}
