package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.homesubcategory.HomeCatData;
import com.customer.domain.repository.GetOfferProductsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetOfferProductsUseCase extends
    UseCase<GetOfferProductsUseCase.RequestValues, GetOfferProductsUseCase.ResponseValues> {
  private GetOfferProductsRepository mRepository;

  @Inject
  public GetOfferProductsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetOfferProductsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getOfferProducts(requestValues.mSkip, requestValues.mSize);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int mSkip;
    private int mSize;

    public RequestValues(int skip, int size) {
      this.mSkip = skip;
      this.mSize = size;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private HomeCatData mData;

    public ResponseValues(HomeCatData data) {
      this.mData = data;
    }

    public HomeCatData getData() {
      return mData;
    }

    public void setData(HomeCatData data) {
      this.mData = data;
    }
  }
}
