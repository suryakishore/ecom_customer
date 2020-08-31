package com.customer.remote.http.model.response.signUp;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.guestSignIn.Token;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpDetails implements ValidItem, Parcelable {

  @SerializedName("country")
  @Expose
  private String country;

  @SerializedName("statusMsg")
  @Expose
  private String statusMsg;

  @SerializedName("city")
  @Expose
  private String city;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("mobile")
  @Expose
  private String mobile;
  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;
  @SerializedName("currencyCode")
  @Expose
  private String currencyCode;
  @SerializedName("location")
  @Expose
  private LocationData location;

  @SerializedName("region")
  @Expose
  private String region;

  @SerializedName("userId")
  @Expose
  private String userId;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("token")
  @Expose
  private Token token;

  @SerializedName("status")
  @Expose
  private String status;

  @SerializedName("countryCode")
  @Expose
  private String countryCode;

  @SerializedName("profilePic")
  @Expose
  private String profilePic;
  @SerializedName("referralCode")
  @Expose
  private String referralCode;

  public SignUpDetails(String country, String statusMsg, String city, String name, String mobile,
      LocationData location, String region, String userId, String email, Token token,
      String status, String countryCode,String profilePic,String referralCode) {
    this.country = country;
    this.statusMsg = statusMsg;
    this.city = city;
    this.name = name;
    this.mobile = mobile;
    this.location = location;
    this.region = region;
    this.userId = userId;
    this.email = email;
    this.token = token;
    this.status = status;
    this.countryCode = countryCode;
    this.profilePic=profilePic;
    this.referralCode=referralCode;
  }

  protected SignUpDetails(Parcel in) {
    country = in.readString();
    statusMsg = in.readString();
    city = in.readString();
    name = in.readString();
    mobile = in.readString();
    location = in.readParcelable(LocationData.class.getClassLoader());
    region = in.readString();
    userId = in.readString();
    email = in.readString();
    token = in.readParcelable(Token.class.getClassLoader());
    status = in.readString();
    countryCode = in.readString();
    profilePic = in.readString();
    referralCode = in.readString();
    currencySymbol=in.readString();
    currencyCode=in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(country);
    dest.writeString(statusMsg);
    dest.writeString(city);
    dest.writeString(name);
    dest.writeString(mobile);
    dest.writeParcelable(location, flags);
    dest.writeString(region);
    dest.writeString(userId);
    dest.writeString(email);
    dest.writeParcelable(token, flags);
    dest.writeString(status);
    dest.writeString(countryCode);
    dest.writeString(profilePic);
    dest.writeString(referralCode);
    dest.writeString(currencyCode);
    dest.writeString(currencySymbol);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SignUpDetails> CREATOR = new Creator<SignUpDetails>() {
    @Override
    public SignUpDetails createFromParcel(Parcel in) {
      return new SignUpDetails(in);
    }

    @Override
    public SignUpDetails[] newArray(int size) {
      return new SignUpDetails[size];
    }
  };

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public LocationData getLocation() {
    return location;
  }

  public void setLocation(LocationData location) {
    this.location = location;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String toEmail) {
    this.email = toEmail;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReferralCode() {
    return referralCode;
  }

  public void setReferralCode(String referralCode) {
    this.referralCode = referralCode;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
