package com.customer.domain.interactor.handler;

import com.customer.domain.model.UserData;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.getcart.TrackingData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.repository.UserInfoRepo;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;

public class UserInfoHandlerImpl implements UserInfoHandler {
  private UserInfoRepo mUserInfoRepo;

  @Inject
  public UserInfoHandlerImpl(UserInfoRepo userInfoRepo) {
    this.mUserInfoRepo = userInfoRepo;
  }

  @Override
  public boolean isUserLoggedIn() {
    return mUserInfoRepo.isUserLoggedIn();
  }

  @Override
  public String getGuestToken() {
    return mUserInfoRepo.getGuestToken();
  }

  @Override
  public String getToken() {
    return mUserInfoRepo.token();
  }

  @Override
  public String walletAmt() {
    return mUserInfoRepo.getWalletAmt();
  }

  @Override
  public String getLoggedInToken() {
    return mUserInfoRepo.getLoggedInToken();
  }

  @Override
  public ConnectableObservable<UserData> getUserDataObservable() {
    return mUserInfoRepo.getUserDataObservable();
  }

  public void getUserData() {
    mUserInfoRepo.getUserData();
  }

  @Override
  public String getUserName() {
    return mUserInfoRepo.getUserName();
  }

  @Override
  public String getUserReferral() {
    return mUserInfoRepo.getUserReferal();
  }

  @Override
  public String getUserEmail() {
    return mUserInfoRepo.getUserEmail();
  }

  @Override
  public String userId() {
    return mUserInfoRepo.getUserId();
  }

  public void setStoreCategoryId(boolean isEcom) {
    mUserInfoRepo.setStoreCategoryId(isEcom);
  }

  public boolean getStoreType() {
    return mUserInfoRepo.getStoreType();
  }

  @Override
  public Observable<ProductsData> getWishListObservable() {
    return mUserInfoRepo.getWishListObservable();
  }

  @Override
  public Observable<Boolean> getWalletAmtObservable() {
    return mUserInfoRepo.getWalletAmtObservable();
  }

  @Override
  public Observable<Integer> getNotificationCountObservable() {
    return mUserInfoRepo.getNotificationCountObservable();
  }

  @Override
  public Observable<TrackingData> getTrackingObservable() {
    return mUserInfoRepo.getTrackingObservable();
  }

  @Override
  public ConnectableObservable<AddressListItemData> getDefaultAddressObservable() {
    return mUserInfoRepo.getDefaultAddressObservable();
  }

  @Override
  public ConnectableObservable<CartData> getCartDataObservable() {
    return mUserInfoRepo.getCartDataObservable();
  }

  @Override
  public ConnectableObservable<OrderHistProductData> getHistoryDataObservable() {
    return mUserInfoRepo.getHistoryDataObservable();
  }

  @Override
  public void setLatLong(double lat, double longitude) {
    mUserInfoRepo.setLatLong(lat, longitude);
  }

  @Override
  public int getNotificationCount() {
    return mUserInfoRepo.getNotificationCount();
  }

  @Override
  public void setNotificationCount(int notificationCount) {
    mUserInfoRepo.setNotificationCount(notificationCount);
  }

  @Override
  public String getMqttTopic() {
    return mUserInfoRepo.getMqttTopic();
  }

  @Override
  public void setMqttTopic(String mqttTopic) {
    mUserInfoRepo.setMqttTopic(mqttTopic);
  }

  @Override
  public long getSessionTime() {
    return mUserInfoRepo.getSessionTime();
  }

  @Override
  public void setSessionTime(long sessionTime) {
    mUserInfoRepo.sessionTime(sessionTime);
  }

  @Override
  public Observable<Boolean> getGenerateTokenObservable() {
    return mUserInfoRepo.getGenerateTokenObservable();
  }

  @Override
  public Observable<Boolean> getLogoutObservable() {
    return mUserInfoRepo.getLogoutObservable();
  }

  @Override
  public void clearData() {
    mUserInfoRepo.clearData();
  }

  @Override
  public String getIpAddress() {
    return mUserInfoRepo.getIpAddress();
  }

  @Override
  public void setIpAddress(String ipAddress) {
    mUserInfoRepo.setIpAddress(ipAddress);
  }

  @Override
  public String getLanguage() {
    return mUserInfoRepo.getLanguageCode();
  }

  @Override
  public void setLanguage(String language) {
    mUserInfoRepo.setLanguage(language);
  }
}
