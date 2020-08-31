package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.ProductCategoryMapper;
import com.customer.domain.interactor.ProductCategoryUseCase;
import com.customer.domain.interactor.ProductCategoryUseCase.ResponseValues;
import com.customer.domain.repository.ProductCategoryRepository;
import com.customer.remote.http.model.request.CategoryByIdRequest;
import com.customer.remote.http.model.response.productCategory.CategoryDetail;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ProductCategoryRepositoryImpl extends BaseRepository implements
    ProductCategoryRepository {
  private DataSource dataSource;
  private ProductCategoryMapper mapper = new ProductCategoryMapper();

  @Inject
  public ProductCategoryRepositoryImpl(DataSource dataSource, DataSource dataSource1) {
    super(dataSource);
    this.dataSource = dataSource1;
  }

  @Override
  public Single<ResponseValues> getProductCategory(String catId, String subCatId) {
    return dataSource.api()
        .pythonApiHandler().getProductCategory(getHeader(),
            new CategoryByIdRequest(catId, subCatId)).flatMap(
            new Function<CategoryDetail, SingleSource<? extends ResponseValues>>() {
                @Override
              public SingleSource<? extends ResponseValues> apply(CategoryDetail detailPojo)
                  throws Exception {
                  return Single.just(
                    new ProductCategoryUseCase.ResponseValues(mapper.mapper(detailPojo)));
                }
              });
  }

  @Override
  public Single<ResponseValues> getProductCategory(int from, int to) {
    return dataSource.api()
        .pythonApiHandler().getProductCategory(getHeader(),
            new CategoryByIdRequest(from, to)).flatMap(
            new Function<CategoryDetail, SingleSource<? extends ResponseValues>>() {
                @Override
              public SingleSource<? extends ResponseValues> apply(CategoryDetail detailPojo)
                  throws Exception {
                  return Single.just(
                      new ProductCategoryUseCase.ResponseValues(mapper.mapper(detailPojo)));
                }
              });
  }
}
