package com.customer.domain.interactor.handler;

import com.customer.domain.model.UserData;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.getcart.TrackingData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public interface UserInfoHandler {
  boolean isUserLoggedIn();

  String getGuestToken();

  String getToken();

  String walletAmt();

  String getLoggedInToken();

  Observable<UserData> getUserDataObservable();

  void getUserData();

  String getUserName();

  String getUserReferral();

  String getUserEmail();

  String userId();

  void setStoreCategoryId(boolean isEcom);

  boolean getStoreType();

  Observable<ProductsData> getWishListObservable();

  Observable<Boolean> getWalletAmtObservable();

  Observable<Integer> getNotificationCountObservable();

  Observable<TrackingData> getTrackingObservable();

  ConnectableObservable<AddressListItemData> getDefaultAddressObservable();

  ConnectableObservable<CartData> getCartDataObservable();

  ConnectableObservable<OrderHistProductData> getHistoryDataObservable();

  void setLatLong(double lat, double longitude);

  int getNotificationCount();

  void setNotificationCount(int notificationCount);

  String getLanguage();

  void setLanguage(String language);

  String getMqttTopic();

  void setMqttTopic(String mqttTopic);

  long getSessionTime();

  void setSessionTime(long sessionTime);

  Observable<Boolean> getGenerateTokenObservable();

  Observable<Boolean> getLogoutObservable();

  void clearData();

  String getIpAddress();

  void setIpAddress(String ipAddress);
}
