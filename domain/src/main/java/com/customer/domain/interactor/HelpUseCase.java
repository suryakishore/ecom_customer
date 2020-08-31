package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.changelan.ChangeLanData;
import com.customer.domain.model.help.HelpListData;
import com.customer.domain.repository.ChangeLanguageRepository;
import com.customer.domain.repository.HelpRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class HelpUseCase extends
    UseCase<HelpUseCase.RequestValues, HelpUseCase.ResponseValues> {
  private HelpRepository mRepository;

  @Inject
  public HelpUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      HelpRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.help();
  }

  public static class RequestValues implements UseCase.RequestValues {

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private HelpListData mData;

    public ResponseValues(HelpListData data) {
      this.mData = data;
    }

    public HelpListData getData() {
      return mData;
    }

    public void setData(HelpListData data) {
      this.mData = data;
    }
  }
}
