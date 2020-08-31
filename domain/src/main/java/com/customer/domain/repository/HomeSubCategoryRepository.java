package com.customer.domain.repository;

import com.customer.domain.interactor.HomeSubCatUseCase;
import io.reactivex.Single;

public interface HomeSubCategoryRepository {

  Single<HomeSubCatUseCase.ResponseValues> getHomeSubCatProducts(int size, int limit);

}
