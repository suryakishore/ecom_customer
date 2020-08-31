package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.LOWER_BOUND;

import android.util.Base64;
import com.customer.data.DataSource;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.utils.LatLong;
import com.customer.remote.http.model.request.Header;

public class BaseRepository {
  private DataSource dataSource;
  private PreferenceManager preferenceManager;

  public BaseRepository(DataSource dataSource) {
    this.dataSource = dataSource;
    preferenceManager = dataSource.preference();
  }

  String getGuestAuthToken() {
    return preferenceManager.getGuestToken();
  }

  String getLanguage() {
    return preferenceManager.getLanguage();
  }

  public String getUserId() {
    return preferenceManager.getUserId();
  }

  public String getCurrencySymbol() {
    return Base64.encodeToString(preferenceManager.getCurrencySymbol().getBytes(), Base64.NO_WRAP);
  }

  public String getAccessToken() {
    if (preferenceManager.getIsLoggedIn()) {
      return preferenceManager.getToken(preferenceManager.getUserId());
    } else {
      return preferenceManager.getGuestToken();
    }
  }

  public String getOTP_Token() {
    return preferenceManager.getOtpToken();
  }

  public Header getHeader() {
    LatLong latLong = preferenceManager.getLatLong();
    return new Header(getAccessToken(), getLanguage(), getCurrencySymbol(),
        preferenceManager.getCurrencyCode(), preferenceManager.getPlatform(),
        preferenceManager.getStoreCatId(), preferenceManager.getCity(),
        preferenceManager.getCountry(), preferenceManager.getIpAddress(),
        latLong != null ? latLong.getLat() : LOWER_BOUND,
        latLong != null ? latLong.getLongitude() : LOWER_BOUND);
  }

  Header getHeaderWithIp(String ipAddress, double latitude, double longitude) {
    Header header = getHeader();
    header.setIpAddress(ipAddress);
    header.setLatitude(latitude);
    header.setLongitude(longitude);
    return header;
  }
}
