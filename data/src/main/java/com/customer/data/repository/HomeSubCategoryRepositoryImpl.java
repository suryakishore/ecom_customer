package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.HomePageMapper;
import com.customer.domain.interactor.HomeSubCatUseCase;
import com.customer.domain.repository.HomeSubCategoryRepository;
import com.customer.remote.http.model.request.HomeSubCategoryRequest;
import com.customer.remote.http.model.response.homeSubCategory.HomeCatDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class HomeSubCategoryRepositoryImpl extends BaseRepository implements
    HomeSubCategoryRepository {
  private DataSource dataSource;
  private HomePageMapper mapper = new HomePageMapper();

  @Inject
  public HomeSubCategoryRepositoryImpl(DataSource dataSource, DataSource dataSource1) {
    super(dataSource);
    this.dataSource = dataSource1;
  }

  @Override
  public Single<HomeSubCatUseCase.ResponseValues> getHomeSubCatProducts(int size, int limit) {
    return dataSource.api()
        .pythonApiHandler().getHomeSubCat(getHeader(),
            new HomeSubCategoryRequest(size, limit)).flatMap(
            new Function<HomeCatDetails, SingleSource<?
                extends HomeSubCatUseCase.ResponseValues>>() {
                @Override
                public SingleSource<? extends HomeSubCatUseCase.ResponseValues> apply(
                    HomeCatDetails homeCatDetails) throws Exception {
                  return Single.just(new HomeSubCatUseCase.ResponseValues(
                      mapper.secondApiMapper(homeCatDetails)));
                }
              });
  }
}
