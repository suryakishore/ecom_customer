package com.customer.remote.http.model.response.profile;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileDevicesDetails implements Parcelable {
  @SerializedName("currentlyActive")
  @Expose
  private String currentlyActive;

  @SerializedName("deviceType")
  @Expose
  private String deviceType;

  @SerializedName("lastLogin")
  @Expose
  private String lastLogin;

  @SerializedName("appVersion")
  @Expose
  private String appVersion;

  @SerializedName("lastTimestamp")
  @Expose
  private String lastTimestamp;

  @SerializedName("browserVersion")
  @Expose
  private String browserVersion;

  @SerializedName("browserName")
  @Expose
  private String browserName;

  @SerializedName("deviceTypeMsg")
  @Expose
  private String deviceTypeMsg;

  @SerializedName("deviceOsVersion")
  @Expose
  private String deviceOsVersion;

  @SerializedName("lastISOdate")
  @Expose
  private String lastISOdate;

  @SerializedName("deviceId")
  @Expose
  private String deviceId;

  @SerializedName("pushToken")
  @Expose
  private String pushToken;

  public MobileDevicesDetails(String currentlyActive, String deviceType, String lastLogin,
      String appVersion, String lastTimestamp, String browserVersion, String browserName,
      String deviceTypeMsg, String deviceOsVersion, String lastISOdate, String deviceId,
      String pushToken) {
    this.currentlyActive = currentlyActive;
    this.deviceType = deviceType;
    this.lastLogin = lastLogin;
    this.appVersion = appVersion;
    this.lastTimestamp = lastTimestamp;
    this.browserVersion = browserVersion;
    this.browserName = browserName;
    this.deviceTypeMsg = deviceTypeMsg;
    this.deviceOsVersion = deviceOsVersion;
    this.lastISOdate = lastISOdate;
    this.deviceId = deviceId;
    this.pushToken = pushToken;
  }

  protected MobileDevicesDetails(Parcel in) {
    currentlyActive = in.readString();
    deviceType = in.readString();
    lastLogin = in.readString();
    appVersion = in.readString();
    lastTimestamp = in.readString();
    browserVersion = in.readString();
    browserName = in.readString();
    deviceTypeMsg = in.readString();
    deviceOsVersion = in.readString();
    lastISOdate = in.readString();
    deviceId = in.readString();
    pushToken = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(currentlyActive);
    dest.writeString(deviceType);
    dest.writeString(lastLogin);
    dest.writeString(appVersion);
    dest.writeString(lastTimestamp);
    dest.writeString(browserVersion);
    dest.writeString(browserName);
    dest.writeString(deviceTypeMsg);
    dest.writeString(deviceOsVersion);
    dest.writeString(lastISOdate);
    dest.writeString(deviceId);
    dest.writeString(pushToken);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<MobileDevicesDetails> CREATOR = new Creator<MobileDevicesDetails>() {
    @Override
    public MobileDevicesDetails createFromParcel(Parcel in) {
      return new MobileDevicesDetails(in);
    }

    @Override
    public MobileDevicesDetails[] newArray(int size) {
      return new MobileDevicesDetails[size];
    }
  };

  public String getCurrentlyActive() {
    return currentlyActive;
  }

  public void setCurrentlyActive(String currentlyActive) {
    this.currentlyActive = currentlyActive;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(String lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getLastTimestamp() {
    return lastTimestamp;
  }

  public void setLastTimestamp(String lastTimestamp) {
    this.lastTimestamp = lastTimestamp;
  }

  public String getBrowserVersion() {
    return browserVersion;
  }

  public void setBrowserVersion(String browserVersion) {
    this.browserVersion = browserVersion;
  }

  public String getBrowserName() {
    return browserName;
  }

  public void setBrowserName(String browserName) {
    this.browserName = browserName;
  }

  public String getDeviceTypeMsg() {
    return deviceTypeMsg;
  }

  public void setDeviceTypeMsg(String deviceTypeMsg) {
    this.deviceTypeMsg = deviceTypeMsg;
  }

  public String getDeviceOsVersion() {
    return deviceOsVersion;
  }

  public void setDeviceOsVersion(String deviceOsVersion) {
    this.deviceOsVersion = deviceOsVersion;
  }

  public String getLastISOdate() {
    return lastISOdate;
  }

  public void setLastISOdate(String lastISOdate) {
    this.lastISOdate = lastISOdate;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getPushToken() {
    return pushToken;
  }

  public void setPushToken(String pushToken) {
    this.pushToken = pushToken;
  }
}
