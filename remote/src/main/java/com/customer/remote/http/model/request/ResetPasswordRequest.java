package com.customer.remote.http.model.request;

import static com.customer.remote.http.RemoteConstants.FORGOT_PASSWORD_TYPE;
import static com.customer.remote.http.RemoteConstants.VALIDATE_PASSWORD_TYPE;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequest implements Parcelable {

  @SerializedName("newPassword")
  @Expose
  private String newPassword;

  @SerializedName("oldPassword")
  @Expose
  private String oldPassword;

  @SerializedName("resetType")
  @Expose
  private int resetType;

  public ResetPasswordRequest(String newPassword, String oldPassword, int resetType) {
    this.newPassword = newPassword;
    this.oldPassword = oldPassword;
    this.resetType = resetType;
  }

  public ResetPasswordRequest(String password, int resetType) {

    if (resetType == FORGOT_PASSWORD_TYPE) {
      this.newPassword = password;
    } else if (resetType == VALIDATE_PASSWORD_TYPE) {
      this.oldPassword = password;
    }
    this.resetType = resetType;
  }

  protected ResetPasswordRequest(Parcel in) {
    newPassword = in.readString();
    oldPassword = in.readString();
    resetType = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(newPassword);
    dest.writeString(oldPassword);
    dest.writeInt(resetType);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ResetPasswordRequest> CREATOR = new Creator<ResetPasswordRequest>() {
    @Override
    public ResetPasswordRequest createFromParcel(Parcel in) {
      return new ResetPasswordRequest(in);
    }

    @Override
    public ResetPasswordRequest[] newArray(int size) {
      return new ResetPasswordRequest[size];
    }
  };

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public int getResetType() {
    return resetType;
  }

  public void setResetType(int resetType) {
    this.resetType = resetType;
  }
}
