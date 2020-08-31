package com.customer.data.repository.handler;

import com.customer.data.DataSource;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.BaseRepository;
import com.customer.data.repository.TrackingObservable;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.repository.observable.DefaultAddressObservable;
import com.customer.data.repository.observable.HistoryDataObservable;
import com.customer.data.repository.observable.NotificationCountObservable;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.data.repository.observable.WalletAmtObservable;
import com.customer.data.repository.observable.WishListObservable;
import com.customer.domain.model.UserData;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.getcart.TrackingData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.repository.UserInfoRepo;
import com.data.cache.DatabaseManager;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;

public class UserInfoRepoImpl extends BaseRepository implements UserInfoRepo {
  private PreferenceManager preference;
  private DatabaseManager mDatabaseManager;

  @Inject
  public UserInfoRepoImpl(DataSource dataSource) {
    super(dataSource);
    UserDataObservable.getInstance();
    CartDataObservable.getInstance();
    HistoryDataObservable.getInstance();
    DefaultAddressObservable.getInstance();
    NotificationCountObservable.getInstance();
    preference = dataSource.preference();
    mDatabaseManager = dataSource.db();
  }

  @Override
  public boolean isUserLoggedIn() {
    return preference.getIsLoggedIn();
  }

  @Override
  public String getGuestToken() {
    return preference.getGuestToken();
  }

  @Override
  public String token() {
    return preference.getToken(preference.getUserId());
  }

  @Override
  public String getWalletAmt() {
    return preference.getWalletAmt();
  }

  @Override
  public String getLoggedInToken() {
    return preference.getToken(preference.getUserId());
  }

  @Override
  public ConnectableObservable<UserData> getUserDataObservable() {
    return UserDataObservable.getObservable();
  }

  @Override
  public void getUserData() {
    UserDataObservable.getInstance().postData(preference.getUserDetails());
  }

  @Override
  public String getUserId() {
    return preference.getUserId();
  }

  public void setStoreCategoryId(boolean isEcom) {
    preference.setStoreType(isEcom);
    if (isEcom) {
      preference.setStoreCatId(preference.getEcomStoreId());
    } else {
      preference.setStoreCatId(preference.getGrocerStoreId());
    }
  }

  public boolean getStoreType() {
    return preference.getStoretype();
  }

  @Override
  public String getUserName() {
    return preference.getUserDetails().getName();
  }

  @Override
  public String getUserReferal() {
    return preference.getReferalCode();
  }

  @Override
  public String getUserEmail() {
    return preference.getUserDetails().getMail();
  }

  @Override
  public int getNotificationCount() {
    return preference.getNotificationCount();
  }

  @Override
  public void setNotificationCount(int notificationCount) {
    preference.setNotificationCount(notificationCount);
  }

  @Override
  public void setLanguage(String language) {
    preference.setLanguage(language);
  }

  @Override
  public String getLanguageCode() {
    return preference.getLanguage();
  }

  @Override
  public String getMqttTopic() {
    return preference.getMqttTopic();
  }

  @Override
  public void setMqttTopic(String mqttTopic) {
    preference.setMqttTopic(mqttTopic);
  }

  @Override
  public void sessionTime(long sessionTime) {
    preference.sessionTime(sessionTime);
  }

  @Override
  public long getSessionTime() {
    return preference.getSessionTime();
  }

  @Override
  public Observable<Boolean> getGenerateTokenObservable() {
    return preference.getGenerateTokenData();
  }

  @Override
  public Observable<Boolean> getLogoutObservable() {
    return preference.getLogoutData();
  }

  @Override
  public void clearData() {
    preference.setToken(preference.getUserId(), "");
    preference.setUserDetails(null);
    preference.setUserId("");
    preference.setWalletAmt("");
    preference.setNotificationCount(0);
    preference.setIsLoggedIn(false);
    UserDataObservable.getInstance().postData(new UserData());
    CartDataObservable.getInstance().postData(new CartData());
    new Thread(new Runnable() {
      @Override
      public void run() {
        mDatabaseManager.cart().clearCart();
        mDatabaseManager.address().deleteAllAddresses();
      }
    }).start();
  }

  @Override
  public String getIpAddress() {
    return preference.getIpAddress();
  }

  @Override
  public void setIpAddress(String ipAddress) {
    preference.setIpAddress(ipAddress);
  }

  @Override
  public void setLatLong(double lat, double longitude) {
    preference.setLatLong(lat, longitude);
  }

  @Override
  public Observable<ProductsData> getWishListObservable() {
    return WishListObservable.getInstance();
  }

  @Override
  public Observable<Boolean> getWalletAmtObservable() {
    return WalletAmtObservable.getInstance();
  }

  @Override
  public Observable<Integer> getNotificationCountObservable() {
    return NotificationCountObservable.getInstance();
  }

  @Override
  public Observable<TrackingData> getTrackingObservable() {
    return TrackingObservable.getInstance();
  }

  @Override
  public ConnectableObservable<AddressListItemData> getDefaultAddressObservable() {
    return DefaultAddressObservable.getObservable();
  }

  @Override
  public ConnectableObservable<CartData> getCartDataObservable() {
    return CartDataObservable.getObservable();
  }

  @Override
  public ConnectableObservable<OrderHistProductData> getHistoryDataObservable() {
    return HistoryDataObservable.getObservable();
  }
}
