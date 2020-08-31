package com.customer.remote.http.model.response.guestSignIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestLocation implements Parcelable {
  public static final Creator<GuestLocation> CREATOR = new Creator<GuestLocation>() {
    @Override
    public GuestLocation createFromParcel(Parcel in) {
      return new GuestLocation(in);
    }

    @Override
    public GuestLocation[] newArray(int size) {
      return new GuestLocation[size];
    }
  };
  @SerializedName("lat")
  @Expose
  private String latitude;
  @SerializedName("long")
  @Expose
  private String longitude;

  protected GuestLocation(Parcel in) {
    latitude = in.readString();
    longitude = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(latitude);
    dest.writeString(longitude);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }
}
