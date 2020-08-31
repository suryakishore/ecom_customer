package com.customer.data.preference;

import com.customer.data.utils.LatLong;
import com.customer.domain.model.UserData;
import io.reactivex.Observable;

public interface PreferenceManager {
  Boolean isLogged = false;
  int screenState = 0;

  String getGuestToken();

  void setGuestToken(String guestAuthToken);

  String getUserId();

  void setUserId(String userId);

  String getToken(String userId);

  void setToken(String userId, String guestAuthToken);

  String getOtpToken();

  void setOtpToken(String otpToken);

  String getOtpId();

  void setOtpId(String guestAuthToken);

  String getLanguage();

  void setLanguage(String guestAuthToken);

  boolean getIsLoggedIn();

  void setIsLoggedIn(boolean guestAuthToken);

  String getCurrencyCode();

  void setCurrencyCode(String currencyCode);

  String getCurrencySymbol();

  void setCurrencySymbol(String currencySymbol);

  int getPlatform();

  String getRefreshToken();

  void setRefreshToken(String refreshToken);

  String getResetPasswordToken();

  void setResetPasswordToken(String token);

  String getUserName();

  void setUserName(String name);

  String getUserMail();

  void setUserMail(String mail);

  UserData getUserDetails();

  void setUserDetails(UserData userData);

  String getStoreCatId();

  void setStoreCatId(String storeCatId);

  String getGrocerStoreId();

  void setGrocerStoreId(String storeCatId);

  String getEcomStoreId();

  void setEcomStoreId(String storeCatId);

  boolean getStoretype();

  void setStoreType(boolean storeType);

  boolean isFirstTimeAsking(String permission);

  void firstTimeAsking(String permission, boolean isFirstTime);

  String getIpAddress();

  void setIpAddress(String ipAddress);

  void setLatLong(double lat, double longitude);

  LatLong getLatLong();

  String getCountry();

  String getState();

  String getCity();

  String getReferalCode();

  void setReferalCode(String referralCode);

  String getWalletAmt();

  void setWalletAmt(String walletAmt);

  int getNotificationCount();

  void setNotificationCount(int notificationCount);

  String getMqttTopic();

  void setMqttTopic(String mqttTopic);

  void sessionTime(long sessionTime);

  long getSessionTime();


  Observable<Boolean> getGenerateTokenData();

  Observable<Boolean> getLogoutData();
}
