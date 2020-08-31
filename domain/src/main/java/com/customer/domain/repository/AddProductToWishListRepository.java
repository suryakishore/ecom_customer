package com.customer.domain.repository;

import com.customer.domain.interactor.AddProductToWishListUseCase;
import io.reactivex.Single;

public interface AddProductToWishListRepository {

  Single<AddProductToWishListUseCase.ResponseValues> addProductWishList(String productId,
      String ipAddress, double latitude, double longitude);
}
