package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.getratable.RatableData;
import com.customer.domain.repository.GetRatableAttributesRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetRatableAttributesUseCase extends
    UseCase<GetRatableAttributesUseCase.RequestValues, GetRatableAttributesUseCase.ResponseValues> {

  private GetRatableAttributesRepository mRepository;

  @Inject
  public GetRatableAttributesUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetRatableAttributesRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getRatableAttributes(requestValues.mProductId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mProductId;

    public RequestValues(String productId) {
      this.mProductId = productId;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private RatableData mData;

    public ResponseValues(RatableData data) {
      this.mData = data;
    }

    public RatableData getData() {
      return mData;
    }

    public void setData(RatableData data) {
      this.mData = data;
    }
  }
}
