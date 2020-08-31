package com.customer.domain.model.generatetoken;

import android.os.Parcel;
import android.os.Parcelable;

public class GenerateTokenItemData implements Parcelable {

  private String accessExpireAt;
  private String accessToken;

  public GenerateTokenItemData(String accessExpireAt, String accessToken) {
    this.accessExpireAt = accessExpireAt;
    this.accessToken = accessToken;
  }

  protected GenerateTokenItemData(Parcel in) {
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

  public static final Creator<GenerateTokenItemData> CREATOR =
      new Creator<GenerateTokenItemData>() {
        @Override
        public GenerateTokenItemData createFromParcel(Parcel in) {
          return new GenerateTokenItemData(in);
        }

        @Override
        public GenerateTokenItemData[] newArray(int size) {
          return new GenerateTokenItemData[size];
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
