package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.SignInData;
import com.customer.domain.model.ordercount.OrderCountListData;
import com.customer.domain.repository.AppConfigRepository;
import com.customer.domain.repository.OrderCountRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class OrderCountUseCase extends
    UseCase<OrderCountUseCase.RequestValues, OrderCountUseCase.ResponseValues> {
  private OrderCountRepository mRepository;

  @Inject
  public OrderCountUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      OrderCountRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.orderCount();
  }

  public static class RequestValues implements UseCase.RequestValues {
    public RequestValues() {
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private OrderCountListData mData;

    public ResponseValues(OrderCountListData data) {
      this.mData = data;
    }

    public OrderCountListData getData() {
      return mData;
    }

    public void setData(OrderCountListData data) {
      this.mData = data;
    }
  }
}
