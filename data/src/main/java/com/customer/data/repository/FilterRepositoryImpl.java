package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.FilteredProductsListMapper;
import com.customer.domain.interactor.FilterUseCase;
import com.customer.domain.repository.FilterRepository;
import com.customer.remote.http.model.request.ApplyFilterRequest;
import com.customer.remote.http.model.response.productListing.ProductListingDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

public class FilterRepositoryImpl extends BaseRepository implements FilterRepository {

  private DataSource mDataSource;
  private FilteredProductsListMapper mMapper = new FilteredProductsListMapper();

  @Inject
  public FilterRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<FilterUseCase.ResponseValues> categoryFilter(String catName,
      HashMap<Integer, Set<String>> filterMap, int sortType) {
    return responseMapper(mDataSource.api().pythonApiHandler().applyFilter(getHeader(),
        new ApplyFilterRequest(filterMap, catName, sortType)));
  }

  @Override
  public Single<FilterUseCase.ResponseValues> subCategoryFilter(
      HashMap<Integer, Set<String>> filterMap, String catName, String subCatName, int sortType) {
    return responseMapper(mDataSource.api().pythonApiHandler().applyFilter(getHeader(),
        new ApplyFilterRequest(catName, subCatName, filterMap, sortType)));
  }

  @Override
  public Single<FilterUseCase.ResponseValues> subSubCategoryFilter(
      HashMap<Integer, Set<String>> filterMap, String catName, String subCatName,
      String subSubCatName, int sortType) {
    return responseMapper(mDataSource.api().pythonApiHandler().applyFilter(getHeader(),
        new ApplyFilterRequest(catName, subCatName, subSubCatName, filterMap, sortType)));
  }

  @Override
  public Single<FilterUseCase.ResponseValues> brandFilter(HashMap<Integer, Set<String>> filterMap,
      String brandName, int sortType) {
    return responseMapper(mDataSource.api().pythonApiHandler().applyFilter(getHeader(),
        new ApplyFilterRequest(0, brandName, filterMap, sortType)));
  }

  @Override
  public Single<FilterUseCase.ResponseValues> searchFilter(HashMap<Integer, Set<String>> filterMap,
      String searchQuery, int sortType) {
    return responseMapper(mDataSource.api().pythonApiHandler().applyFilter(getHeader(),
        new ApplyFilterRequest(searchQuery, filterMap, sortType)));
  }

  @Override
  public Single<FilterUseCase.ResponseValues> offerFilter(HashMap<Integer, Set<String>> filterMap,
      String offerId, int sortType) {
    return responseMapper(mDataSource.api().pythonApiHandler().applyFilter(getHeader(),
        new ApplyFilterRequest(offerId, 0, filterMap, sortType)));
  }

  private Single<FilterUseCase.ResponseValues> responseMapper(
      Single<ProductListingDetails> productListingDetailsSingle) {
    return productListingDetailsSingle.flatMap(
        new Function<ProductListingDetails, SingleSource<? extends FilterUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends FilterUseCase.ResponseValues> apply(
              ProductListingDetails productListingDetails) throws Exception {
            return Single.just(
                new FilterUseCase.ResponseValues(mMapper.mapper(productListingDetails)));
          }
        });
  }
}
