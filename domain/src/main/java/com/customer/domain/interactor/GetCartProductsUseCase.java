package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.repository.GetCartProductsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetCartProductsUseCase extends
    UseCase<GetCartProductsUseCase.RequestValues, GetCartProductsUseCase.ResponseValues> {

  private GetCartProductsRepository mRepository;

  @Inject
  public GetCartProductsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetCartProductsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getCartProducts();
  }

  public static class RequestValues implements UseCase.RequestValues {
    public RequestValues() {
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CartData mData;

    public ResponseValues(CartData data) {
      this.mData = data;
    }

    public CartData getData() {
      return mData;
    }

    public void setData(CartData data) {
      this.mData = data;
    }
  }
}
