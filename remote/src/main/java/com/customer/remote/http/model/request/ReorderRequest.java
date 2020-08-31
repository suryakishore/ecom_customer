package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReorderRequest implements Parcelable {

  @Expose
  @SerializedName("type")
  private String type;

  @Expose
  @SerializedName("orderId")
  private String orderId;

  public ReorderRequest(String type, String orderId) {
    this.type = type;
    this.orderId = orderId;
  }

  protected ReorderRequest(Parcel in) {
    type = in.readString();
    orderId = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(type);
    dest.writeString(orderId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ReorderRequest> CREATOR = new Creator<ReorderRequest>() {
    @Override
    public ReorderRequest createFromParcel(Parcel in) {
      return new ReorderRequest(in);
    }

    @Override
    public ReorderRequest[] newArray(int size) {
      return new ReorderRequest[size];
    }
  };

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
