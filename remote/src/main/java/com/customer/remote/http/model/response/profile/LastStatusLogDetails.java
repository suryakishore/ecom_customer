package com.customer.remote.http.model.response.profile;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastStatusLogDetails implements Parcelable {
  @SerializedName("actionByUserId")
  @Expose
  private String actionByUserId;

  @SerializedName("actionByUserType")
  @Expose
  private String actionByUserType;

  @SerializedName("action")
  @Expose
  private String action;

  @SerializedName("status")
  @Expose
  private String status;

  @SerializedName("timestamp")
  @Expose
  private String timestamp;

  public LastStatusLogDetails(String actionByUserId, String actionByUserType, String action,
      String status, String timestamp) {
    this.actionByUserId = actionByUserId;
    this.actionByUserType = actionByUserType;
    this.action = action;
    this.status = status;
    this.timestamp = timestamp;
  }

  protected LastStatusLogDetails(Parcel in) {
    actionByUserId = in.readString();
    actionByUserType = in.readString();
    action = in.readString();
    status = in.readString();
    timestamp = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(actionByUserId);
    dest.writeString(actionByUserType);
    dest.writeString(action);
    dest.writeString(status);
    dest.writeString(timestamp);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<LastStatusLogDetails> CREATOR = new Creator<LastStatusLogDetails>() {
    @Override
    public LastStatusLogDetails createFromParcel(Parcel in) {
      return new LastStatusLogDetails(in);
    }

    @Override
    public LastStatusLogDetails[] newArray(int size) {
      return new LastStatusLogDetails[size];
    }
  };

  public String getActionByUserId() {
    return actionByUserId;
  }

  public void setActionByUserId(String actionByUserId) {
    this.actionByUserId = actionByUserId;
  }

  public String getActionByUserType() {
    return actionByUserType;
  }

  public void setActionByUserType(String actionByUserType) {
    this.actionByUserType = actionByUserType;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}
