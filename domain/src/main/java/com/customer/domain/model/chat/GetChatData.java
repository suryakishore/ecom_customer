package com.customer.domain.model.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GetChatData  {
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
  private int custProType;

  public GetChatData() {
  }

  public GetChatData(String _id, int type, int contentType, String content, String fromID,
      String bid, String targetId) {
    this._id = _id;
    this.type = type;
    this.contentType = contentType;
    this.content = content;
    this.fromID = fromID;
    this.bid = bid;
    this.targetId = targetId;
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

  public int getCustProType() {
    return custProType;
  }

  public void setCustProType(int custProType) {
    this.custProType = custProType;
  }

  @Override
  public String toString() {
    return "GetChatData{" +
        "_id='" + _id + '\'' +
        ", type=" + type +
        ", contentType=" + contentType +
        ", content='" + content + '\'' +
        ", fromID='" + fromID + '\'' +
        ", bid='" + bid + '\'' +
        ", targetId='" + targetId + '\'' +
        ", custProType=" + custProType +
        '}';
  }
}
