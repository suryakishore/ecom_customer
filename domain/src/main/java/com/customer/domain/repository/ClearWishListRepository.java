package com.customer.domain.repository;

import com.customer.domain.interactor.ClearWishListUseCase;
import io.reactivex.Single;

public interface ClearWishListRepository {

  Single<ClearWishListUseCase.ResponseValues> clearWishList();
}

