package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.TrackingMapper;
import com.customer.domain.interactor.TrackingOrderUseCase;
import com.customer.domain.repository.TrackingRepository;
import com.customer.remote.http.model.response.tracking.TrackingListDetails;
import com.customer.remote.http.model.response.tracking.TrackingListOrderStatusData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;
public class TrackingRepositoryImpl extends BaseRepository implements TrackingRepository {
  private DataSource mDataSource;
  private TrackingMapper mMapper = new TrackingMapper();
  @Inject
  public TrackingRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<TrackingOrderUseCase.ResponseValues> trackOrder(
      String productOrderId) {
    return mDataSource.api().nodeApiHandler().getDeliveryStatus(getHeader(),
        productOrderId).flatMap(
        new Function<TrackingListOrderStatusData, SingleSource<?
            extends TrackingOrderUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends TrackingOrderUseCase.ResponseValues> apply(
                TrackingListOrderStatusData details) {
              return Single.just(
                new TrackingOrderUseCase.ResponseValues(mMapper.mapper(details)));
            }
          });
  }
}
