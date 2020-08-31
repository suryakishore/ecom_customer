package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.CancelReasonsMapper;
import com.customer.domain.interactor.GetCancelReasonsUseCase;
import com.customer.domain.repository.GetReasonsRepository;
import com.customer.remote.http.model.response.getReasons.GetReasonsItemDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetReasonsRepositoryImpl extends BaseRepository implements
    GetReasonsRepository {
  private DataSource dataSource;
  private CancelReasonsMapper mMapper = new CancelReasonsMapper();

  @Inject
  public GetReasonsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<GetCancelReasonsUseCase.ResponseValues> getReasons() {
    return dataSource.api().nodeApiHandler().getReasons(getHeader()).flatMap(
        new Function<GetReasonsItemDetails, SingleSource<?
            extends GetCancelReasonsUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetCancelReasonsUseCase.ResponseValues> apply(
              GetReasonsItemDetails details) throws Exception {
            return Single.just(
                new GetCancelReasonsUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
