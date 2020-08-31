package com.customer.domain.repository;

import com.customer.domain.interactor.LikeDisLikeReviewUseCase;
import io.reactivex.Single;

public interface LikeDisLikeReviewRepository {

  Single<LikeDisLikeReviewUseCase.ResponseValues> likeReview(String reviewId, boolean like);

  Single<LikeDisLikeReviewUseCase.ResponseValues> disLikeReview(boolean disLike, String reviewId);

}
