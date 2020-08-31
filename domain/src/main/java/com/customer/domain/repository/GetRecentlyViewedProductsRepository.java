package com.customer.domain.repository;

import com.customer.domain.interactor.GetRecentlyViewedProductsUseCase;
import io.reactivex.Single;

public interface GetRecentlyViewedProductsRepository {
  Single<GetRecentlyViewedProductsUseCase.ResponseValues> getRecentlyViewedProducts();
}
