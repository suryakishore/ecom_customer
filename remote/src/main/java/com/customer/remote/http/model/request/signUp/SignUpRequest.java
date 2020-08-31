package com.customer.remote.http.model.request.signUp;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest implements Parcelable {
  public static final Creator<SignUpRequest> CREATOR = new Creator<SignUpRequest>() {
    @Override
    public SignUpRequest createFromParcel(Parcel in) {
      return new SignUpRequest(in);
    }

    @Override
    public SignUpRequest[] newArray(int size) {
      return new SignUpRequest[size];
    }
  };
  @SerializedName("deviceId")
  @Expose
  private String deviceId;
  @SerializedName("mobile")
  @Expose
  private String mobile;
  @SerializedName("countryCode")
  @Expose
  private String countryCode;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("password")
  @Expose
  private String password;
  @SerializedName("deviceType")
  @Expose
  private int deviceType;
  @SerializedName("signUpType")
  @Expose
  private int signUpType;
  @SerializedName("userType")
  @Expose
  private int userType;
  @SerializedName("customerType")
  @Expose
  private int customerType;
  @SerializedName("termsAndCond")
  @Expose
  private int termsAndCond;
  @SerializedName("ipAddress")
  @Expose
  private String ipAddress;
  @SerializedName("firstName")
  @Expose
  private String firstName;
  @SerializedName("lastName")
  @Expose
  private String lastName;
  @SerializedName("profilePicture")
  @Expose
  private String profilePicture;
  @SerializedName("deviceModel")
  @Expose
  private String deviceModel;
  @SerializedName("deviceMake")
  @Expose
  private String deviceMake;
  @SerializedName("deviceTime")
  @Expose
  private String deviceTime;
  @SerializedName("deviceOsVersion")
  @Expose
  private String deviceOsVersion;
  @SerializedName("latitude")
  @Expose
  private String latitude;
  @SerializedName("longitude")
  @Expose
  private String longitude;
  @SerializedName("appVersion")
  @Expose
  private String appVersion;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("country")
  @Expose
  private String country;
  @SerializedName("state")
  @Expose
  private String state;

  public SignUpRequest(String deviceId, String mobile, String countryCode, String email,
      String password, int deviceType, int signUpType, int userType, int customerType,
      int termsAndCond,
      String ipAddress, String firstName, String lastName, String profilePicture,
      String deviceModel, String deviceMake, String deviceTime,
      String browserName, String deviceOsVersion, String latitude, String longitude,
      String appVersion, String city, String country, String state) {
    this.deviceId = deviceId;
    this.mobile = mobile;
    this.countryCode = countryCode;
    this.email = email;
    this.password = password;
    this.deviceType = deviceType;
    this.signUpType = signUpType;
    this.userType = userType;
    this.customerType = customerType;
    this.termsAndCond = termsAndCond;
    this.ipAddress = ipAddress;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profilePicture = profilePicture;
    this.deviceModel = deviceModel;
    this.deviceMake = deviceMake;
    this.deviceTime = deviceTime;
    this.deviceOsVersion = deviceOsVersion;
    this.latitude = latitude;
    this.longitude = longitude;
    this.appVersion = appVersion;
    this.city = city;
    this.state = state;
    this.country = country;
  }

  protected SignUpRequest(Parcel in) {
    deviceId = in.readString();
    mobile = in.readString();
    countryCode = in.readString();
    email = in.readString();
    password = in.readString();
    deviceType = in.readInt();
    signUpType = in.readInt();
    userType = in.readInt();
    customerType = in.readInt();
    termsAndCond = in.readInt();
    ipAddress = in.readString();
    firstName = in.readString();
    lastName = in.readString();
    profilePicture = in.readString();
    deviceModel = in.readString();
    deviceMake = in.readString();
    deviceTime = in.readString();
    deviceOsVersion = in.readString();
    latitude = in.readString();
    longitude = in.readString();
    appVersion = in.readString();
    city = in.readString();
    country = in.readString();
    state = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(deviceId);
    dest.writeString(mobile);
    dest.writeString(countryCode);
    dest.writeString(email);
    dest.writeString(password);
    dest.writeInt(deviceType);
    dest.writeInt(signUpType);
    dest.writeInt(userType);
    dest.writeInt(customerType);
    dest.writeInt(termsAndCond);
    dest.writeString(ipAddress);
    dest.writeString(firstName);
    dest.writeString(lastName);
    dest.writeString(profilePicture);
    dest.writeString(deviceModel);
    dest.writeString(deviceMake);
    dest.writeString(deviceTime);
    dest.writeString(deviceOsVersion);
    dest.writeString(latitude);
    dest.writeString(longitude);
    dest.writeString(appVersion);
    dest.writeString(city);
    dest.writeString(country);
    dest.writeString(state);
  }

  @Override
  public int describeContents() {
    return 0;
  }

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

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(int deviceType) {
    this.deviceType = deviceType;
  }

  public int getSignUpType() {
    return signUpType;
  }

  public void setSignUpType(int signUpType) {
    this.signUpType = signUpType;
  }

  public int getUserType() {
    return userType;
  }

  public void setUserType(int userType) {
    this.userType = userType;
  }

  public int getTermsAndCond() {
    return termsAndCond;
  }

  public void setTermsAndCond(int termsAndCond) {
    this.termsAndCond = termsAndCond;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public int getCustomerType() {
    return customerType;
  }

  public void setCustomerType(int customerType) {
    this.customerType = customerType;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceMake() {
    return deviceMake;
  }

  public void setDeviceMake(String deviceMake) {
    this.deviceMake = deviceMake;
  }

  public String getDeviceTime() {
    return deviceTime;
  }

  public void setDeviceTime(String deviceTime) {
    this.deviceTime = deviceTime;
  }



  public String getDeviceOsVersion() {
    return deviceOsVersion;
  }

  public void setDeviceOsVersion(String deviceOsVersion) {
    this.deviceOsVersion = deviceOsVersion;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
