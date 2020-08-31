package com.customer.remote.http.model.request;

import static com.customer.remote.http.RemoteConstants.LIMIT;
import static com.customer.remote.http.RemoteConstants.ORDER_BY;
import static com.customer.remote.http.RemoteConstants.ORDER_ID;
import static com.customer.remote.http.RemoteConstants.ORDER_TIME;
import static com.customer.remote.http.RemoteConstants.ORDER_TYPE;
import static com.customer.remote.http.RemoteConstants.SKIP;
import static com.customer.remote.http.RemoteConstants.STATUS;
import static com.customer.remote.http.RemoteConstants.STORE_TYPE;
import static com.customer.remote.http.RemoteConstants.TYPE;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class CancelOrderRequest implements Parcelable {

  @Expose
  @SerializedName("type")
  private String type;
  @Expose
  @SerializedName("orderId")
  private String orderId;
  @Expose
  @SerializedName("reason")
  private String reason;
  @Expose
  @SerializedName("comment")
  private String comment;

  public CancelOrderRequest(String type, String orderId, String reason, String comment) {
    this.type = type;
    this.orderId = orderId;
    this.reason = reason;
    this.comment = comment;
  }

  protected CancelOrderRequest(Parcel in) {
    type = in.readString();
    orderId = in.readString();
    reason = in.readString();
    comment = in.readString();
  }

  public static final Creator<CancelOrderRequest> CREATOR = new Creator<CancelOrderRequest>() {
    @Override
    public CancelOrderRequest createFromParcel(Parcel in) {
      return new CancelOrderRequest(in);
    }

    @Override
    public CancelOrderRequest[] newArray(int size) {
      return new CancelOrderRequest[size];
    }
  };

  public  ArrayMap<String, Object> getQuery() {
   /* HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put(TYPE, type);
    hashMap.put(ORDER_ID, orderId);
    hashMap.put("reason", reason);
    hashMap.put("comment", comment);*/
    ArrayMap<String, Object> userIdMapper = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      userIdMapper = new ArrayMap<>();
      userIdMapper.put(TYPE, type);
      userIdMapper.put(ORDER_ID, orderId);
      userIdMapper.put("reason", reason);
      userIdMapper.put("comment", comment);    }
    return userIdMapper;
  }
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

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(type);
    dest.writeString(orderId);
    dest.writeString(reason);
    dest.writeString(comment);
  }
}
