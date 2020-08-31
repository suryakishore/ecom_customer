package com.customer.remote.http.model.response.signIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.guestSignIn.Token;
import com.customer.remote.http.model.response.signUp.LocationData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInDetailsData implements ValidItem, Parcelable {
  @SerializedName("accessToken")
  @Expose
  private String accessToken;

  @SerializedName("otpExpiryTime")
  @Expose
  private int otpExpiryTime;

  @SerializedName("otpId")
  @Expose
  private String otpId;

  @SerializedName("country")
  @Expose
  private String country;

  @SerializedName("city")
  @Expose
  private String city;

  @SerializedName("googleMapKeyMqtt")
  @Expose
  private String googleMapKeyMqtt;

  @SerializedName("mmjCard")
  @Expose
  private IdentityCard mmjCard;

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("fcmTopic")
  @Expose
  private String fcmTopic;

  @SerializedName("mqttTopic")
  @Expose
  private String mqttTopic;

  @SerializedName("identityCard")
  @Expose
  private IdentityCard identityCard;

  @SerializedName("userId")
  @Expose
  private String userId;

  @SerializedName("token")
  @Expose
  private Token token;

  @SerializedName("countryCode")
  @Expose
  private String countryCode;
  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;
  @SerializedName("currencyCode")
  @Expose
  private String currencyCode;
  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("location")
  @Expose
  private LocationData location;

  @SerializedName("region")
  @Expose
  private String region;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("status")
  @Expose
  private String status;

  @SerializedName("requester_id")
  @Expose
  private String requester_id;

  @SerializedName("profilePic")
  @Expose
  private String profilePic;

  @SerializedName("referralCode")
  @Expose
  private String referralCode;
  @SerializedName("expireTime")
  @Expose
  private String expireTime;
  public SignInDetailsData(String accessToken, String otpId, int otpExpiryTime) {
    this.accessToken = accessToken;
    this.otpId = otpId;
    this.otpExpiryTime = otpExpiryTime;
  }

  public SignInDetailsData(String country, String city, String googleMapKeyMqtt,
      IdentityCard mmjCard, String mobile, String fcmTopic, String mqttTopic,
      IdentityCard identityCard, String userId, Token token, String countryCode, String name,
      LocationData location, String region, String email, String status, String requester_id) {
    this.country = country;
    this.city = city;
    this.googleMapKeyMqtt = googleMapKeyMqtt;
    this.mmjCard = mmjCard;
    this.mobile = mobile;
    this.fcmTopic = fcmTopic;
    this.mqttTopic = mqttTopic;
    this.identityCard = identityCard;
    this.userId = userId;
    this.token = token;
    this.countryCode = countryCode;
    this.name = name;
    this.location = location;
    this.region = region;
    this.email = email;
    this.status = status;
    this.requester_id = requester_id;
  }

  protected SignInDetailsData(Parcel in) {
    accessToken = in.readString();
    otpExpiryTime = in.readInt();
    otpId = in.readString();
    country = in.readString();
    city = in.readString();
    googleMapKeyMqtt = in.readString();
    mmjCard = in.readParcelable(IdentityCard.class.getClassLoader());
    mobile = in.readString();
    fcmTopic = in.readString();
    mqttTopic = in.readString();
    identityCard = in.readParcelable(IdentityCard.class.getClassLoader());
    userId = in.readString();
    token = in.readParcelable(Token.class.getClassLoader());
    countryCode = in.readString();
    name = in.readString();
    region = in.readString();
    email = in.readString();
    status = in.readString();
    requester_id = in.readString();
    profilePic = in.readString();
    referralCode = in.readString();
    currencyCode=in.readString();
    currencySymbol=in.readString();
    expireTime=in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(accessToken);
    dest.writeInt(otpExpiryTime);
    dest.writeString(otpId);
    dest.writeString(country);
    dest.writeString(city);
    dest.writeString(googleMapKeyMqtt);
    dest.writeParcelable(mmjCard, flags);
    dest.writeString(mobile);
    dest.writeString(fcmTopic);
    dest.writeString(mqttTopic);
    dest.writeParcelable(identityCard, flags);
    dest.writeString(userId);
    dest.writeParcelable(token, flags);
    dest.writeString(countryCode);
    dest.writeString(name);
    dest.writeString(region);
    dest.writeString(email);
    dest.writeString(status);
    dest.writeString(requester_id);
    dest.writeString(profilePic);
    dest.writeString(referralCode);
    dest.writeString(currencyCode);
    dest.writeString(currencySymbol);
    dest.writeString(expireTime);

  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SignInDetailsData> CREATOR = new Creator<SignInDetailsData>() {
    @Override
    public SignInDetailsData createFromParcel(Parcel in) {
      return new SignInDetailsData(in);
    }

    @Override
    public SignInDetailsData[] newArray(int size) {
      return new SignInDetailsData[size];
    }
  };

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public int getOtpExpiryTime() {
    return otpExpiryTime;
  }

  public void setOtpExpiryTime(int otpExpiryTime) {
    this.otpExpiryTime = otpExpiryTime;
  }

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getGoogleMapKeyMqtt() {
    return googleMapKeyMqtt;
  }

  public void setGoogleMapKeyMqtt(String googleMapKeyMqtt) {
    this.googleMapKeyMqtt = googleMapKeyMqtt;
  }

  public IdentityCard getMmjCard() {
    return mmjCard;
  }

  public void setMmjCard(IdentityCard mmjCard) {
    this.mmjCard = mmjCard;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getFcmTopic() {
    return fcmTopic;
  }

  public void setFcmTopic(String fcmTopic) {
    this.fcmTopic = fcmTopic;
  }

  public String getMqttTopic() {
    return mqttTopic;
  }

  public void setMqttTopic(String mqttTopic) {
    this.mqttTopic = mqttTopic;
  }

  public IdentityCard getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(IdentityCard identityCard) {
    this.identityCard = identityCard;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRequester_id() {
    return requester_id;
  }

  public void setRequester_id(String requester_id) {
    this.requester_id = requester_id;
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

  public String getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
