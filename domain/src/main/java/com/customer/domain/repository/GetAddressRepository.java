package com.customer.domain.repository;

import com.customer.domain.interactor.GetAddressUseCase;
import io.reactivex.Single;

public interface GetAddressRepository {
  Single<GetAddressUseCase.ResponseValues> getFilteredProductList();
}
