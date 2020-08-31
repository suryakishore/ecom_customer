package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.cancelreasons.CancelReasonsData;
import com.customer.domain.repository.GetReasonsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetCancelReasonsUseCase extends
    UseCase<GetCancelReasonsUseCase.RequestValues, GetCancelReasonsUseCase.ResponseValues> {
  private GetReasonsRepository mRepository;

  @Inject
  public GetCancelReasonsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetReasonsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getReasons();
  }

  public static class RequestValues implements UseCase.RequestValues {
    public RequestValues() {
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CancelReasonsData mData;

    public ResponseValues(CancelReasonsData data) {
      this.mData = data;
    }

    public CancelReasonsData getData() {
      return mData;
    }

    public void setData(CancelReasonsData data) {
      this.mData = data;
    }
  }
}
