package com.customer.domain.repository;

import com.customer.domain.interactor.ProductCategoryUseCase.ResponseValues;
import io.reactivex.Single;

public interface ProductCategoryRepository {

  Single<ResponseValues> getProductCategory(String catId, String subCatId);

  Single<ResponseValues> getProductCategory(int from, int to);
}
