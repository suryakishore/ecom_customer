package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.home.HomeListData;
import com.customer.domain.repository.HomePageRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class HomePageUseCase extends
    UseCase<HomePageUseCase.RequestValues, HomePageUseCase.ResponseValues> {
  private HomePageRepository mRepository;

  @Inject
  public HomePageUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      HomePageRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.homePage();
  }

  public static class RequestValues implements UseCase.RequestValues {
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
