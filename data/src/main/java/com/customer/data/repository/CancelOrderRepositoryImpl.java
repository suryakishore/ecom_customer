package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.repository.observable.HistoryDataObservable;
import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.repository.CancelOrderRepository;
import com.customer.remote.http.model.request.CancelOrderRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class CancelOrderRepositoryImpl extends BaseRepository implements CancelOrderRepository {

  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();

  @Inject
  public CancelOrderRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<CancelOrderUseCase.ResponseValues> cancelOrder(String type, final String orderId,
      String reason, String comment) {
    return mDataSource.api().nodeApiHandler().cancelOrder(getHeader(), new CancelOrderRequest(type,
        orderId, reason, comment)).flatMap(
        new Function<CommonModel, SingleSource<? extends CancelOrderUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends CancelOrderUseCase.ResponseValues> apply(CommonModel model)
              throws Exception {
              HistoryDataObservable.getInstance().postData(new OrderHistProductData(orderId));
              return Single.just(new CancelOrderUseCase.ResponseValues(mMapper.mapper(model)));
            }
          });
  }
}
