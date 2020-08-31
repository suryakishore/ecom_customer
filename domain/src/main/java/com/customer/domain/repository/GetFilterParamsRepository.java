package com.customer.domain.repository;

import com.customer.domain.interactor.GetFilterParamsUseCase.ResponseValues;
import io.reactivex.Single;

public interface GetFilterParamsRepository {
  Single<ResponseValues> getFilterParams(int level, String catName, String subCatName,
      String subSubCatName, String searchQuery, String brandName);

}
