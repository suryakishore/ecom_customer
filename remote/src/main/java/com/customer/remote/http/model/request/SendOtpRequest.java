package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpRequest implements Parcelable {

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("countryCode")
  @Expose
  private String countryCode;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("verifyType")
  @Expose
  private int verifyType;
  @SerializedName("type")
  @Expose
  private int type;

  @SerializedName("triggeredBy")
  @Expose
  private String triggeredBy;

  @SerializedName("userName")
  @Expose
  private String userName;

  public SendOtpRequest(String mobile, String countryCode, int verifyType, String triggeredBy,
      String userName, int type) {
    this.mobile = mobile;
    this.countryCode = countryCode;
    this.verifyType = verifyType;
    this.triggeredBy = triggeredBy;
    this.userName = userName;
    this.type = type;
  }

  public SendOtpRequest(String email, int verifyType, String triggeredBy, String userName) {
    this.email = email;
    this.verifyType = verifyType;
    this.triggeredBy = triggeredBy;
    this.userName = userName;
  }

  protected SendOtpRequest(Parcel in) {
    mobile = in.readString();
    countryCode = in.readString();
    email = in.readString();
    verifyType = in.readInt();
    type = in.readInt();
    triggeredBy = in.readString();
    userName = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mobile);
    dest.writeString(countryCode);
    dest.writeString(email);
    dest.writeInt(verifyType);
    dest.writeInt(type);
    dest.writeString(triggeredBy);
    dest.writeString(userName);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SendOtpRequest> CREATOR = new Creator<SendOtpRequest>() {
    @Override
    public SendOtpRequest createFromParcel(Parcel in) {
      return new SendOtpRequest(in);
    }

    @Override
    public SendOtpRequest[] newArray(int size) {
      return new SendOtpRequest[size];
    }
  };

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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getVerifyType() {
    return verifyType;
  }

  public void setVerifyType(int verifyType) {
    this.verifyType = verifyType;
  }

  public String getTriggeredBy() {
    return triggeredBy;
  }

  public void setTriggeredBy(String triggeredBy) {
    this.triggeredBy = triggeredBy;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
