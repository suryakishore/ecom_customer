package com.customer.domain.repository;

import com.customer.domain.interactor.GetRatableAttributesUseCase;
import io.reactivex.Single;

public interface GetRatableAttributesRepository {
  Single<GetRatableAttributesUseCase.ResponseValues> getRatableAttributes(String productId);
}
