package com.customer.remote.http.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaoCodeResponseDetails implements Parcelable {

  @SerializedName("parentProductId")
  @Expose
  private String parentProductId;

  @SerializedName("childProductId")
  @Expose
  private String childProductId;

  public BaoCodeResponseDetails(String parentProductId, String childProductId) {
    this.parentProductId = parentProductId;
    this.childProductId = childProductId;
  }

  protected BaoCodeResponseDetails(Parcel in) {
    parentProductId = in.readString();
    childProductId = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(parentProductId);
    dest.writeString(childProductId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<BaoCodeResponseDetails> CREATOR =
      new Creator<BaoCodeResponseDetails>() {
        @Override
        public BaoCodeResponseDetails createFromParcel(Parcel in) {
          return new BaoCodeResponseDetails(in);
        }

        @Override
        public BaoCodeResponseDetails[] newArray(int size) {
          return new BaoCodeResponseDetails[size];
        }
      };

  public String getParentProductId() {
    return parentProductId;
  }

  public void setParentProductId(String parentProductId) {
    this.parentProductId = parentProductId;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }
}
