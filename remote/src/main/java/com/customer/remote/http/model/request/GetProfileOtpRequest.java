package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileOtpRequest implements Parcelable {
  /*  @SerializedName("userId")
    @Expose*/
  private String userId;

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

  public GetProfileOtpRequest(String userId, String mobile, String countryCode, int type) {
    this.userId = userId;
    this.mobile = mobile;
    this.countryCode = countryCode;
    this.type = type;
  }

  public GetProfileOtpRequest(String userId, String email, int type) {
    this.userId = userId;
    this.email = email;
    this.type = type;
  }

  protected GetProfileOtpRequest(Parcel in) {
    userId = in.readString();
    mobile = in.readString();
    countryCode = in.readString();
    email = in.readString();
    type = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userId);
    dest.writeString(mobile);
    dest.writeString(countryCode);
    dest.writeString(email);
    dest.writeInt(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GetProfileOtpRequest> CREATOR = new Creator<GetProfileOtpRequest>() {
    @Override
    public GetProfileOtpRequest createFromParcel(Parcel in) {
      return new GetProfileOtpRequest(in);
    }

    @Override
    public GetProfileOtpRequest[] newArray(int size) {
      return new GetProfileOtpRequest[size];
    }
  };

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
