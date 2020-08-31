package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.filter.FilterData;
import com.customer.domain.repository.GetFilterParamsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetFilterParamsUseCase extends
    UseCase<GetFilterParamsUseCase.RequestValues, GetFilterParamsUseCase.ResponseValues> {
  private GetFilterParamsRepository mRepository;

  @Inject
  public GetFilterParamsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetFilterParamsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getFilterParams(requestValues.mLevel, requestValues.mCatName,
        requestValues.mSubCatName,
        requestValues.mSubSubCatName, requestValues.mSearchQuery, requestValues.mBrandName);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private int mLevel;
    private String mCatName;
    private String mSubCatName;
    private String mSubSubCatName;
    private String mBrandName;
    private String mSearchQuery;

    public RequestValues(String searchQuery) {
      this.mSearchQuery = searchQuery;
    }

    public RequestValues(int level, String catName) {
      this.mLevel = level;
      this.mCatName = catName;
    }

    public RequestValues(String brandName, int level) {
      this.mLevel = level;
      this.mBrandName = brandName;
    }

    public RequestValues(int level, String catName, String subCatName) {
      this.mLevel = level;
      this.mCatName = catName;
      this.mSubCatName = subCatName;
    }

    public RequestValues(int level, String catName, String subCatName,
        String subSubCatName) {
      this.mLevel = level;
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mSubSubCatName = subSubCatName;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private FilterData mData;

    public ResponseValues(FilterData data) {
      this.mData = data;
    }

    public FilterData getData() {
      return mData;
    }

    public void setData(FilterData data) {
      this.mData = data;
    }
  }
}
