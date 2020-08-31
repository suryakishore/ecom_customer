package com.customer.domain.repository;

import com.customer.domain.interactor.GetWishListUseCase;
import io.reactivex.Single;

public interface GetWishListRepository {

  Single<GetWishListUseCase.ResponseValues> getWishListProducts(int sortType, String searchQuery);
}
