package com.customer.domain.repository;

import com.customer.domain.interactor.GetCancelReasonsUseCase;
import com.customer.domain.interactor.GetOfferProductsUseCase;
import io.reactivex.Single;

public interface GetReasonsRepository {
  Single<GetCancelReasonsUseCase.ResponseValues> getReasons();
}