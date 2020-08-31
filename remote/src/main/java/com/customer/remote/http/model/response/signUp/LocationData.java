package com.customer.remote.http.model.response.signUp;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData implements ValidItem, Parcelable {

  @SerializedName("lat")
  @Expose
  private String lat;

  @SerializedName("latitude")
  @Expose
  private double latitude;

  @SerializedName("long")
  @Expose
  private String longi;

  @SerializedName("longitude")
  @Expose
  private double longitude;

  public LocationData(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public LocationData(String lat, String longi) {
    this.lat = lat;
    this.longi = longi;
  }

  protected LocationData(Parcel in) {
    lat = in.readString();
    latitude = in.readDouble();
    longi = in.readString();
    longitude = in.readDouble();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(lat);
    dest.writeDouble(latitude);
    dest.writeString(longi);
    dest.writeDouble(longitude);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<LocationData> CREATOR = new Creator<LocationData>() {
    @Override
    public LocationData createFromParcel(Parcel in) {
      return new LocationData(in);
    }

    @Override
    public LocationData[] newArray(int size) {
      return new LocationData[size];
    }
  };

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

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLongi() {
    return longi;
  }

  public void setLongi(String longi) {
    this.longi = longi;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
