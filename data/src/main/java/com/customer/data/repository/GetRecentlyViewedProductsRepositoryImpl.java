package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.RecentlyViewedProductListMapper;
import com.customer.domain.interactor.GetRecentlyViewedProductsUseCase;
import com.customer.domain.repository.GetRecentlyViewedProductsRepository;
import com.customer.remote.http.model.response.recentlyviewed.RecentlyViewedDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetRecentlyViewedProductsRepositoryImpl extends BaseRepository implements
    GetRecentlyViewedProductsRepository {
  private RecentlyViewedProductListMapper mViewedProductListMapper =
      new RecentlyViewedProductListMapper();
  private DataSource mDataSource;

  @Inject
  public GetRecentlyViewedProductsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
  }

  @Override
  public Single<GetRecentlyViewedProductsUseCase.ResponseValues> getRecentlyViewedProducts() {
    return mDataSource.api().pythonApiHandler().getAllRecentlyViewedProducts(getHeader()).flatMap(
        new Function<RecentlyViewedDetails, SingleSource<?
            extends GetRecentlyViewedProductsUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetRecentlyViewedProductsUseCase.ResponseValues> apply(
              RecentlyViewedDetails recentlyViewedDetails) throws Exception {
            return Single.just(new GetRecentlyViewedProductsUseCase.ResponseValues(
                mViewedProductListMapper.mapper(recentlyViewedDetails)));
          }
        });
  }
}
