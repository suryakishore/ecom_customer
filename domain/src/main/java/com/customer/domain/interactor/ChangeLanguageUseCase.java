package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.brands.AllBrandsListData;
import com.customer.domain.model.changelan.ChangeLanData;
import com.customer.domain.repository.AllBrandsRepository;
import com.customer.domain.repository.ChangeLanguageRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ChangeLanguageUseCase extends
    UseCase<ChangeLanguageUseCase.RequestValues, ChangeLanguageUseCase.ResponseValues> {
  private ChangeLanguageRepository mRepository;

  @Inject
  public ChangeLanguageUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      ChangeLanguageRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.changeLanguage();
  }

  public static class RequestValues implements UseCase.RequestValues {

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ChangeLanData mData;

    public ResponseValues(ChangeLanData data) {
      this.mData = data;
    }

    public ChangeLanData getData() {
      return mData;
    }

    public void setData(ChangeLanData data) {
      this.mData = data;
    }
  }
}
