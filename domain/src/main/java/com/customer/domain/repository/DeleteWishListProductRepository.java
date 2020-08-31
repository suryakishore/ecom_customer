package com.customer.domain.repository;

import com.customer.domain.interactor.DeleteWishListProductUseCase;
import io.reactivex.Single;

public interface DeleteWishListProductRepository {

  Single<DeleteWishListProductUseCase.ResponseValues> deleteWishListProduct(String productId,
      String ipAddress, double latitude, double longitude);
}
