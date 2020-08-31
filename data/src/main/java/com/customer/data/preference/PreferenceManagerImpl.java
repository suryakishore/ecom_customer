package com.customer.data.preference;

import static android.provider.Telephony.Carriers.PASSWORD;
import static com.customer.data.account.AccountGeneral.ACCOUNT_TYPE;
import static com.customer.data.account.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.customer.data.utils.DataConstants.LOWER_BOUND;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import com.customer.data.preference.SessionKeys.PreferenceTaskEntry;
import com.customer.data.utils.DataConstants;
import com.customer.data.utils.LatLong;
import com.customer.domain.model.UserData;
import com.customer.remote.http.GenerateTokenRepoImpl;
import com.customer.remote.http.LogoutRepoImpl;
import com.google.gson.Gson;
import io.reactivex.Observable;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PreferenceManagerImpl implements PreferenceManager {
  private Context context;
  private SharedPreferences sharedPreferences;
  private AccountManager accountManager;
  private SharedPreferences.Editor editor;
  private Gson gson;
  private GenerateTokenRepoImpl mGenerateTokenRepo;
  private LogoutRepoImpl mLogoutRepo;

  public PreferenceManagerImpl(Context context) throws GeneralSecurityException, IOException {
    int privateMode = 0;
    this.context = context;
    mGenerateTokenRepo = new GenerateTokenRepoImpl();
    mLogoutRepo = new LogoutRepoImpl();
    if (Build.VERSION.SDK_INT >= 23) {
      String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
      sharedPreferences =
          EncryptedSharedPreferences.create(
              DataConstants.SHARED_PREF_NAME,
              masterKeyAlias,
              context,
              EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
              EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    } else {
      sharedPreferences =
          context.getSharedPreferences(DataConstants.SHARED_PREF_NAME, privateMode);
    }
    gson = new Gson();
    editor = sharedPreferences.edit();
    editor.apply();
    accountManager = AccountManager.get(context);
  }

  @Override
  public String getUserId() {
    return sharedPreferences.getString(PreferenceTaskEntry.USER_ID, "");
  }

  @Override
  public void setUserId(String userId) {
    editor.putString(SessionKeys.PreferenceTaskEntry.USER_ID, userId);
    editor.commit();
  }

  @Override
  public String getGuestToken() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.GUEST_AUTH_TOKEN, "");
  }

  @Override
  public void setGuestToken(String guestAuthToken) {
    editor.putString(SessionKeys.PreferenceTaskEntry.GUEST_AUTH_TOKEN, guestAuthToken);
    editor.commit();
  }

  @Override
  public String getOtpToken() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.OTP_TOKEN, "");
  }

  @Override
  public void setOtpToken(String otpToken) {
    editor.putString(SessionKeys.PreferenceTaskEntry.OTP_TOKEN, otpToken);
    editor.commit();
  }

  @Override
  public String getToken(String userId) {
    Account[] account = accountManager.getAccountsByType(ACCOUNT_TYPE);
    List<Account> accounts = Arrays.asList(account);
    if (accounts.size() > 0) {
      for (int i = 0; i < accounts.size(); i++) {
        if (accounts.get(i).name.equals(userId)) {
          return accountManager.peekAuthToken(accounts.get(i), AUTHTOKEN_TYPE_FULL_ACCESS);
        }
      }
    }
    return null;
  }

  @Override
  public void setToken(String userId, String guestAuthToken) {
    Log.d("exe", "userId" + userId);
    final Account account = new Account(userId, ACCOUNT_TYPE);
    accountManager.addAccountExplicitly(account, PASSWORD, null);
    accountManager.setAuthToken(account, AUTHTOKEN_TYPE_FULL_ACCESS, guestAuthToken);
  }

  @Override
  public String getOtpId() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.OTP_ID, "");
  }

  @Override
  public void setOtpId(String guestAuthToken) {
    editor.putString(SessionKeys.PreferenceTaskEntry.OTP_ID, guestAuthToken);
    editor.commit();
  }

  @Override
  public String getLanguage() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.LANGUAGE, "en");
  }

  @Override
  public void setLanguage(String guestAuthToken) {
    editor.putString(SessionKeys.PreferenceTaskEntry.LANGUAGE, guestAuthToken);
    editor.commit();
  }

  @Override
  public boolean getIsLoggedIn() {
    return sharedPreferences.getBoolean(SessionKeys.PreferenceTaskEntry.IS_LOGGED_IN, false);
  }

  @Override
  public void setIsLoggedIn(boolean isLoggedIn) {
    editor.putBoolean(SessionKeys.PreferenceTaskEntry.IS_LOGGED_IN, isLoggedIn);
    editor.commit();
  }

  @Override
  public String getRefreshToken() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.REFRESH_TOKEN, "");
  }

  @Override
  public void setRefreshToken(String refreshToken) {
    editor.putString(SessionKeys.PreferenceTaskEntry.REFRESH_TOKEN, refreshToken);
    editor.commit();
  }

  @Override
  public String getCurrencyCode() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.CURRENCY_CODE, "INR");
  }

  @Override
  public void setCurrencyCode(String currencyCode) {
    editor.putString(SessionKeys.PreferenceTaskEntry.CURRENCY_CODE, currencyCode);
    editor.commit();
  }

  @Override
  public String getResetPasswordToken() {
    return sharedPreferences.getString(
        SessionKeys.PreferenceTaskEntry.RESET_PASSWORD_AUTH_TOKEN, "");
  }

  @Override
  public void setResetPasswordToken(String token) {
    editor.putString(SessionKeys.PreferenceTaskEntry.RESET_PASSWORD_AUTH_TOKEN, token);
    editor.commit();
  }

  @Override
  public String getCurrencySymbol() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.CURRENCY_SYMBOL, "$");
  }

  @Override
  public void setCurrencySymbol(String currencySymbol) {
    editor.putString(SessionKeys.PreferenceTaskEntry.CURRENCY_SYMBOL, currencySymbol);
    editor.commit();
  }

  @Override
  public int getPlatform() {
    return sharedPreferences.getInt(SessionKeys.PreferenceTaskEntry.PLATFORM, 2);
  }

  @Override
  public String getUserName() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.USER_NAME, "");
  }

  @Override
  public void setUserName(String name) {
    editor.putString(SessionKeys.PreferenceTaskEntry.USER_NAME, name);
    editor.commit();
  }

  @Override
  public String getUserMail() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.USER_MAIL, "");
  }

  @Override
  public void setUserMail(String mail) {
    editor.putString(SessionKeys.PreferenceTaskEntry.USER_MAIL, mail);
    editor.commit();
  }

  @Override
  public String getStoreCatId() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.STORE_CAT_ID, "");
  }

  @Override
  public void setStoreCatId(String storeCatId) {
    editor.putString(SessionKeys.PreferenceTaskEntry.STORE_CAT_ID, storeCatId);
    editor.commit();
  }

  @Override
  public void setStoreType(boolean storeType) {
    editor.putBoolean(SessionKeys.PreferenceTaskEntry.STORE_TYPE, storeType);
    editor.commit();
  }

  @Override
  public boolean getStoretype() {
    return sharedPreferences.getBoolean(SessionKeys.PreferenceTaskEntry.STORE_TYPE, true);
  }

  @Override
  public String getGrocerStoreId() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.GROCER_STORE_CAT_ID, "");
  }

  @Override
  public void setGrocerStoreId(String storeCatId) {
    editor.putString(SessionKeys.PreferenceTaskEntry.GROCER_STORE_CAT_ID, storeCatId);
    editor.commit();
  }

  @Override
  public String getEcomStoreId() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.ECOM_STORE_CAT_ID, "");
  }

  @Override
  public void setEcomStoreId(String storeCatId) {
    editor.putString(SessionKeys.PreferenceTaskEntry.ECOM_STORE_CAT_ID, storeCatId);
    editor.commit();
  }

  @Override
  public boolean isFirstTimeAsking(String permission) {
    return sharedPreferences.getBoolean(permission, true);
  }

  @Override
  public void firstTimeAsking(String permission, boolean isFirstTime) {
    editor.putBoolean(permission, isFirstTime);
    editor.commit();
  }

  public String getIpAddress() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.IP_ADDRESS, "");
  }

  public void setIpAddress(String ipAddress) {
    editor.putString(SessionKeys.PreferenceTaskEntry.IP_ADDRESS, ipAddress);
    editor.commit();
  }

  public void setLatLong(double lat, double longitude) {
    LatLong latLong = new LatLong(lat, longitude);
    String laLngStr = "";
    laLngStr = gson.toJson(latLong);
    editor.putString(PreferenceTaskEntry.LATITUDE, laLngStr);
    getAddress(context, lat, longitude);
    editor.commit();
  }

  public LatLong getLatLong() {
    String data = sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.LATITUDE, "");
    return gson.fromJson(data, LatLong.class);
  }

  public String getCity() {
    return sharedPreferences.getString(PreferenceTaskEntry.CITY, "");
  }

  @Override
  public String getWalletAmt() {
    return sharedPreferences.getString(PreferenceTaskEntry.WALLET_AMT, "");
  }

  @Override
  public void setWalletAmt(String walletAmt) {
    editor.putString(PreferenceTaskEntry.WALLET_AMT, walletAmt);
    editor.commit();
  }

  @Override
  public int getNotificationCount() {
    return sharedPreferences.getInt(SessionKeys.PreferenceTaskEntry.NOTIFICATION_COUNT, 0);
  }

  @Override
  public void setNotificationCount(int notificationCount) {
    editor.putInt(PreferenceTaskEntry.NOTIFICATION_COUNT, notificationCount);
    editor.commit();
  }

  @Override
  public String getMqttTopic() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.MQTT_TOPIC, "");
  }

  @Override
  public void setMqttTopic(String mqttTopic) {
    editor.putString(PreferenceTaskEntry.MQTT_TOPIC, mqttTopic);
    editor.commit();
  }

  @Override
  public void sessionTime(long sessionTime) {
    editor.putLong(PreferenceTaskEntry.SESSION_TIME, sessionTime);
    editor.commit();
  }

  @Override
  public long getSessionTime() {
    return sharedPreferences.getLong(SessionKeys.PreferenceTaskEntry.SESSION_TIME, 0);
  }

  @Override
  public Observable<Boolean> getGenerateTokenData() {
    return mGenerateTokenRepo.generateTokenObservable();
  }

  @Override
  public Observable<Boolean> getLogoutData() {
    return mLogoutRepo.logoutObservable();
  }

  @Override
  public String getReferalCode() {
    return sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.REFFERAL_CODE, "");
  }

  @Override
  public void setReferalCode(String referralCode) {
    editor.putString(PreferenceTaskEntry.REFFERAL_CODE, referralCode);
    editor.commit();
  }

  public String getCountry() {
    return sharedPreferences.getString(PreferenceTaskEntry.COUNTRY, "");
  }

  @Override
  public String getState() {
    return sharedPreferences.getString(PreferenceTaskEntry.STATE, "");
  }

  private String getAddress(Context context, double latitude, double longitude) {
    StringBuilder result = new StringBuilder();
    try {
      Geocoder geocoder = new Geocoder(context, Locale.getDefault());
      List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
      if (addresses.size() > LOWER_BOUND) {
        Address address = addresses.get(LOWER_BOUND);
        result.append(address.getAddressLine(LOWER_BOUND));
        editor.putString(PreferenceTaskEntry.CITY, address.getLocality());
        editor.putString(PreferenceTaskEntry.COUNTRY, address.getCountryName());
        Log.d("exe", "STATE" + address.getAdminArea());
        editor.putString(PreferenceTaskEntry.STATE, address.getAdminArea());
        editor.commit();
      }
    } catch (IOException e) {
      Log.d("", "" + e.getMessage());
    }
    return result.toString();
  }

  @Override
  public UserData getUserDetails() {
    String data = sharedPreferences.getString(SessionKeys.PreferenceTaskEntry.USER_DATA, "");
    return gson.fromJson(data, UserData.class);
  }

  @Override
  public void setUserDetails(UserData userData) {
    String userDataStr;
    if (userData != null) {
      userDataStr = gson.toJson(userData);
    } else {
      userDataStr = "";
    }
    editor.putString(SessionKeys.PreferenceTaskEntry.USER_DATA, userDataStr);
    editor.commit();
  }
}
