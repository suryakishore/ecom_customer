package com.customer.domain.repository;

import com.customer.domain.interactor.GetCartProductsUseCase;
import io.reactivex.Single;

public interface GetCartProductsRepository {

  Single<GetCartProductsUseCase.ResponseValues> getCartProducts();
}
