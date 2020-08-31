package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.brands.AllBrandsListData;
import com.customer.domain.model.pdp.ProductDataModel;
import com.customer.domain.repository.AllBrandsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class AllBrandsUseCase extends
    UseCase<AllBrandsUseCase.RequestValues, AllBrandsUseCase.ResponseValues> {
  private AllBrandsRepository mRepository;

  @Inject
  public AllBrandsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      AllBrandsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.allBrands(requestValues.mFrom, requestValues.mTo);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mFrom;
    private String mTo;

    public RequestValues(String from, String to) {
      this.mFrom = from;
      this.mTo = to;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private AllBrandsListData mData;

    public ResponseValues(AllBrandsListData data) {
      this.mData = data;
    }

    public AllBrandsListData getData() {
      return mData;
    }

    public void setData(AllBrandsListData data) {
      this.mData = data;
    }
  }
}
