package com.customer.domain.repository;

import com.customer.domain.interactor.PlaceOrderUseCase;
import io.reactivex.Single;

public interface PlaceOrderRepository {
  Single<PlaceOrderUseCase.ResponseValues> placeOrder(String cartId, String addressId,
      String billingAddressId,
      int paymentType, String cardId, boolean payByWallet, String coupon, String couponId,
      double discount,
      double latitude, double longitude, String ipAddress, String extraNote, int storeType,
      int orderType);
}
