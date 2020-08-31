package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.LikeDisLikeReviewRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class LikeDisLikeReviewUseCase extends
    UseCase<LikeDisLikeReviewUseCase.RequestValues, LikeDisLikeReviewUseCase.ResponseValues> {

  private LikeDisLikeReviewRepository mRepository;

  @Inject
  public LikeDisLikeReviewUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      LikeDisLikeReviewRepository likeDisLikeReviewRepository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = likeDisLikeReviewRepository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    if (requestValues.mLike) {
      return mRepository.likeReview(requestValues.mReviewId, requestValues.mLike);
    } else {
      return mRepository.disLikeReview(requestValues.mDislike, requestValues.mReviewId);
    }
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mReviewId;
    private boolean mLike = false;
    private boolean mDislike = false;

    public RequestValues(String reviewId, boolean like) {
      this.mReviewId = reviewId;
      this.mLike = like;
    }

    public RequestValues(Boolean dislike, String reviewId) {
      this.mReviewId = reviewId;
      this.mDislike = dislike;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CommonData mData;

    public ResponseValues(CommonData data) {
      mData = data;
    }

    public CommonData getData() {
      return mData;
    }

    public void setData(CommonData data) {
      mData = data;
    }
  }
}
