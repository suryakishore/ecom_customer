package com.customer.remote.http.model.response.guestSignIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestSignInResponse implements ValidItem, Parcelable {

  @SerializedName("data")
  @Expose
  private GuestSignInDetailsData data;

  @SerializedName("message")
  @Expose
  private String message;

  protected GuestSignInResponse(Parcel in) {
    data = in.readParcelable(GuestSignInDetailsData.class.getClassLoader());
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(data, flags);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GuestSignInResponse> CREATOR = new Creator<GuestSignInResponse>() {
    @Override
    public GuestSignInResponse createFromParcel(Parcel in) {
      return new GuestSignInResponse(in);
    }

    @Override
    public GuestSignInResponse[] newArray(int size) {
      return new GuestSignInResponse[size];
    }
  };

  public GuestSignInDetailsData getData() {
    return data;
  }

  public void setData(GuestSignInDetailsData data) {
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
