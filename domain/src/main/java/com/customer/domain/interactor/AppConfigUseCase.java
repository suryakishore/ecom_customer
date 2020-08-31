package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.SignInData;
import com.customer.domain.repository.AppConfigRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class AppConfigUseCase extends
    UseCase<AppConfigUseCase.RequestValues, AppConfigUseCase.ResponseValues> {
  private AppConfigRepository mRepository;

  @Inject
  public AppConfigUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      AppConfigRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.appConfig(requestValues.mBackGroundFlag);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int mBackGroundFlag;

    public RequestValues(int backGroundFlag) {
      this.mBackGroundFlag = backGroundFlag;
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
