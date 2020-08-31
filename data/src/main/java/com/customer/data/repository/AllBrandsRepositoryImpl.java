package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.AllBrandsMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.repository.AllBrandsRepository;
import com.customer.remote.http.model.response.brands.AllBrandsListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class AllBrandsRepositoryImpl extends BaseRepository implements
    AllBrandsRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private AllBrandsMapper mMapper = new AllBrandsMapper();

  @Inject
  public AllBrandsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<AllBrandsUseCase.ResponseValues> allBrands(String from, String to) {
    return mDataSource.api().pythonApiHandler().allBrandsApi(getHeader(), from,
        to).flatMap(
        new Function<AllBrandsListDetails, SingleSource<?
            extends AllBrandsUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends AllBrandsUseCase.ResponseValues> apply(
                AllBrandsListDetails details) {
              return Single.just(
                new AllBrandsUseCase.ResponseValues(mMapper.mapper(details)));
            }
          });
  }
}
