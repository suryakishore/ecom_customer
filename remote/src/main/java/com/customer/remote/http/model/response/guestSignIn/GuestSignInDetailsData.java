package com.customer.remote.http.model.response.guestSignIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GuestSignInDetailsData implements ValidItem, Parcelable {
  public static final Creator<GuestSignInDetailsData> CREATOR =
      new Creator<GuestSignInDetailsData>() {
        @Override
        public GuestSignInDetailsData createFromParcel(Parcel in) {
          return new GuestSignInDetailsData(in);
        }

        @Override
        public GuestSignInDetailsData[] newArray(int size) {
          return new GuestSignInDetailsData[size];
        }
      };
  @SerializedName("googleMapKeyMqtt")
  @Expose
  private String googleMapKeyMqtt;
  @SerializedName("ip")
  @Expose
  private String ip;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("region")
  @Expose
  private String region;
  @SerializedName("country")
  @Expose
  private String country;
  @SerializedName("type")
  @Expose
  private String type;
  @SerializedName("sid")
  @Expose
  private String sid;
  @SerializedName("token")
  @Expose
  private Token token;
  @SerializedName("location")
  @Expose
  private GuestLocation location;
  @SerializedName("storeCategory")
  @Expose
  private ArrayList<StoreCategoryIdData> storeCategory;

  public GuestSignInDetailsData(String googleMapKeyMqtt, String type, String sid, Token token,
      ArrayList<StoreCategoryIdData> storeCategory) {
    this.googleMapKeyMqtt = googleMapKeyMqtt;
    this.type = type;
    this.sid = sid;
    this.token = token;
    this.storeCategory = storeCategory;
  }

  protected GuestSignInDetailsData(Parcel in) {
    googleMapKeyMqtt = in.readString();
    type = in.readString();
    sid = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(googleMapKeyMqtt);
    dest.writeString(ip);
    dest.writeString(city);
    dest.writeString(region);
    dest.writeString(country);
    dest.writeString(type);
    dest.writeString(sid);
    dest.writeParcelable(token, flags);
    dest.writeParcelable(location, flags);
    dest.writeTypedList(storeCategory);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public ArrayList<StoreCategoryIdData> getStoreCategory() {
    return storeCategory;
  }

  public void setStoreCategory(
      ArrayList<StoreCategoryIdData> storeCategory) {
    this.storeCategory = storeCategory;
  }

  public String getGoogleMapKeyMqtt() {
    return googleMapKeyMqtt;
  }

  public void setGoogleMapKeyMqtt(String googleMapKeyMqtt) {
    this.googleMapKeyMqtt = googleMapKeyMqtt;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public GuestLocation getLocation() {
    return location;
  }

  public void setLocation(GuestLocation location) {
    this.location = location;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}