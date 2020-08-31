package com.customer.remote.http.model.response.webview;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebPageDetails implements Parcelable {
  public static final Creator<WebPageDetails> CREATOR = new Creator<WebPageDetails>() {
    @Override
    public WebPageDetails createFromParcel(Parcel in) {
      return new WebPageDetails(in);
    }

    @Override
    public WebPageDetails[] newArray(int size) {
      return new WebPageDetails[size];
    }
  };
  @SerializedName("data")
  @Expose
  private WebPageData data;
  @SerializedName("message")
  @Expose
  private String message;

  protected WebPageDetails(Parcel in) {
    data = in.readParcelable(WebPageData.class.getClassLoader());
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

  public WebPageData getData() {
    return data;
  }

  public void setData(WebPageData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
