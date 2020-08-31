package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.ADD;
import static com.customer.data.utils.DataConstants.DELETE;
import static com.customer.data.utils.DataConstants.MODIFY;
import static com.customer.data.utils.DataConstants.ZERO;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.mapper.UpdateCartRequestMapper;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.utils.DataConstants;
import com.customer.domain.interactor.UpdateCartUseCase;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.repository.UpdateCartRepository;
import com.customer.remote.http.model.request.UpdateCartRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.customer.remote.http.model.response.pdp.PdpOfferDetails;
import com.data.cache.DatabaseManager;
import com.data.cache.entities.UserCart;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class UpdateCartRepositoryImpl extends BaseRepository implements UpdateCartRepository {
  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();
  private UpdateCartRequestMapper mCartRequestMapper = new UpdateCartRequestMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public UpdateCartRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<UpdateCartUseCase.ResponseValues> updateCart(int userType, String centralProductId,
       final String productId, String unitId, final String storeId, int storeTypeId, final String productName,
       String estimatedProductPrice, String extraNotes, final int newQuantity, int oldQuantity,
       final int action, int cartType, String userIp, double latitude, double longitude) {
    return mDataSource.api().nodeApiHandler().updateCart(getHeader(),
            new UpdateCartRequest(userType, centralProductId, productId, unitId, storeId,
                    getHeader().getStoreCatId(), storeTypeId, productName, ZERO, extraNotes,
                    newQuantity, oldQuantity, action, cartType, userIp, latitude, longitude)).flatMap(
            new Function<CommonModel, SingleSource<? extends UpdateCartUseCase.ResponseValues>>() {
              @Override
              public SingleSource<? extends UpdateCartUseCase.ResponseValues> apply(CommonModel model)
                      throws Exception {
                Thread t = new Thread() {
                  public void run() {
                    if (action == ADD || action == MODIFY) {
                      UserCart cart = new UserCart(productId, storeId, productName, newQuantity);
                      mDatabaseManager.cart().insertItemToCart(cart);
                    } else if (action == DELETE) {
                      mDatabaseManager.cart().deleteCartItemById(productId);
                    }
                    CartDataObservable.getInstance().postData(
                            new CartData(action, productId, newQuantity));
                  }
                };
                t.start();
                return Single.just(new UpdateCartUseCase.ResponseValues(mMapper.mapper(model)));
              }
            });
  }

  @Override
  public Single<UpdateCartUseCase.ResponseValues> updateCart(int userType, String centralProductId,
                                                             final String productId, String unitId, final String storeId, int storeTypeId,
                                                             final String productName,
                                                             String estimatedProductPrice, String extraNotes, final int newQuantity, int oldQuantity,
                                                             final int action, int cartType, String userIp, double latitude, double longitude, PdpOfferData offer) {
    return mDataSource.api().nodeApiHandler().updateCart(getHeader(),
        new UpdateCartRequest(userType,
            centralProductId, productId, unitId, storeId, getHeader().getStoreCatId(), storeTypeId,
            productName, ZERO,
            extraNotes, newQuantity, oldQuantity, action, cartType, userIp, latitude,
            longitude, mCartRequestMapper.getOffer(offer))).flatMap(
        new Function<CommonModel, SingleSource<? extends UpdateCartUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends UpdateCartUseCase.ResponseValues> apply(CommonModel model)
              throws Exception {
            Thread t = new Thread() {
              public void run() {
                if (action == ADD || action == MODIFY) {
                  UserCart cart = new UserCart(productId, storeId, productName, newQuantity);
                  mDatabaseManager.cart().insertItemToCart(cart);
                } else if (action == DELETE) {
                  mDatabaseManager.cart().deleteCartItemById(productId);
                }
                CartDataObservable.getInstance().postData(
                    new CartData(action, productId, newQuantity));
              }
            };
            t.start();
            return Single.just(new UpdateCartUseCase.ResponseValues(mMapper.mapper(model)));
          }
        });
  }
}
