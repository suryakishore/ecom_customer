package com.customer.remote.http.model.response.sellerlist;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SellerDetails implements Parcelable {

  @SerializedName("data")
  @Expose
  private ArrayList<SellerDetailsItem> data;

  @SerializedName("message")
  @Expose
  private String message;

  public SellerDetails(
      ArrayList<SellerDetailsItem> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected SellerDetails(Parcel in) {
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SellerDetails> CREATOR = new Creator<SellerDetails>() {
    @Override
    public SellerDetails createFromParcel(Parcel in) {
      return new SellerDetails(in);
    }

    @Override
    public SellerDetails[] newArray(int size) {
      return new SellerDetails[size];
    }
  };

  public ArrayList<SellerDetailsItem> getData() {
    return data;
  }

  public void setData(
      ArrayList<SellerDetailsItem> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
