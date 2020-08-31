package com.customer.data.repository;

import android.util.Log;
import com.customer.data.DataSource;
import com.customer.data.mapper.OrderHistoryMapper;
import com.customer.domain.interactor.GetOrderHistoryUseCase;
import com.customer.domain.repository.GetOrderHistoryRepository;
import com.customer.remote.http.model.request.GetOrderHistoryRequest;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetOrderHistoryRepositoryImpl extends BaseRepository implements
    GetOrderHistoryRepository {
  private DataSource mDataSource;
  private OrderHistoryMapper mMapper = new OrderHistoryMapper();

  @Inject
  public GetOrderHistoryRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
  }

  @Override
  public Single<GetOrderHistoryUseCase.ResponseValues> getOrderHistory(int limit, int skip,
      int status, int orderType, int storeType, int orderBy, String search, String orderTime) {
    Log.d("exe", "orderTime" + orderTime);
    return mDataSource.api().nodeApiHandler().getOrderHistory(getHeader(),
        new GetOrderHistoryRequest(limit, skip, status, orderType, storeType, orderBy,
            search, orderTime)).flatMap(
        new Function<OrderHistoryDetails, SingleSource<?
            extends GetOrderHistoryUseCase.ResponseValues>>() {
                @Override
          public SingleSource<? extends GetOrderHistoryUseCase.ResponseValues> apply(
                    OrderHistoryDetails orderHistoryDetails) throws Exception {
                  return Single.just(
                new GetOrderHistoryUseCase.ResponseValues(mMapper.mapper(orderHistoryDetails)));
                }
              });
  }
}
