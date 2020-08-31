package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.pdp.ProductDataModel;
import com.customer.domain.repository.GetAllReviewsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetAllReviewsUseCase extends
    UseCase<GetAllReviewsUseCase.RequestValues, GetAllReviewsUseCase.ResponseValues> {
  private GetAllReviewsRepository mRepository;

  @Inject
  public GetAllReviewsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetAllReviewsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getAllReviews(requestValues.mParentProductId, requestValues.mSkip,
        requestValues.mLimit);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mParentProductId;
    private String mSkip;
    private String mLimit;

    public RequestValues(String parentProductId, String skip, String limit) {
      this.mParentProductId = parentProductId;
      this.mSkip = skip;
      this.mLimit = limit;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ProductDataModel mData;

    public ResponseValues(ProductDataModel data) {
      this.mData = data;
    }

    public ProductDataModel getData() {
      return mData;
    }

    public void setData(ProductDataModel data) {
      this.mData = data;
    }
  }
}
