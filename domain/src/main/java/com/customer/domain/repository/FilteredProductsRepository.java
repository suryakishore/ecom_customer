package com.customer.domain.repository;

import com.customer.domain.interactor.FilteredProductsUseCase.ResponseValues;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.Set;

public interface FilteredProductsRepository {

  Single<ResponseValues> getCategoryProductList(String catName, String subCatName,
      String subSubCatName, int sortType,String page);

  Single<ResponseValues> getCategoryProductList(String catName, String subCatName,
      String subSubCatName,HashMap<Integer, Set<String>> filterMap, int sortType,String page);

  Single<ResponseValues> getSearchProductList(String searchQuery, int sortType,String page);

  Single<ResponseValues> getFilteredProductList(HashMap<Integer, Set<String>> filterMap,
      int sortType,String page);

  Single<ResponseValues> getFilteredProductList(String catName, String subCatName,
      HashMap<Integer, Set<String>> filterMap,
      int sortType,String page);

  Single<ResponseValues> getFilteredProductList(HashMap<Integer, Set<String>> filterMap,
      String searchQuery,
      int sortType,String page);

  Single<ResponseValues> getFilteredProductList(String catName,
      HashMap<Integer, Set<String>> filterMap,
      int sortType,String page);

  Single<ResponseValues> getProductsUnderBrand(String brandName, int sortType);

  Single<ResponseValues> getProductsUnderBrand(int type, String name,String inText);
}
