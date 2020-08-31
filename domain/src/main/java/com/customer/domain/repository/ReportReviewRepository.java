package com.customer.domain.repository;

import com.customer.domain.interactor.ReportReviewUseCase;
import io.reactivex.Single;

public interface ReportReviewRepository {

  Single<ReportReviewUseCase.ResponseValues> reportReview(String reviewId);
}
