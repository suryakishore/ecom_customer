package com.customer.remote.http.model.response.profile;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDetails implements Parcelable {

  @SerializedName("softLimitHit")
  @Expose
  private String softLimitHit;

  @SerializedName("balance")
  @Expose
  private String balance;

  @SerializedName("blocked")
  @Expose
  private String blocked;

  @SerializedName("hardLimitHit")
  @Expose
  private String hardLimitHit;

  @SerializedName("hardLimit")
  @Expose
  private String hardLimit;

  @SerializedName("softLimit")
  @Expose
  private String softLimit;

  public WalletDetails(String softLimitHit, String balance, String blocked,
      String hardLimitHit, String hardLimit, String softLimit) {
    this.softLimitHit = softLimitHit;
    this.balance = balance;
    this.blocked = blocked;
    this.hardLimitHit = hardLimitHit;
    this.hardLimit = hardLimit;
    this.softLimit = softLimit;
  }

  protected WalletDetails(Parcel in) {
    softLimitHit = in.readString();
    balance = in.readString();
    blocked = in.readString();
    hardLimitHit = in.readString();
    hardLimit = in.readString();
    softLimit = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(softLimitHit);
    dest.writeString(balance);
    dest.writeString(blocked);
    dest.writeString(hardLimitHit);
    dest.writeString(hardLimit);
    dest.writeString(softLimit);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<WalletDetails> CREATOR = new Creator<WalletDetails>() {
    @Override
    public WalletDetails createFromParcel(Parcel in) {
      return new WalletDetails(in);
    }

    @Override
    public WalletDetails[] newArray(int size) {
      return new WalletDetails[size];
    }
  };

  public String getSoftLimitHit() {
    return softLimitHit;
  }

  public void setSoftLimitHit(String softLimitHit) {
    this.softLimitHit = softLimitHit;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getBlocked() {
    return blocked;
  }

  public void setBlocked(String blocked) {
    this.blocked = blocked;
  }

  public String getHardLimitHit() {
    return hardLimitHit;
  }

  public void setHardLimitHit(String hardLimitHit) {
    this.hardLimitHit = hardLimitHit;
  }

  public String getHardLimit() {
    return hardLimit;
  }

  public void setHardLimit(String hardLimit) {
    this.hardLimit = hardLimit;
  }

  public String getSoftLimit() {
    return softLimit;
  }

  public void setSoftLimit(String softLimit) {
    this.softLimit = softLimit;
  }
}
