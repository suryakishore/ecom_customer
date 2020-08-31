package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header implements Parcelable {
  public static final Creator<Header> CREATOR = new Creator<Header>() {
    @Override
    public Header createFromParcel(Parcel in) {
      return new Header(in);
    }

    @Override
    public Header[] newArray(int size) {
      return new Header[size];
    }
  };
  @SerializedName("ipAddress")
  @Expose
  private String ipAddress;
  @SerializedName("token")
  @Expose
  private String token;
  @SerializedName("language")
  @Expose
  private String language;
  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;
  @SerializedName("currencyCode")
  @Expose
  private String currencyCode;
  @SerializedName("platform")
  @Expose
  private int platform;
  @SerializedName("latitude")
  @Expose
  private double latitude;
  @SerializedName("longitude")
  @Expose
  private double longitude;
  @SerializedName("storeCatId")
  @Expose
  private String storeCatId;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("country")
  @Expose
  private String country;

  public Header(String token, String language, String currencySymbol, String currencyCode,
      int platform, String storeCatId, String city, String country, String ipAddress,
      double latitude, double longitude) {
    this.token = token;
    this.language = language;
    this.currencySymbol = currencySymbol;
    this.currencyCode = currencyCode;
    this.platform = platform;
    this.storeCatId = storeCatId;
    this.city = city;
    this.country = country;
    this.ipAddress = ipAddress;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Header(String token, String language, String currencySymbol, String currencyCode,
      int platform, double latitude, double longitude, String ipAddress, String storeCatId) {
    this.token = token;
    this.language = language;
    this.currencySymbol = currencySymbol;
    this.currencyCode = currencyCode;
    this.platform = platform;
    this.latitude = latitude;
    this.longitude = longitude;
    this.ipAddress = ipAddress;
    this.storeCatId = storeCatId;
  }

  protected Header(Parcel in) {
    ipAddress = in.readString();
    token = in.readString();
    language = in.readString();
    currencySymbol = in.readString();
    currencyCode = in.readString();
    platform = in.readInt();
    latitude = in.readDouble();
    longitude = in.readDouble();
    storeCatId = in.readString();
    city = in.readString();
    country = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(ipAddress);
    dest.writeString(token);
    dest.writeString(language);
    dest.writeString(currencySymbol);
    dest.writeString(currencyCode);
    dest.writeInt(platform);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
    dest.writeString(storeCatId);
    dest.writeString(city);
    dest.writeString(country);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getStoreCatId() {
    return storeCatId;
  }

  public void setStoreCatId(String storeCatId) {
    this.storeCatId = storeCatId;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
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

  public int getPlatform() {
    return platform;
  }

  public void setPlatform(int platform) {
    this.platform = platform;
  }
}
