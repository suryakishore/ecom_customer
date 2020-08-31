package com.customer.remote.http.model.response.generatetoken;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateTokenItemDetails implements Parcelable {
  @SerializedName("accessExpireAt")
  @Expose
  private String accessExpireAt;
  @SerializedName("accessToken")
  @Expose
  private String accessToken;

  public GenerateTokenItemDetails(String accessExpireAt, String accessToken) {
    this.accessExpireAt = accessExpireAt;
    this.accessToken = accessToken;
  }

  protected GenerateTokenItemDetails(Parcel in) {
    accessExpireAt = in.readString();
    accessToken = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(accessExpireAt);
    dest.writeString(accessToken);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GenerateTokenItemDetails> CREATOR =
      new Creator<GenerateTokenItemDetails>() {
        @Override
        public GenerateTokenItemDetails createFromParcel(Parcel in) {
          return new GenerateTokenItemDetails(in);
        }

        @Override
        public GenerateTokenItemDetails[] newArray(int size) {
          return new GenerateTokenItemDetails[size];
        }
      };

  public String getAccessExpireAt() {
    return accessExpireAt;
  }

  public void setAccessExpireAt(String accessExpireAt) {
    this.accessExpireAt = accessExpireAt;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
