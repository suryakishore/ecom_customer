package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileOtpVerifyRequest implements Parcelable {
  /*  @SerializedName("userId")
    @Expose*/
  private String userId;

  @SerializedName("otpId")
  @Expose
  private String otpId;

  @SerializedName("otpCode")
  @Expose
  private String otpCode;

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("countryCode")
  @Expose
  private String countryCode;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("type")
  @Expose
  private int type;

  public ProfileOtpVerifyRequest(String userId, String otpId, String otpCode, String mobile,
      String countryCode, int type) {
    this.userId = userId;
    this.otpId = otpId;
    this.otpCode = otpCode;
    this.mobile = mobile;
    this.countryCode = countryCode;
    this.type = type;
  }

  public ProfileOtpVerifyRequest(String userId, String otpId, String otpCode, String email,
      int type) {
    this.userId = userId;
    this.otpId = otpId;
    this.otpCode = otpCode;
    this.email = email;
    this.type = type;
  }

  protected ProfileOtpVerifyRequest(Parcel in) {
    userId = in.readString();
    otpId = in.readString();
    otpCode = in.readString();
    mobile = in.readString();
    countryCode = in.readString();
    email = in.readString();
    type = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userId);
    dest.writeString(otpId);
    dest.writeString(otpCode);
    dest.writeString(mobile);
    dest.writeString(countryCode);
    dest.writeString(email);
    dest.writeInt(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ProfileOtpVerifyRequest> CREATOR =
      new Creator<ProfileOtpVerifyRequest>() {
        @Override
        public ProfileOtpVerifyRequest createFromParcel(Parcel in) {
          return new ProfileOtpVerifyRequest(in);
        }

        @Override
        public ProfileOtpVerifyRequest[] newArray(int size) {
          return new ProfileOtpVerifyRequest[size];
        }
      };

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public String getOtpCode() {
    return otpCode;
  }

  public void setOtpCode(String otpCode) {
    this.otpCode = otpCode;
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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
