package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class RecentViewDetails implements ValidItem, Parcelable {

  @SerializedName("penCount")
  @Expose
  private String penCount;

  @SerializedName("data")
  @Expose
  private ArrayList<RecentDetails> data;

  @SerializedName("type")
  @Expose
  private int type;

  @SerializedName("message")
  @Expose
  private String message;

  public RecentViewDetails(String penCount, ArrayList<RecentDetails> data, int type,
      String message) {
    this.penCount = penCount;
    this.data = data;
    this.type = type;
    this.message = message;
  }

  protected RecentViewDetails(Parcel in) {
    penCount = in.readString();
    data = in.createTypedArrayList(RecentDetails.CREATOR);
    type = in.readInt();
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(penCount);
    dest.writeTypedList(data);
    dest.writeInt(type);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<RecentViewDetails> CREATOR = new Creator<RecentViewDetails>() {
    @Override
    public RecentViewDetails createFromParcel(Parcel in) {
      return new RecentViewDetails(in);
    }

    @Override
    public RecentViewDetails[] newArray(int size) {
      return new RecentViewDetails[size];
    }
  };

  public String getPenCount() {
    return penCount;
  }

  public void setPenCount(String penCount) {
    this.penCount = penCount;
  }

  public ArrayList<RecentDetails> getData() {
    return data;
  }

  public void setData(ArrayList<RecentDetails> data) {
    this.data = data;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
