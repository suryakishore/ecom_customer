package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.tracking.TrackingData;
import com.customer.domain.repository.TrackingRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class TrackingOrderUseCase extends
    UseCase<TrackingOrderUseCase.RequestValues, TrackingOrderUseCase.ResponseValues> {
  private TrackingRepository mRepository;

  @Inject
  public TrackingOrderUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, TrackingRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.trackOrder(requestValues.productOrderId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String productOrderId;

    public RequestValues(String productOrderId) {
      this.productOrderId = productOrderId;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private TrackingData mData;

    public ResponseValues(TrackingData data) {
      this.mData = data;
    }

    public TrackingData getData() {
      return mData;
    }

    public void setData(TrackingData data) {
      this.mData = data;
    }
  }
}
