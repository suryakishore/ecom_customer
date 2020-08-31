package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class BrandDetails implements ValidItem, Parcelable {

  @SerializedName("data")
  @Expose
  private ArrayList<ResponseDetails> data;

  @SerializedName("penCount")
  @Expose
  private String penCount;

  @SerializedName("message")
  @Expose
  private String message;

  @SerializedName("type")
  @Expose
  private int type;

  public BrandDetails(ArrayList<ResponseDetails> data, String penCount, String message, int type) {
    this.data = data;
    this.penCount = penCount;
    this.message = message;
    this.type = type;
  }

  protected BrandDetails(Parcel in) {
    penCount = in.readString();
    message = in.readString();
    type = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(penCount);
    dest.writeString(message);
    dest.writeInt(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<BrandDetails> CREATOR = new Creator<BrandDetails>() {
    @Override
    public BrandDetails createFromParcel(Parcel in) {
      return new BrandDetails(in);
    }

    @Override
    public BrandDetails[] newArray(int size) {
      return new BrandDetails[size];
    }
  };

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public ArrayList<ResponseDetails> getData() {
    return data;
  }

  public void setData(ArrayList<ResponseDetails> data) {
    this.data = data;
  }

  public String getPenCount() {
    return penCount;
  }

  public void setPenCount(String penCount) {
    this.penCount = penCount;
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
