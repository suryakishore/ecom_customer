package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInRequest implements Parcelable {
  public static final Creator<SignInRequest> CREATOR = new Creator<SignInRequest>() {
    @Override
    public SignInRequest createFromParcel(Parcel in) {
      return new SignInRequest(in);
    }

    @Override
    public SignInRequest[] newArray(int size) {
      return new SignInRequest[size];
    }
  };
  @SerializedName("mobile")
  @Expose
  private String mobile;
  @SerializedName("countryCode")
  @Expose
  private String countryCode;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("password")
  @Expose
  private String password;
  @SerializedName("verifyType")
  @Expose
  private int verifyType;
  @SerializedName("loginType")
  @Expose
  private int loginType;
  @SerializedName("deviceId")
  @Expose
  private String deviceId;
  @SerializedName("deviceType")
  @Expose
  private int deviceType;
  @SerializedName("ipAddress")
  @Expose
  private String ipAddress;
  @SerializedName("googleId")
  @Expose
  private String googleId;
  @SerializedName("facebookId")
  @Expose
  private String facebookId;
  @SerializedName("deviceModel")
  @Expose
  private String deviceModel;
  @SerializedName("deviceMake")
  @Expose
  private String deviceMake;
  @SerializedName("browserName")
  @Expose
  private String browserName;
  @SerializedName("deviceOsVersion")
  @Expose
  private String deviceOsVersion;
  @SerializedName("latitude")
  @Expose
  private String latitude;
  @SerializedName("longitude")
  @Expose
  private String longitude;
  @SerializedName("appVersion")
  @Expose
  private String appVersion;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("country")
  @Expose
  private String country;
  @SerializedName("state")
  @Expose
  private String state;

  public SignInRequest(String email, String password,
      int verifyType, int loginType, String deviceId, int deviceType, String ipAddress,
      String googleId, String facebookId, String deviceModel, String deviceMake, String browserName,
      String deviceOsVersion, String latitude, String longitude, String appVersion, String city,
      String country, String state) {
    this.email = email;
    this.password = password;
    this.verifyType = verifyType;
    this.loginType = loginType;
    this.deviceId = deviceId;
    this.deviceType = deviceType;
    this.ipAddress = ipAddress;
    this.googleId = googleId;
    this.facebookId = facebookId;
    this.deviceModel = deviceModel;
    this.deviceMake = deviceMake;
    this.browserName = browserName;
    this.deviceOsVersion = deviceOsVersion;
    this.latitude = latitude;
    this.longitude = longitude;
    this.appVersion = appVersion;
    this.state = state;
    this.country = country;
    this.city = city;
  }

  public SignInRequest(String mobile, String countryCode, String password,
      int verifyType, int loginType, String deviceId, int deviceType, String ipAddress,
      String deviceModel, String deviceMake, String browserName, String deviceOsVersion,
      String latitude, String longitude, String appVersion, String city, String country,
      String state) {
    this.mobile = mobile;
    this.countryCode = countryCode;
    this.password = password;
    this.verifyType = verifyType;
    this.loginType = loginType;
    this.deviceId = deviceId;
    this.deviceType = deviceType;
    this.ipAddress = ipAddress;
    this.deviceModel = deviceModel;
    this.deviceMake = deviceMake;
    this.browserName = browserName;
    this.deviceOsVersion = deviceOsVersion;
    this.latitude = latitude;
    this.longitude = longitude;
    this.appVersion = appVersion;
    this.state = state;
    this.country = country;
    this.city = city;
  }

  protected SignInRequest(Parcel in) {
    mobile = in.readString();
    countryCode = in.readString();
    email = in.readString();
    password = in.readString();
    verifyType = in.readInt();
    loginType = in.readInt();
    deviceId = in.readString();
    deviceType = in.readInt();
    ipAddress = in.readString();
    googleId = in.readString();
    facebookId = in.readString();
    deviceModel = in.readString();
    deviceMake = in.readString();
    browserName = in.readString();
    deviceOsVersion = in.readString();
    latitude = in.readString();
    longitude = in.readString();
    appVersion = in.readString();
    city = in.readString();
    country = in.readString();
    state = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mobile);
    dest.writeString(countryCode);
    dest.writeString(email);
    dest.writeString(password);
    dest.writeInt(verifyType);
    dest.writeInt(loginType);
    dest.writeString(deviceId);
    dest.writeInt(deviceType);
    dest.writeString(ipAddress);
    dest.writeString(googleId);
    dest.writeString(facebookId);
    dest.writeString(deviceModel);
    dest.writeString(deviceMake);
    dest.writeString(browserName);
    dest.writeString(deviceOsVersion);
    dest.writeString(latitude);
    dest.writeString(longitude);
    dest.writeString(appVersion);
    dest.writeString(city);
    dest.writeString(country);
    dest.writeString(state);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGoogleId() {
    return googleId;
  }

  public void setGoogleId(String googleId) {
    this.googleId = googleId;
  }

  public String getFacebookId() {
    return facebookId;
  }

  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getVerifyType() {
    return verifyType;
  }

  public void setVerifyType(int verifyType) {
    this.verifyType = verifyType;
  }

  public int getLoginType() {
    return loginType;
  }

  public void setLoginType(int loginType) {
    this.loginType = loginType;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public int getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(int deviceType) {
    this.deviceType = deviceType;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceMake() {
    return deviceMake;
  }

  public void setDeviceMake(String deviceMake) {
    this.deviceMake = deviceMake;
  }

  public String getBrowserName() {
    return browserName;
  }

  public void setBrowserName(String browserName) {
    this.browserName = browserName;
  }

  public String getDeviceOsVersion() {
    return deviceOsVersion;
  }

  public void setDeviceOsVersion(String deviceOsVersion) {
    this.deviceOsVersion = deviceOsVersion;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
