package com.customer.remote.http.model.response.chat;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChatItemDetails implements Parcelable {
  public static final Creator<GetChatItemDetails> CREATOR = new Creator<GetChatItemDetails>() {
    @Override
    public GetChatItemDetails createFromParcel(Parcel in) {
      return new GetChatItemDetails(in);
    }

    @Override
    public GetChatItemDetails[] newArray(int size) {
      return new GetChatItemDetails[size];
    }
  };
  @SerializedName("_id")
  @Expose
  private String _id;
  @SerializedName("type")
  @Expose
  private int type;
  @SerializedName("contentType")
  @Expose
  private int contentType;
  @SerializedName("content")
  @Expose
  private String content;
  @SerializedName("fromID")
  @Expose
  private String fromID;
  @SerializedName("bid")
  @Expose
  private String bid;
  @SerializedName("targetId")
  @Expose
  private String targetId;

  protected GetChatItemDetails(Parcel in) {
    _id = in.readString();
    type = in.readInt();
    contentType = in.readInt();
    content = in.readString();
    fromID = in.readString();
    bid = in.readString();
    targetId = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(_id);
    dest.writeInt(type);
    dest.writeInt(contentType);
    dest.writeString(content);
    dest.writeString(fromID);
    dest.writeString(bid);
    dest.writeString(targetId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getContentType() {
    return contentType;
  }

  public void setContentType(int contentType) {
    this.contentType = contentType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getFromID() {
    return fromID;
  }

  public void setFromID(String fromID) {
    this.fromID = fromID;
  }

  public String getBid() {
    return bid;
  }

  public void setBid(String bid) {
    this.bid = bid;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }
}
