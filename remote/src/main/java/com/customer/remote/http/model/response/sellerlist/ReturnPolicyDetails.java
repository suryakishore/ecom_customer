package com.customer.remote.http.model.response.sellerlist;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnPolicyDetails implements Parcelable {

  @SerializedName("isReturn")
  @Expose
  private boolean isReturn;

  @SerializedName("noofdays")
  @Expose
  private int noofdays;

  public ReturnPolicyDetails(boolean isReturn, int noofdays) {
    this.isReturn = isReturn;
    this.noofdays = noofdays;
  }

  protected ReturnPolicyDetails(Parcel in) {
    isReturn = in.readByte() != 0;
    noofdays = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte) (isReturn ? 1 : 0));
    dest.writeInt(noofdays);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ReturnPolicyDetails> CREATOR = new Creator<ReturnPolicyDetails>() {
    @Override
    public ReturnPolicyDetails createFromParcel(Parcel in) {
      return new ReturnPolicyDetails(in);
    }

    @Override
    public ReturnPolicyDetails[] newArray(int size) {
      return new ReturnPolicyDetails[size];
    }
  };

  public boolean isReturn() {
    return isReturn;
  }

  public void setReturn(boolean aReturn) {
    isReturn = aReturn;
  }

  public int getNoofdays() {
    return noofdays;
  }

  public void setNoofdays(int noofdays) {
    this.noofdays = noofdays;
  }
}
