package com.customer.domain.model.profile;

public class MobileDevicesData {
  private String currentlyActive;

  private String deviceType;

  private String lastLogin;

  private String appVersion;

  private String lastTimestamp;

  private String browserVersion;

  private String browserName;

  private String deviceTypeMsg;

  private String deviceOsVersion;

  private String lastISOdate;

  private String deviceId;

  private String pushToken;

  public MobileDevicesData(String currentlyActive, String deviceType, String lastLogin,
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
