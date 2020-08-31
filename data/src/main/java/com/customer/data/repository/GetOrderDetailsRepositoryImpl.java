package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.OrderDetailsMapper;
import com.customer.domain.interactor.GetOrderDetailsUseCase;
import com.customer.domain.repository.GetOrderDetailsRepository;
import com.customer.remote.http.model.response.orderdetails.MasterOrderDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetOrderDetailsRepositoryImpl extends BaseRepository implements
    GetOrderDetailsRepository {
  private DataSource mDataSource;
  private OrderDetailsMapper mMapper = new OrderDetailsMapper();

  @Inject
  public GetOrderDetailsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<GetOrderDetailsUseCase.ResponseValues> getOrderDetails(String type,
      String orderId) {
    return mDataSource.api().nodeApiHandler().getOrderDetails(getHeader(), type, orderId).flatMap(
        new Function<MasterOrderDetails, SingleSource<?
            extends GetOrderDetailsUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetOrderDetailsUseCase.ResponseValues> apply(
              MasterOrderDetails masterOrderDetails) throws Exception {
            return Single.just(
                new GetOrderDetailsUseCase.ResponseValues(mMapper.mapper(masterOrderDetails)));
          }
        });
  }
}
