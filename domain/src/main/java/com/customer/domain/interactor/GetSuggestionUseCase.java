package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.suggestion.SuggestionListData;
import com.customer.domain.repository.SuggestionRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetSuggestionUseCase extends
    UseCase<GetSuggestionUseCase.RequestValues, GetSuggestionUseCase.ResponseValues> {
  private SuggestionRepository mRepository;

  @Inject
  public GetSuggestionUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      SuggestionRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.suggestion(requestValues.mSearchItem);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mSearchItem;

    public RequestValues(String searchItem) {
      this.mSearchItem = searchItem;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private SuggestionListData mData;

    public ResponseValues(SuggestionListData data) {
      mData = data;
    }

    public SuggestionListData getData() {
      return mData;
    }

    public void setData(SuggestionListData data) {
      mData = data;
    }
  }
}
