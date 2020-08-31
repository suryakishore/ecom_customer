package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.FilteredProductsListMapper;
import com.customer.domain.interactor.FilteredProductsUseCase;
import com.customer.domain.interactor.FilteredProductsUseCase.ResponseValues;
import com.customer.domain.repository.FilteredProductsRepository;
import com.customer.remote.http.model.request.CategoryQueryParameterRequest;
import com.customer.remote.http.model.response.productListing.ProductListingDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

public class FilteredProductsListRepositoryImpl extends BaseRepository
    implements FilteredProductsRepository {
  private DataSource dataSource;
  private FilteredProductsListMapper mapper = new FilteredProductsListMapper();

  @Inject
  public FilteredProductsListRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ResponseValues> getCategoryProductList(String catName, String subCatName,
      String subSubCatName, int sortType, String page) {
    return dataSource.api().pythonApiHandler().getProductList(getHeader(),
        new CategoryQueryParameterRequest(catName, subCatName, subSubCatName, sortType,
            page)
    ).flatMap(new Function<ProductListingDetails,
        SingleSource<? extends ResponseValues>>() {
      @Override
      public SingleSource<? extends ResponseValues> apply(ProductListingDetails details)
          throws Exception {
        return Single.just(
            new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
      }
    });
  }

  @Override
  public Single<ResponseValues> getCategoryProductList(String catName, String subCatName,
      String subSubCatName, HashMap<Integer, Set<String>> filterMap, int sortType,
      String page) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(catName, subCatName, subSubCatName, filterMap,
            sortType,
            page)).flatMap(
        new Function<ProductListingDetails,
            SingleSource<? extends ResponseValues>>() {
                @Override
          public SingleSource<? extends ResponseValues> apply(
                    ProductListingDetails details)
              throws Exception {
                  return Single.just(
                new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getSearchProductList(String searchQuery, int sortType,
      String page) {
    return dataSource.api().pythonApiHandler().getProductList(getHeader(),
        new CategoryQueryParameterRequest(searchQuery, sortType, page)).flatMap(
        new Function<ProductListingDetails,
            SingleSource<? extends ResponseValues>>() {
                @Override
          public SingleSource<? extends ResponseValues> apply(
                    ProductListingDetails details)
              throws Exception {
                  return Single.just(
                      new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getFilteredProductList(HashMap<Integer, Set<String>> filterMap,
      int sortType, String page) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(filterMap, sortType, page)).flatMap(
        new Function<ProductListingDetails,
            SingleSource<? extends ResponseValues>>() {
                @Override
          public SingleSource<? extends ResponseValues> apply(
                    ProductListingDetails details)
              throws Exception {
                  return Single.just(
                new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getFilteredProductList(String catName, String subCatName,
      HashMap<Integer, Set<String>> filterMap, int sortType, String page) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(catName, subCatName, filterMap, sortType,
            page)).flatMap(
        new Function<ProductListingDetails,
            SingleSource<? extends ResponseValues>>() {
                @Override
          public SingleSource<? extends ResponseValues> apply(
                    ProductListingDetails details)
              throws Exception {
                  return Single.just(
                new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getFilteredProductList(HashMap<Integer, Set<String>> filterMap,
      String searchQuery, int sortType, String page) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(searchQuery, filterMap, sortType, page)).flatMap(
        new Function<ProductListingDetails,
            SingleSource<? extends ResponseValues>>() {
                @Override
          public SingleSource<? extends ResponseValues> apply(
                    ProductListingDetails details)
              throws Exception {
                  return Single.just(
                new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getFilteredProductList(String catName,
      HashMap<Integer, Set<String>> filterMap, int sortType, String page) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(filterMap, catName, sortType, page)).flatMap(
        new Function<ProductListingDetails,
            SingleSource<? extends ResponseValues>>() {
                @Override
          public SingleSource<? extends ResponseValues> apply(
                    ProductListingDetails details)
              throws Exception {
                  return Single.just(
                new FilteredProductsUseCase.ResponseValues(mapper.mapper(details)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getProductsUnderBrand(String brandName, int sortType) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(0, brandName, sortType)).flatMap(
        new Function<ProductListingDetails, SingleSource<? extends ResponseValues>>() {
            @Override
          public SingleSource<? extends ResponseValues> apply(
                ProductListingDetails productListingDetails) throws Exception {
              return Single.just(
                new FilteredProductsUseCase.ResponseValues(
                    mapper.mapper(productListingDetails)));
            }
          });
  }

  @Override
  public Single<ResponseValues> getProductsUnderBrand(int type, String name, String inText) {
    return dataSource.api().pythonApiHandler().getFilteredProductList(getHeader(),
        new CategoryQueryParameterRequest(type, name, inText)).flatMap(
        new Function<ProductListingDetails, SingleSource<? extends ResponseValues>>() {
            @Override
          public SingleSource<? extends ResponseValues> apply(
                ProductListingDetails productListingDetails) throws Exception {
              return Single.just(
                new FilteredProductsUseCase.ResponseValues(
                    mapper.mapper(productListingDetails)));
            }
          });
  }
}
