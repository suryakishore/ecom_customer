package com.customer.domain.repository;

import com.customer.domain.interactor.FilterUseCase;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.Set;

public interface FilterRepository {

  Single<FilterUseCase.ResponseValues> categoryFilter(String catName,
      HashMap<Integer, Set<String>> filterMap, int sortType);

  Single<FilterUseCase.ResponseValues> subCategoryFilter(HashMap<Integer, Set<String>> filterMap,
      String catName, String subCatName, int sortType);

  Single<FilterUseCase.ResponseValues> subSubCategoryFilter(HashMap<Integer, Set<String>> filterMap,
      String catName, String subCatName, String subSubCatName, int sortType);

  Single<FilterUseCase.ResponseValues> brandFilter(HashMap<Integer, Set<String>> filterMap,
      String brandName, int sortType);

  Single<FilterUseCase.ResponseValues> searchFilter(HashMap<Integer, Set<String>> filterMap,
      String searchQuery, int sortType);

  Single<FilterUseCase.ResponseValues> offerFilter(HashMap<Integer, Set<String>> filterMap,
      String offerId, int sortType);
}
