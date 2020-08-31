package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.orderdetails.OrderData;
import com.customer.domain.repository.GetPackageDetailsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetPackageDetailsUseCase extends
    UseCase<GetPackageDetailsUseCase.RequestValues, GetPackageDetailsUseCase.ResponseValues> {
  private GetPackageDetailsRepository mRepository;

  @Inject
  public GetPackageDetailsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetPackageDetailsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getPackageDetails(requestValues.productOrderId, requestValues.packageId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String productOrderId;
    private String packageId;

    public RequestValues(String productOrderId, String packageId) {
      this.productOrderId = productOrderId;
      this.packageId = packageId;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private OrderData mData;

    public ResponseValues(OrderData data) {
      this.mData = data;
    }

    public OrderData getData() {
      return mData;
    }

    public void setData(OrderData data) {
      this.mData = data;
    }
  }
}
