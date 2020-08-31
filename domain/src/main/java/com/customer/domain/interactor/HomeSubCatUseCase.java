package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.home.HomeListData;
import com.customer.domain.repository.HomeSubCategoryRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class HomeSubCatUseCase extends
    UseCase<HomeSubCatUseCase.RequestValues, HomeSubCatUseCase.ResponseValues> {

  private HomeSubCategoryRepository mRepository;

  @Inject
  public HomeSubCatUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      HomeSubCategoryRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getHomeSubCatProducts(requestValues.mSize, requestValues.mLimit);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private int mSize;
    private int mLimit;

    public RequestValues(int size, int limit) {
      this.mSize = size;
      this.mLimit = limit;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private HomeListData mData;

    public ResponseValues(HomeListData data) {
      this.mData = data;
    }

    public HomeListData getData() {
      return mData;
    }

    public void setData(HomeListData data) {
      this.mData = data;
    }
  }
}
