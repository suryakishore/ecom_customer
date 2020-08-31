package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.repository.observable.HistoryDataObservable;
import com.customer.domain.interactor.ReorderUseCase;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.repository.ReorderRepository;
import com.customer.remote.http.model.request.ReorderRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ReorderRepositoryImpl extends BaseRepository implements ReorderRepository {
  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public ReorderRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<ReorderUseCase.ResponseValues> makeReorder(String type, final String orderId) {
    return mDataSource.api().nodeApiHandler().makeReorder(getHeader(),
        new ReorderRequest(type, orderId)).flatMap(
        new Function<CommonModel, SingleSource<? extends ReorderUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends ReorderUseCase.ResponseValues> apply(CommonModel model)
              throws Exception {
              mDatabaseManager.cart().clearCart();
              CartDataObservable.getInstance().postData(new CartData(5, "", 0));
              HistoryDataObservable.getInstance().postData(new OrderHistProductData(orderId));
              return Single.just(new ReorderUseCase.ResponseValues(mMapper.mapper(model)));
            }
          });
  }
}
