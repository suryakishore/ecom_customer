package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.orderdetails.MasterOrderData;
import com.customer.domain.model.orderdetails.OrderData;
import com.customer.domain.repository.GetOrderDetailsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetOrderDetailsUseCase extends
    UseCase<GetOrderDetailsUseCase.RequestValues, GetOrderDetailsUseCase.ResponseValues> {
  private GetOrderDetailsRepository mRepository;

  @Inject
  public GetOrderDetailsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetOrderDetailsRepository getOrderDetailsRepository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = getOrderDetailsRepository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getOrderDetails(requestValues.mType, requestValues.mOrderId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mType;
    private String mOrderId;

    public RequestValues(String type, String orderId) {
      mType = type;
      mOrderId = orderId;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private MasterOrderData mData;

    public ResponseValues(MasterOrderData data) {
      mData = data;
    }

    public MasterOrderData getData() {
      return mData;
    }

    public void setData(MasterOrderData data) {
      mData = data;
    }
  }
}
