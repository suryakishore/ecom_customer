package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.PlaceOrderUseCase;
import com.customer.domain.repository.PlaceOrderRepository;
import com.customer.remote.http.model.request.PlaceOrderRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class PlaceOrderRepositoryImpl extends BaseRepository implements PlaceOrderRepository {
  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public PlaceOrderRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<PlaceOrderUseCase.ResponseValues> placeOrder(String cartId, String addressId,
      String billingAddressId,
      int paymentType, String cardId, boolean payByWallet, String coupon, String couponId,
      double discount,
      double latitude, double longitude, String ipAddress, String extraNote, int storeType,
      int orderType) {
    return mDataSource.api().nodeApiHandler().placeOrder(getHeader(), new PlaceOrderRequest(
        cartId, addressId, billingAddressId, paymentType, cardId, payByWallet, coupon, couponId,
        discount,
        latitude, longitude,
        ipAddress, extraNote, getUserId(), storeType, orderType)).flatMap(
        new Function<CommonModel, SingleSource<? extends PlaceOrderUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends PlaceOrderUseCase.ResponseValues> apply(CommonModel model)
              throws Exception {
            mDatabaseManager.cart().clearCart();
            return Single.just(new PlaceOrderUseCase.ResponseValues(mMapper.mapper(model)));
          }
        });
  }
}
