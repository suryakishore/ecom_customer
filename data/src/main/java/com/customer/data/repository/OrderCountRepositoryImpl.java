package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.OrderCountMapper;
import com.customer.data.mapper.SignInMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.OrderCountUseCase;
import com.customer.domain.repository.OrderCountRepository;
import com.customer.remote.http.model.response.ordercount.OrderCountListDetails;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class OrderCountRepositoryImpl extends BaseRepository implements
    OrderCountRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private OrderCountMapper mMapper = new OrderCountMapper();

  @Inject
  public OrderCountRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<OrderCountUseCase.ResponseValues> orderCount() {
    return mDataSource.api().nodeApiHandler().orderCount(getHeader()).flatMap(
        new Function<OrderCountListDetails, SingleSource<?
            extends OrderCountUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends OrderCountUseCase.ResponseValues> apply(
              OrderCountListDetails details) {
            return Single.just(
                new OrderCountUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
