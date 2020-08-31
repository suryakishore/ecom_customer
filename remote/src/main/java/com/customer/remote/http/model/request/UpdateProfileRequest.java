package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest implements Parcelable {

  @SerializedName("firstName")
  @Expose
  private String firstName;

  @SerializedName("lastName")
  @Expose
  private String lastName;

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("profilePic")
  @Expose
  private String profilePic;

  public UpdateProfileRequest(String firstName, String lastName, String countryCode,
      String mobile, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.mobile = mobile;
    this.email = email;
  }

  public UpdateProfileRequest(String profilePic) {
    this.profilePic = profilePic;
  }

  protected UpdateProfileRequest(Parcel in) {
    firstName = in.readString();
    lastName = in.readString();
    mobile = in.readString();
    email = in.readString();
    profilePic = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(firstName);
    dest.writeString(lastName);
    dest.writeString(mobile);
    dest.writeString(email);
    dest.writeString(profilePic);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UpdateProfileRequest> CREATOR = new Creator<UpdateProfileRequest>() {
    @Override
    public UpdateProfileRequest createFromParcel(Parcel in) {
      return new UpdateProfileRequest(in);
    }

    @Override
    public UpdateProfileRequest[] newArray(int size) {
      return new UpdateProfileRequest[size];
    }
  };

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
