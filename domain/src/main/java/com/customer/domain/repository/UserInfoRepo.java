package com.customer.domain.repository;

import com.customer.domain.model.UserData;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.getcart.TrackingData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public interface UserInfoRepo {
  boolean isUserLoggedIn();

  String getGuestToken();

  String token();

  String getWalletAmt();

  String getLoggedInToken();

  ConnectableObservable<UserData> getUserDataObservable();

  void getUserData();

  String getUserId();

  void setStoreCategoryId(boolean isEcom);

  boolean getStoreType();

  Observable<ProductsData> getWishListObservable();

  Observable<Boolean> getWalletAmtObservable();

  Observable<Integer> getNotificationCountObservable();

  Observable<TrackingData> getTrackingObservable();

  ConnectableObservable<AddressListItemData> getDefaultAddressObservable();

  ConnectableObservable<CartData> getCartDataObservable();

  ConnectableObservable<OrderHistProductData> getHistoryDataObservable();

  String getUserName();

  String getUserReferal();

  void setIpAddress(String ipAddress);

  void setLatLong(double lat, double longitude);

  String getUserEmail();

  int getNotificationCount();

  void setNotificationCount(int notificationCount);

  void setLanguage(String language);

  String getLanguageCode();

  String getMqttTopic();

  void setMqttTopic(String mqttTopic);

  void sessionTime(long sessionTime);

  long getSessionTime();

  Observable<Boolean> getGenerateTokenObservable();

  Observable<Boolean> getLogoutObservable();

  void clearData();
  String getIpAddress();

}
