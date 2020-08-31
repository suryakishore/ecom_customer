package com.customer.remote.http.model.response.profile;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MmjCardDetails implements Parcelable {
  @SerializedName("verified")
  @Expose
  private String verified;

  @SerializedName("url")
  @Expose
  private String url;

  public MmjCardDetails(String verified, String url) {
    this.verified = verified;
    this.url = url;
  }

  protected MmjCardDetails(Parcel in) {
    verified = in.readString();
    url = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(verified);
    dest.writeString(url);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<MmjCardDetails> CREATOR = new Creator<MmjCardDetails>() {
    @Override
    public MmjCardDetails createFromParcel(Parcel in) {
      return new MmjCardDetails(in);
    }

    @Override
    public MmjCardDetails[] newArray(int size) {
      return new MmjCardDetails[size];
    }
  };

  public String getVerified() {
    return verified;
  }

  public void setVerified(String verified) {
    this.verified = verified;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
