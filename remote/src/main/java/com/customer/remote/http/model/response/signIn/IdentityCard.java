package com.customer.remote.http.model.response.signIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentityCard implements ValidItem, Parcelable {
  @SerializedName("verified")
  @Expose
  private String verified;

  @SerializedName("url")
  @Expose
  private String url;

  public IdentityCard(String verified, String url) {
    this.verified = verified;
    this.url = url;
  }

  protected IdentityCard(Parcel in) {
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

  public static final Creator<IdentityCard> CREATOR = new Creator<IdentityCard>() {
    @Override
    public IdentityCard createFromParcel(Parcel in) {
      return new IdentityCard(in);
    }

    @Override
    public IdentityCard[] newArray(int size) {
      return new IdentityCard[size];
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

  @Override
  public Boolean isValid() {
    return true;
  }
}
