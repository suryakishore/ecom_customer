package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateTokenRequest implements Parcelable {
  @SerializedName("refToken")
  @Expose
  private String refToken;
  @SerializedName("accessToken")
  @Expose
  private String accessToken;

  public GenerateTokenRequest(String refToken, String accessToken) {
    this.refToken = refToken;
    this.accessToken = accessToken;
  }

  protected GenerateTokenRequest(Parcel in) {
    refToken = in.readString();
    accessToken = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(refToken);
    dest.writeString(accessToken);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GenerateTokenRequest> CREATOR = new Creator<GenerateTokenRequest>() {
    @Override
    public GenerateTokenRequest createFromParcel(Parcel in) {
      return new GenerateTokenRequest(in);
    }

    @Override
    public GenerateTokenRequest[] newArray(int size) {
      return new GenerateTokenRequest[size];
    }
  };

  public String getRefToken() {
    return refToken;
  }

  public void setRefToken(String refToken) {
    this.refToken = refToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
