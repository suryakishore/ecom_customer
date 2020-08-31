package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.ClearWishListRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ClearWishListUseCase extends
    UseCase<ClearWishListUseCase.RequestValues,
        ClearWishListUseCase.ResponseValues> {

  private ClearWishListRepository mRepository;

  @Inject
  public ClearWishListUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, ClearWishListRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.clearWishList();
  }

  public static class RequestValues implements UseCase.RequestValues {
    public RequestValues() {
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
