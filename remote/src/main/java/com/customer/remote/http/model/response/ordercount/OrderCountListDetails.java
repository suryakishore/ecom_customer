package com.customer.remote.http.model.response.ordercount;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.tracking.TrackingItemDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class OrderCountListDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private ArrayList<OrderCountItemDetails> data;
  @SerializedName("message")
  @Expose
  private String message;

  public OrderCountListDetails(
      ArrayList<OrderCountItemDetails> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected OrderCountListDetails(Parcel in) {
    data = in.createTypedArrayList(OrderCountItemDetails.CREATOR);
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(data);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<OrderCountListDetails> CREATOR = new Creator<OrderCountListDetails>() {
    @Override
    public OrderCountListDetails createFromParcel(Parcel in) {
      return new OrderCountListDetails(in);
    }

    @Override
    public OrderCountListDetails[] newArray(int size) {
      return new OrderCountListDetails[size];
    }
  };

  public ArrayList<OrderCountItemDetails> getData ()
  {
    return data;
  }

  public void setData (ArrayList<OrderCountItemDetails> data)
  {
    this.data = data;
  }

  public String getMessage ()
  {
    return message;
  }

  public void setMessage (String message)
  {
    this.message = message;
  }

}
