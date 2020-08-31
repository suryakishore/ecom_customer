package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyMobileOrMailRequest implements Parcelable {
  @SerializedName("countryCode")
  @Expose
  private String countryCode;

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("verifyType")
  @Expose
  private int verifyType;

  @SerializedName("email")
  @Expose
  private String email;

  public VerifyMobileOrMailRequest(String countryCode, String mobile, int verifyType) {
    this.countryCode = countryCode;
    this.mobile = mobile;
    this.verifyType = verifyType;
  }

  public VerifyMobileOrMailRequest(String email, int verifyType) {
    this.email = email;
    this.verifyType = verifyType;
  }

  protected VerifyMobileOrMailRequest(Parcel in) {
    countryCode = in.readString();
    mobile = in.readString();
    verifyType = in.readInt();
    email = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(countryCode);
    dest.writeString(mobile);
    dest.writeInt(verifyType);
    dest.writeString(email);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<VerifyMobileOrMailRequest> CREATOR =
      new Creator<VerifyMobileOrMailRequest>() {
        @Override
        public VerifyMobileOrMailRequest createFromParcel(Parcel in) {
          return new VerifyMobileOrMailRequest(in);
        }

        @Override
        public VerifyMobileOrMailRequest[] newArray(int size) {
          return new VerifyMobileOrMailRequest[size];
        }
      };

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
}
