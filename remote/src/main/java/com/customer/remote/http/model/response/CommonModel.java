package com.customer.remote.http.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonModel implements ValidItem, Parcelable {

  @SerializedName("message")
  @Expose
  private String message;

  public CommonModel(String message) {
    this.message = message;
  }

  protected CommonModel(Parcel in) {
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CommonModel> CREATOR = new Creator<CommonModel>() {
    @Override
    public CommonModel createFromParcel(Parcel in) {
      return new CommonModel(in);
    }

    @Override
    public CommonModel[] newArray(int size) {
      return new CommonModel[size];
    }
  };

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
