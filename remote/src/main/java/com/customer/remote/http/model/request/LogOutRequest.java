package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.DeviceInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogOutRequest implements Parcelable {

  @SerializedName("ipAddress")
  @Expose
  private String ipAddress;

  @SerializedName("latitude")
  @Expose
  private double latitude;

  @SerializedName("longitude")
  @Expose
  private double longitude;

  @SerializedName("deviceMake")
  @Expose
  private String deviceMake;

  @SerializedName("deviceTime")
  @Expose
  private String deviceTime;

  @SerializedName("deviceModel")
  @Expose
  private String deviceModel;

  @SerializedName("deviceOsVersion")
  @Expose
  private String deviceOsVersion;

  @SerializedName("city")
  @Expose
  private String city;

  @SerializedName("country")
  @Expose
  private String country;

  public LogOutRequest(String ipAddress, double latitude, double longitude) {
    this.ipAddress = ipAddress;
    this.latitude = latitude;
    this.longitude = longitude;
    this.deviceMake = DeviceInfo.DEVICE_MAKE;
    this.deviceTime = String.valueOf(DeviceInfo.DEVICE_TIME.getTime());
    this.deviceModel = DeviceInfo.DEVICE_MODEL;
    this.deviceOsVersion = String.valueOf(DeviceInfo.DEVICE_OS_VERSION);
  }

  protected LogOutRequest(Parcel in) {
    ipAddress = in.readString();
    latitude = in.readDouble();
    longitude = in.readDouble();
    deviceMake = in.readString();
    deviceTime = in.readString();
    deviceModel = in.readString();
    deviceOsVersion = in.readString();
    city = in.readString();
    country = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(ipAddress);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
    dest.writeString(deviceMake);
    dest.writeString(deviceTime);
    dest.writeString(deviceModel);
    dest.writeString(deviceOsVersion);
    dest.writeString(city);
    dest.writeString(country);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<LogOutRequest> CREATOR = new Creator<LogOutRequest>() {
    @Override
    public LogOutRequest createFromParcel(Parcel in) {
      return new LogOutRequest(in);
    }

    @Override
    public LogOutRequest[] newArray(int size) {
      return new LogOutRequest[size];
    }
  };

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getDeviceMake() {
    return deviceMake;
  }

  public void setDeviceMake(String deviceMake) {
    this.deviceMake = deviceMake;
  }

  public String getDeviceTime() {
    return deviceTime;
  }

  public void setDeviceTime(String deviceTime) {
    this.deviceTime = deviceTime;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceOsVersion() {
    return deviceOsVersion;
  }

  public void setDeviceOsVersion(String deviceOsVersion) {
    this.deviceOsVersion = deviceOsVersion;
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
}
