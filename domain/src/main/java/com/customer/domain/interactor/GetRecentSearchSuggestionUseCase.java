package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.recentsearch.RecentSearchData;
import com.customer.domain.repository.GetRecentSearchSuggestionRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetRecentSearchSuggestionUseCase extends
    UseCase<GetRecentSearchSuggestionUseCase.RequestValues,
        GetRecentSearchSuggestionUseCase.ResponseValues> {

  private GetRecentSearchSuggestionRepository mRepository;

  @Inject
  public GetRecentSearchSuggestionUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetRecentSearchSuggestionRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getRecentSearchSuggestion();
  }

  public static class RequestValues implements UseCase.RequestValues {

  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private RecentSearchData mData;

    public ResponseValues(RecentSearchData data) {
      this.mData = data;
    }

    public RecentSearchData getData() {
      return mData;
    }

    public void setData(RecentSearchData data) {
      this.mData = data;
    }
  }
}
