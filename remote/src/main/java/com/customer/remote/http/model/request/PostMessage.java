package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostMessage implements Parcelable  {

  @SerializedName("type")
  @Expose
  private int type;
  @SerializedName("contentType")
  @Expose
  private int contentType;
  @SerializedName("timestamp")
  @Expose
  private long timestamp;
  @SerializedName("content")
  @Expose
  private String content;
  @SerializedName("fromID")
  @Expose
  private String fromID;
  @SerializedName("storeOrderId")
  @Expose
  private String storeOrderId;
  @SerializedName("targetId")
  @Expose
  private String targetId;
  @SerializedName("fromCustomer")
  @Expose
  private int fromCustomer;

  public PostMessage(int type, int contentType,long timestamp, String content, String fromID, String bid,
      String targetId, int fromCustomer) {
    this.type = type;
    this.contentType=contentType;
    this.timestamp = timestamp;
    this.content = content;
    this.fromID = fromID;
    this.storeOrderId = bid;
    this.targetId = targetId;
    this.fromCustomer = fromCustomer;
  }

  protected PostMessage(Parcel in) {
    type = in.readInt();
    contentType = in.readInt();
    timestamp = in.readLong();
    content = in.readString();
    fromID = in.readString();
    storeOrderId = in.readString();
    targetId = in.readString();
    fromCustomer = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(type);
    dest.writeInt(contentType);
    dest.writeLong(timestamp);
    dest.writeString(content);
    dest.writeString(fromID);
    dest.writeString(storeOrderId);
    dest.writeString(targetId);
    dest.writeInt(fromCustomer);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<PostMessage> CREATOR = new Creator<PostMessage>() {
    @Override
    public PostMessage createFromParcel(Parcel in) {
      return new PostMessage(in);
    }

    @Override
    public PostMessage[] newArray(int size) {
      return new PostMessage[size];
    }
  };
}
