package com.customer.domain.interactor;

import static com.customer.domain.DomainConstants.LOWER_BOUND;

import android.text.TextUtils;
import com.customer.domain.DomainUtil;
import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.productlisting.ProductListingData;
import com.customer.domain.repository.FilteredProductsRepository;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

public class FilteredProductsUseCase extends
    UseCase<FilteredProductsUseCase.RequestValues, FilteredProductsUseCase.ResponseValues> {
  private FilteredProductsRepository mRepository;

  @Inject
  public FilteredProductsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      FilteredProductsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    if (!DomainUtil.isEmptyMap(requestValues.mFilterMap) && TextUtils.isEmpty(
        requestValues.mCatName)) {
      return mRepository.getFilteredProductList(requestValues.mFilterMap, requestValues.mSortType,
          requestValues.page);
    } else if (!DomainUtil.isEmptyMap(requestValues.mFilterMap) && !TextUtils.isEmpty(
        requestValues.mCatName) && !TextUtils.isEmpty(
        requestValues.mSubCatName) && !TextUtils.isEmpty(
        requestValues.mSubSubCatName)) {
      return mRepository.getCategoryProductList(requestValues.mCatName, requestValues.mSubCatName,
          requestValues.mSubSubCatName,requestValues.mFilterMap, requestValues.mSortType, requestValues.page);
    } else if (!DomainUtil.isEmptyMap(requestValues.mFilterMap) && !TextUtils.isEmpty(
        requestValues.mCatName) && !TextUtils.isEmpty(
        requestValues.mSubCatName)) {
      return mRepository.getFilteredProductList(requestValues.mCatName, requestValues.mSubCatName,
          requestValues.mFilterMap, requestValues.mSortType, requestValues.page);
    } else if (!DomainUtil.isEmptyMap(requestValues.mFilterMap) && !TextUtils.isEmpty(
        requestValues.mCatName)) {
      return mRepository.getFilteredProductList(requestValues.mCatName, requestValues.mFilterMap,
          requestValues.mSortType, requestValues.page);
    } else if (!DomainUtil.isEmptyMap(requestValues.mFilterMap)
        && !TextUtils.isEmpty(requestValues.mSearchQuery)) {
      return mRepository.getFilteredProductList(requestValues.mSearchQuery,
          requestValues.mFilterMap,
          requestValues.mSortType, requestValues.page);
    } else if (!TextUtils.isEmpty(requestValues.mSearchQuery)) {
      return mRepository.getSearchProductList(requestValues.mSearchQuery, requestValues.mSortType,
          requestValues.page);
    } else if (!TextUtils.isEmpty(requestValues.mBrandName)) {
      return mRepository.getProductsUnderBrand(requestValues.mBrandName, requestValues.mSortType);
    } else if (requestValues.mType != LOWER_BOUND) {
      return mRepository.getProductsUnderBrand(requestValues.mType, requestValues.mName,
          requestValues.mInText);
    } else {
      return mRepository.getCategoryProductList(requestValues.mCatName,
          requestValues.mSubCatName,
          requestValues.mSubSubCatName, requestValues.mSortType, requestValues.page);
    }
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mCatName;
    private String mSubCatName;
    private String mSubSubCatName;
    private String mSearchQuery;
    private HashMap<Integer, Set<String>> mFilterMap;
    private int mSortType;
    private String mBrandName;
    private String mName;
    private int mType;
    private String mInText;
    private String page;

    public RequestValues(String name, int type, String page) {
      this.mName = name;
      this.mType = type;
      this.page = page;
    }

    public RequestValues(String name, int type, String inText, String page) {
      this.mName = name;
      this.mType = type;
      this.mInText = inText;
      this.page = page;
    }

    public RequestValues(String catName, String page) {
      this.mCatName = catName;
      this.page = page;
    }

    public RequestValues(int sortType, String brandName, String page) {
      this.mSortType = sortType;
      this.mBrandName = brandName;
      this.page = page;
    }

    public RequestValues(HashMap<Integer, Set<String>> filterMap, int sortType, String page) {
      this.mFilterMap = filterMap;
      this.mSortType = sortType;
      this.page = page;
    }

    public RequestValues(String catName, String subCatName,
        HashMap<Integer, Set<String>> filterMap, int sortType, String page) {
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mFilterMap = filterMap;
      this.mSortType = sortType;
      this.page = page;
    }

    public RequestValues(String catName, String subCatName, int sortType, String page) {
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mSortType = sortType;
      this.page = page;
    }

    public RequestValues(String catName, String subCatName, String subSubCatName,
        HashMap<Integer, Set<String>> filterMap, int sortType,
        String page) {
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mSubSubCatName = subSubCatName;
      this.mFilterMap = filterMap;
      this.mSortType = sortType;
      this.page = page;
    }

    public RequestValues(String catName, String subCatName, String subSubCatName, int sortType,
        String page) {
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mSubSubCatName = subSubCatName;
      this.mSortType = sortType;
      this.page = page;
    }

    public String getBrandName() {
      return mBrandName;
    }

    public void setBrandName(String brandName) {
      this.mBrandName = brandName;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public int getType() {
      return mType;
    }

    public void setType(int type) {
      this.mType = type;
    }

    public int getSortType() {
      return mSortType;
    }

    public void setSortType(int sortType) {
      this.mSortType = sortType;
    }

    public HashMap<Integer, Set<String>> getFilterMap() {
      return mFilterMap;
    }

    public void setFilterMap(HashMap<Integer, Set<String>> filterMap) {
      this.mFilterMap = filterMap;
    }

    public String getSearchQuery() {
      return mSearchQuery;
    }

    public void setSearchQuery(String searchQuery) {
      this.mSearchQuery = searchQuery;
    }

    public String getCatName() {
      return mCatName;
    }

    public void setCatName(String catName) {
      this.mCatName = catName;
    }

    public String getSubCatName() {
      return mSubCatName;
    }

    public void setSubCatName(String subCatName) {
      this.mSubCatName = subCatName;
    }

    public String getSubSubCatName() {
      return mSubSubCatName;
    }

    public void setSubSubCatName(String subSubCatName) {
      this.mSubSubCatName = subSubCatName;
    }

    public String getPage() {
      return page;
    }

    public void setPage(String page) {
      this.page = page;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ProductListingData mData;

    public ResponseValues(ProductListingData data) {
      this.mData = data;
    }

    public ProductListingData getData() {
      return mData;
    }

    public void setData(ProductListingData data) {
      this.mData = data;
    }
  }
}
