package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.GetRatableProductMapper;
import com.customer.domain.interactor.GetRatableAttributesUseCase;
import com.customer.domain.repository.GetRatableAttributesRepository;
import com.customer.remote.http.model.response.getratable.RatableDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetRatableAttributesRepositoryImpl extends BaseRepository implements
    GetRatableAttributesRepository {

  private DataSource dataSource;
  private GetRatableProductMapper mMapper = new GetRatableProductMapper();

  @Inject
  public GetRatableAttributesRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<GetRatableAttributesUseCase.ResponseValues> getRatableAttributes(String productId) {
    return dataSource.api().pythonApiHandler().getRatableAttributes(getHeader(), productId).flatMap(
        new Function<RatableDetails, SingleSource<?
            extends GetRatableAttributesUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetRatableAttributesUseCase.ResponseValues> apply(
              RatableDetails details) throws Exception {
            return Single.just(
                new GetRatableAttributesUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
