package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.ReorderRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ReorderUseCase extends
    UseCase<ReorderUseCase.RequestValues, ReorderUseCase.ResponseValues> {

  private ReorderRepository mRepository;

  @Inject
  public ReorderUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, ReorderRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.makeReorder(requestValues.type, requestValues.orderId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String type;
    private String orderId;

    public RequestValues(String type, String orderId) {
      this.type = type;
      this.orderId = orderId;
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
      mData = data;
    }
  }
}
