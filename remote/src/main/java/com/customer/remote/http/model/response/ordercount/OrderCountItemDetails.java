package com.customer.remote.http.model.response.ordercount;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCountItemDetails implements Parcelable {

  @SerializedName("statusText")
  @Expose
  private String statusText;
  @SerializedName("status")
  @Expose
  private Integer status;
  @SerializedName("count")
  @Expose
  private Integer count;

  public OrderCountItemDetails(String statusText, Integer status, Integer count) {
    this.statusText = statusText;
    this.status = status;
    this.count = count;
  }

  protected OrderCountItemDetails(Parcel in) {
    statusText = in.readString();
    if (in.readByte() == 0) {
      status = null;
    } else {
      status = in.readInt();
    }
    if (in.readByte() == 0) {
      count = null;
    } else {
      count = in.readInt();
    }
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(statusText);
    if (status == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(status);
    }
    if (count == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(count);
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<OrderCountItemDetails> CREATOR =
      new Creator<OrderCountItemDetails>() {
        @Override
        public OrderCountItemDetails createFromParcel(Parcel in) {
          return new OrderCountItemDetails(in);
        }

        @Override
        public OrderCountItemDetails[] newArray(int size) {
          return new OrderCountItemDetails[size];
        }
      };

  public String getStatusText() {
    return statusText;
  }

  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
