package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.recentlyviewed.RecentlyViewedData;
import com.customer.domain.repository.GetRecentlyViewedProductsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetRecentlyViewedProductsUseCase extends
    UseCase<GetRecentlyViewedProductsUseCase.RequestValues,
        GetRecentlyViewedProductsUseCase.ResponseValues> {
  private GetRecentlyViewedProductsRepository mRepository;

  @Inject
  public GetRecentlyViewedProductsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetRecentlyViewedProductsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getRecentlyViewedProducts();
  }

  public static class RequestValues implements UseCase.RequestValues {

    public RequestValues() {

    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private RecentlyViewedData mData;

    public ResponseValues(RecentlyViewedData data) {
      this.mData = data;
    }

    public RecentlyViewedData getData() {
      return mData;
    }

    public void setData(RecentlyViewedData data) {
      this.mData = data;
    }
  }
}
