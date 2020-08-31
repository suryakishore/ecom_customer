package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest implements Parcelable {

  @SerializedName("verifyType")
  @Expose
  private int verifyType;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("countryCode")
  @Expose
  private String countryCode;

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("deviceId")
  @Expose
  private String deviceId;

  @SerializedName("deviceMake")
  @Expose
  private String deviceMake;

  @SerializedName("deviceModel")
  @Expose
  private String deviceModel;

  @SerializedName("deviceType")
  @Expose
  private String deviceType;

  public ForgotPasswordRequest(int verifyType, String countryCode, String mobile, String deviceId,
      String deviceMake, String deviceModel, String deviceType) {
    this.verifyType = verifyType;
    this.countryCode = countryCode;
    this.mobile = mobile;
    this.deviceId = deviceId;
    this.deviceMake = deviceMake;
    this.deviceModel = deviceModel;
    this.deviceType = deviceType;
  }

  public ForgotPasswordRequest(int verifyType, String email, String deviceId, String deviceMake,
      String deviceModel, String deviceType) {
    this.verifyType = verifyType;
    this.email = email;
    this.deviceId = deviceId;
    this.deviceMake = deviceMake;
    this.deviceModel = deviceModel;
    this.deviceType = deviceType;
  }

  protected ForgotPasswordRequest(Parcel in) {
    verifyType = in.readInt();
    email = in.readString();
    countryCode = in.readString();
    mobile = in.readString();
    deviceId = in.readString();
    deviceMake = in.readString();
    deviceModel = in.readString();
    deviceType = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(verifyType);
    dest.writeString(email);
    dest.writeString(countryCode);
    dest.writeString(mobile);
    dest.writeString(deviceId);
    dest.writeString(deviceMake);
    dest.writeString(deviceModel);
    dest.writeString(deviceType);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ForgotPasswordRequest> CREATOR =
      new Creator<ForgotPasswordRequest>() {
        @Override
        public ForgotPasswordRequest createFromParcel(Parcel in) {
          return new ForgotPasswordRequest(in);
        }

        @Override
        public ForgotPasswordRequest[] newArray(int size) {
          return new ForgotPasswordRequest[size];
        }
      };

  public int getVerifyType() {
    return verifyType;
  }

  public void setVerifyType(int verifyType) {
    this.verifyType = verifyType;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceMake() {
    return deviceMake;
  }

  public void setDeviceMake(String deviceMake) {
    this.deviceMake = deviceMake;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }
}
