package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.ReportReviewRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ReportReviewUseCase extends
    UseCase<ReportReviewUseCase.RequestValues, ReportReviewUseCase.ResponseValues> {

  private ReportReviewRepository mRepository;

  @Inject
  public ReportReviewUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      ReportReviewRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.reportReview(requestValues.mReviewId);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mReviewId;

    public RequestValues(String reviewId) {
      this.mReviewId = reviewId;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CommonData mData;

    public ResponseValues(CommonData data) {
      this.mData = data;
    }

    public CommonData getData() {
      return mData;
    }

    public void setData(CommonData data) {
      this.mData = data;
    }
  }
}
