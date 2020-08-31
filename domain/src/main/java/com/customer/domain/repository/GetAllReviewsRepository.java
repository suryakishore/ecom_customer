package com.customer.domain.repository;

import com.customer.domain.interactor.GetAllReviewsUseCase;
import io.reactivex.Single;

public interface GetAllReviewsRepository {

  Single<GetAllReviewsUseCase.ResponseValues> getAllReviews(String parentProductId, String skip,
      String limit);
}
