package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.model.SignInData;
import com.customer.domain.repository.GenerateTokenRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GenerateTokenUseCase extends
    UseCase<GenerateTokenUseCase.RequestValues, GenerateTokenUseCase.ResponseValues> {
  private GenerateTokenRepository mRepository;

  @Inject
  public GenerateTokenUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GenerateTokenRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.generateToken();
  }

  public static class RequestValues implements UseCase.RequestValues {
    public RequestValues() {
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
