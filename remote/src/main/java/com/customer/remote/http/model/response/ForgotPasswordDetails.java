package com.customer.remote.http.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordDetails implements ValidItem, Parcelable {
  @SerializedName("otpId")
  @Expose
  private String otpId;

  @SerializedName("otpExpiryTime")
  @Expose
  private String otpExpiryTime;

  public ForgotPasswordDetails(String otpId, String otpExpiryTime) {
    this.otpId = otpId;
    this.otpExpiryTime = otpExpiryTime;
  }

  protected ForgotPasswordDetails(Parcel in) {
    otpId = in.readString();
    otpExpiryTime = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(otpId);
    dest.writeString(otpExpiryTime);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ForgotPasswordDetails> CREATOR =
      new Creator<ForgotPasswordDetails>() {
        @Override
        public ForgotPasswordDetails createFromParcel(Parcel in) {
          return new ForgotPasswordDetails(in);
        }

        @Override
        public ForgotPasswordDetails[] newArray(int size) {
          return new ForgotPasswordDetails[size];
        }
      };

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public String getOtpExpiryTime() {
    return otpExpiryTime;
  }

  public void setOtpExpiryTime(String otpExpiryTime) {
    this.otpExpiryTime = otpExpiryTime;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
