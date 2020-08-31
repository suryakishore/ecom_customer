package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.CancelOrderRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class CancelOrderUseCase extends
    UseCase<CancelOrderUseCase.RequestValues, CancelOrderUseCase.ResponseValues> {

  private CancelOrderRepository mRepository;

  @Inject
  public CancelOrderUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, CancelOrderRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.cancelOrder(requestValues.type, requestValues.orderId,
        requestValues.reason, requestValues.comment);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String type;
    private String orderId;
    private String reason;
    private String comment;

    public RequestValues(String type, String orderId, String reason, String comment) {
      this.type = type;
      this.orderId = orderId;
      this.reason = reason;
      this.comment = comment;
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
