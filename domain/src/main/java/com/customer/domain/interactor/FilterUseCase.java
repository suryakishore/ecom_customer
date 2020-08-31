package com.customer.domain.interactor;

import static com.customer.domain.DomainConstants.BRAND_LEVEL;
import static com.customer.domain.DomainConstants.CAT_LEVEL;
import static com.customer.domain.DomainConstants.OFFER_LEVEL;
import static com.customer.domain.DomainConstants.SEARCH_LEVEL;
import static com.customer.domain.DomainConstants.SUB_CAT_LEVEL;
import static com.customer.domain.DomainConstants.SUB_SUB_CAT_LEVEL;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.productlisting.ProductListingData;
import com.customer.domain.repository.FilterRepository;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

public class FilterUseCase extends
    UseCase<FilterUseCase.RequestValues, FilterUseCase.ResponseValues> {
  private FilterRepository mRepository;

  @Inject
  public FilterUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, FilterRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {

    switch (requestValues.mFilterFlow) {
      case CAT_LEVEL:
        return mRepository.categoryFilter(requestValues.mCatName, requestValues.mFilterMap,
            requestValues.mSortType);
      case SUB_CAT_LEVEL:
        return mRepository.subCategoryFilter(requestValues.mFilterMap, requestValues.mCatName,
            requestValues.mSubCatName, requestValues.mSortType);
      case SUB_SUB_CAT_LEVEL:
        return mRepository.subSubCategoryFilter(requestValues.mFilterMap, requestValues.mCatName,
            requestValues.mSubCatName, requestValues.mSubSubCatName, requestValues.mSortType);
      case BRAND_LEVEL:
        return mRepository.brandFilter(requestValues.mFilterMap, requestValues.mBrandName,
            requestValues.mSortType);
      case SEARCH_LEVEL:
        return mRepository.searchFilter(requestValues.mFilterMap, requestValues.mSearchQuery,
            requestValues.mSortType);
      case OFFER_LEVEL:
        return mRepository.offerFilter(requestValues.mFilterMap, requestValues.mOfferId,
            requestValues.mSortType);
    }
    return null;
  }

  public static class RequestValues implements UseCase.RequestValues {
    private HashMap<Integer, Set<String>> mFilterMap;
    private String mCatName;
    private String mSubCatName;
    private String mSubSubCatName;
    private String mBrandName;
    private String mOfferId;
    private String mSearchQuery;
    private int mSortType;
    private int mFilterFlow;

    public RequestValues(HashMap<Integer, Set<String>> filterMap, String catName, int sortType) {
      this.mFilterMap = filterMap;
      this.mCatName = catName;
      this.mSortType = sortType;
      mFilterFlow = CAT_LEVEL;
    }

    public RequestValues(HashMap<Integer, Set<String>> filterMap, String catName,
        String subCatName, int sortType) {
      this.mFilterMap = filterMap;
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mSortType = sortType;
      mFilterFlow = SUB_CAT_LEVEL;
    }

    public RequestValues(HashMap<Integer, Set<String>> filterMap, String catName,
        String subCatName, String subSubCatName, int sortType) {
      this.mFilterMap = filterMap;
      this.mCatName = catName;
      this.mSubCatName = subCatName;
      this.mSubSubCatName = subSubCatName;
      this.mSortType = sortType;
      mFilterFlow = SUB_SUB_CAT_LEVEL;
    }

    public RequestValues(String brandName, HashMap<Integer, Set<String>> filterMap, int sortType) {
      this.mFilterMap = filterMap;
      this.mBrandName = brandName;
      this.mSortType = sortType;
      this.mFilterFlow = BRAND_LEVEL;
    }

    public RequestValues(HashMap<Integer, Set<String>> filterMap, int sortType, String offerId) {
      this.mFilterMap = filterMap;
      this.mSortType = sortType;
      this.mOfferId = offerId;
      this.mFilterFlow = OFFER_LEVEL;
    }

    public RequestValues(int sortType, String searchQuery,
        HashMap<Integer, Set<String>> filterMap) {
      this.mFilterMap = filterMap;
      this.mSortType = sortType;
      this.mSearchQuery = searchQuery;
      this.mFilterFlow = SEARCH_LEVEL;
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
