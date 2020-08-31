package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class BannerDetails implements ValidItem , Parcelable {

  @SerializedName("data")
  @Expose
  private ArrayList<BannerImageDetails> data;

  @SerializedName("message")
  @Expose
  private String message;

  @SerializedName("type")
  @Expose
  private int type;

  public BannerDetails(ArrayList<BannerImageDetails> data, String message, int type) {
    this.data = data;
    this.message = message;
    this.type = type;
  }

  protected BannerDetails(Parcel in) {
    message = in.readString();
    type = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
    dest.writeInt(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<BannerDetails> CREATOR = new Creator<BannerDetails>() {
    @Override
    public BannerDetails createFromParcel(Parcel in) {
      return new BannerDetails(in);
    }

    @Override
    public BannerDetails[] newArray(int size) {
      return new BannerDetails[size];
    }
  };

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public ArrayList<BannerImageDetails> getData() {
    return data;
  }

  public void setData(ArrayList<BannerImageDetails> data) {
    this.data = data;
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
