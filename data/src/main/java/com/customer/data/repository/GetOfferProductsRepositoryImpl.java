package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.HomeSubCategoryMapper;
import com.customer.domain.interactor.GetOfferProductsUseCase;
import com.customer.domain.repository.GetOfferProductsRepository;
import com.customer.remote.http.model.response.homeSubCategory.HomeCatDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetOfferProductsRepositoryImpl extends BaseRepository implements
    GetOfferProductsRepository {

  private DataSource dataSource;
  private HomeSubCategoryMapper mapper = new HomeSubCategoryMapper();

  @Inject
  public GetOfferProductsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<GetOfferProductsUseCase.ResponseValues> getOfferProducts(int skip, int size) {
    return dataSource.api().pythonApiHandler().getOfferProducts(getHeader(), skip, size).flatMap(
        new Function<HomeCatDetails, SingleSource<?
            extends GetOfferProductsUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetOfferProductsUseCase.ResponseValues> apply(
              HomeCatDetails homeCatDetails) throws Exception {
            return Single.just(
                new GetOfferProductsUseCase.ResponseValues(mapper.mapper(homeCatDetails)));
          }
        });
  }
}
