package com.customer.remote.http.model.response.guestSignIn;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreCategoryIdData implements Parcelable {

  @SerializedName("storeCategoryId")
  @Expose
  private String storeCategoryId;

  @SerializedName("storeCategoryName")
  @Expose
  private String storeCategoryName;

  public StoreCategoryIdData(String storeCategoryId, String storeCategoryName) {
    this.storeCategoryId = storeCategoryId;
    this.storeCategoryName = storeCategoryName;
  }

  protected StoreCategoryIdData(Parcel in) {
    storeCategoryId = in.readString();
    storeCategoryName = in.readString();
  }

  public static final Creator<StoreCategoryIdData> CREATOR = new Creator<StoreCategoryIdData>() {
    @Override
    public StoreCategoryIdData createFromParcel(Parcel in) {
      return new StoreCategoryIdData(in);
    }

    @Override
    public StoreCategoryIdData[] newArray(int size) {
      return new StoreCategoryIdData[size];
    }
  };

  public String getStoreCategoryId() {
    return storeCategoryId;
  }

  public void setStoreCategoryId(String storeCategoryId) {
    this.storeCategoryId = storeCategoryId;
  }

  public String getStoreCategoryName() {
    return storeCategoryName;
  }

  public void setStoreCategoryName(String storeCategoryName) {
    this.storeCategoryName = storeCategoryName;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(storeCategoryId);
    dest.writeString(storeCategoryName);
  }
}
