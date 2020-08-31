package com.customer.domain.repository;

import com.customer.domain.interactor.GetOfferProductsUseCase;
import io.reactivex.Single;

public interface GetOfferProductsRepository {

  Single<GetOfferProductsUseCase.ResponseValues> getOfferProducts(int skip, int size);
}
