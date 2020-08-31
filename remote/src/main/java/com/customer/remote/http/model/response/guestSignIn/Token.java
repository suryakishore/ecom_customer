package com.customer.remote.http.model.response.guestSignIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token implements ValidItem, Parcelable {

  @SerializedName("accessToken")
  @Expose
  private String accessToken;

  @SerializedName("accessExpiry")
  @Expose
  private String accessExpiry;

  @SerializedName("refreshToken")
  @Expose
  private String refreshToken;

  public Token(String accessToken, String accessExpiry, String refreshToken) {
    this.accessToken = accessToken;
    this.accessExpiry = accessExpiry;
    this.refreshToken = refreshToken;
  }

  protected Token(Parcel in) {
    accessToken = in.readString();
    accessExpiry = in.readString();
    refreshToken = in.readString();
  }

  public static final Creator<Token> CREATOR = new Creator<Token>() {
    @Override
    public Token createFromParcel(Parcel in) {
      return new Token(in);
    }

    @Override
    public Token[] newArray(int size) {
      return new Token[size];
    }
  };

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessExpiry() {
    return accessExpiry;
  }

  public void setAccessExpiry(String accessExpiry) {
    this.accessExpiry = accessExpiry;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public Boolean isValid() {
    return true;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(accessToken);
    dest.writeString(accessExpiry);
    dest.writeString(refreshToken);
  }
}
