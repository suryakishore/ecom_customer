package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.NotifyProductAvailabilityUseCase;
import com.customer.domain.repository.NotifyProductAvailabilityRepository;
import com.customer.remote.http.model.request.NotifyProductAvailabilityRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class NotifyProductAvailabilityRepositoryImpl extends BaseRepository implements
    NotifyProductAvailabilityRepository {

  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();

  @Inject
  public NotifyProductAvailabilityRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<NotifyProductAvailabilityUseCase.ResponseValues> notifyProductAvailabilityRepository(
      String childProductId, String email, String parentProductId) {
    return mDataSource.api().pythonApiHandler().notifyProductAvailability(getAccessToken(),
        new NotifyProductAvailabilityRequest(childProductId, email, parentProductId)).flatMap(
        new Function<CommonModel, SingleSource<?
            extends NotifyProductAvailabilityUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends NotifyProductAvailabilityUseCase.ResponseValues> apply(
                CommonModel model) throws Exception {
              return Single.just(
                  new NotifyProductAvailabilityUseCase.ResponseValues(mMapper.mapper(model)));
            }
          });
  }
}