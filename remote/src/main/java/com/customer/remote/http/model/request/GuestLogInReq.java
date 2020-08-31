package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestLogInReq implements Parcelable {

  @SerializedName("deviceId")
  @Expose
  private String deviceId;
  @SerializedName("ipAddress")
  @Expose
  private String ipAddress;
  @SerializedName("appVersion")
  @Expose
  private String appVersion;
  @SerializedName("deviceType")
  @Expose
  private int deviceType;
  @SerializedName("deviceMake")
  @Expose
  private String deviceMake;
  @SerializedName("deviceModel")
  @Expose
  private String deviceModel;
  @SerializedName("deviceOsVersion")
  @Expose
  private String deviceOsVersion;
  @SerializedName("browserName")
  @Expose
  private String browserName;
  @SerializedName("browserVersion")
  @Expose
  private String browserVersion;
  @SerializedName("deviceTime")
  @Expose
  private String deviceTime;
  @SerializedName("latitude")
  @Expose
  private String latitude;
  @SerializedName("longitude")
  @Expose
  private String longitude;

  public GuestLogInReq(String deviceId, String appVersion, int deviceType, String deviceMake,
      String deviceModel, String deviceOsVersion, String ipAddress, String browserName,
      String browserVersion, String deviceTime,String latitude,String longitude) {
    this.deviceId = deviceId;
    this.appVersion = appVersion;
    this.deviceType = deviceType;
    this.deviceMake = deviceMake;
    this.deviceModel = deviceModel;
    this.deviceOsVersion = deviceOsVersion;
    this.ipAddress=ipAddress;
    this.browserName=browserName;
    this.browserVersion=browserVersion;
    this.deviceTime=deviceTime;
    this.latitude=latitude;
    this.longitude=longitude;
  }

  protected GuestLogInReq(Parcel in) {
    deviceId = in.readString();
    ipAddress = in.readString();
    appVersion = in.readString();
    deviceType = in.readInt();
    deviceMake = in.readString();
    deviceModel = in.readString();
    deviceOsVersion = in.readString();
    browserName = in.readString();
    browserVersion = in.readString();
    deviceTime = in.readString();
    latitude = in.readString();
    longitude = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(deviceId);
    dest.writeString(ipAddress);
    dest.writeString(appVersion);
    dest.writeInt(deviceType);
    dest.writeString(deviceMake);
    dest.writeString(deviceModel);
    dest.writeString(deviceOsVersion);
    dest.writeString(browserName);
    dest.writeString(browserVersion);
    dest.writeString(deviceTime);
    dest.writeString(latitude);
    dest.writeString(longitude);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GuestLogInReq> CREATOR = new Creator<GuestLogInReq>() {
    @Override
    public GuestLogInReq createFromParcel(Parcel in) {
      return new GuestLogInReq(in);
    }

    @Override
    public GuestLogInReq[] newArray(int size) {
      return new GuestLogInReq[size];
    }
  };
}
