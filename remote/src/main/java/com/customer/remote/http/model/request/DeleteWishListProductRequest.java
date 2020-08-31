package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteWishListProductRequest implements Parcelable {
  @SerializedName("userid")
  @Expose
  private String userid;

  @SerializedName("productid")
  @Expose
  private String productid;

  @SerializedName("ipaddress")
  @Expose
  private String ipaddress;

  @SerializedName("latitude")
  @Expose
  private double latitude;

  @SerializedName("longitude")
  @Expose
  private double longitude;

  public DeleteWishListProductRequest(String userid, String productid, String ipaddress,
      double latitude, double longitude) {
    this.userid = userid;
    this.productid = productid;
    this.ipaddress = ipaddress;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  protected DeleteWishListProductRequest(Parcel in) {
    userid = in.readString();
    productid = in.readString();
    ipaddress = in.readString();
    latitude = in.readDouble();
    longitude = in.readDouble();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userid);
    dest.writeString(productid);
    dest.writeString(ipaddress);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<DeleteWishListProductRequest> CREATOR =
      new Creator<DeleteWishListProductRequest>() {
        @Override
        public DeleteWishListProductRequest createFromParcel(Parcel in) {
          return new DeleteWishListProductRequest(in);
        }

        @Override
        public DeleteWishListProductRequest[] newArray(int size) {
          return new DeleteWishListProductRequest[size];
        }
      };

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getProdiuctid() {
    return productid;
  }

  public void setProdiuctid(String prodiuctid) {
    this.productid = prodiuctid;
  }

  public String getIpaddress() {
    return ipaddress;
  }

  public void setIpaddress(String ipaddress) {
    this.ipaddress = ipaddress;
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
}
