package com.customer.remote.http.model.response.profile;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.signUp.LocationData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ProfileDetails implements ValidItem, Parcelable {

  @SerializedName("googleId")
  @Expose
  private String googleId;

  @SerializedName("lastName")
  @Expose
  private String lastName;

  @SerializedName("zipCode")
  @Expose
  private String zipCode;

  @SerializedName("country")
  @Expose
  private String country;

  @SerializedName("lastLogin")
  @Expose
  private String lastLogin;

  @SerializedName("createdISOdate")
  @Expose
  private String createdISOdate;

  @SerializedName("city")
  @Expose
  private String city;

  @SerializedName("statusLogs")
  @Expose
  private ArrayList<LastStatusLogDetails> statusLogs;

  @SerializedName("loginType")
  @Expose
  private String loginType;

  @SerializedName("mmjCard")
  @Expose
  private MmjCardDetails mmjCard;

  @SerializedName("mobileVerified")
  @Expose
  private String mobileVerified;

  @SerializedName("mqttTopic")
  @Expose
  private String mqttTopic;

  @SerializedName("cityId")
  @Expose
  private String cityId;

  @SerializedName("password")
  @Expose
  private String password;

  @SerializedName("countryCode")
  @Expose
  private String countryCode;

  @SerializedName("userTypeText")
  @Expose
  private String userTypeText;

  @SerializedName("socialMediaId")
  @Expose
  private String socialMediaId;

  @SerializedName("lastStatusLog")
  @Expose
  private LastStatusLogDetails lastStatusLog;

  @SerializedName("termsAndCondition")
  @Expose
  private String termsAndCondition;

  @SerializedName("guestToken")
  @Expose
  private String guestToken;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("seqId")
  @Expose
  private String seqId;

  @SerializedName("wallet")
  @Expose
  private WalletDetails wallet;

  @SerializedName("registrationDateTimeStamp")
  @Expose
  private String registrationDateTimeStamp;

  @SerializedName("facebookId")
  @Expose
  private String facebookId;

  @SerializedName("profilePic")
  @Expose
  private String profilePic;

  @SerializedName("createdTimestamp")
  @Expose
  private String createdTimestamp;

  @SerializedName("registeredFromCity")
  @Expose
  private String registeredFromCity;

  @SerializedName("mobile")
  @Expose
  private String mobile;

  @SerializedName("coordinates")
  @Expose
  private LocationData coordinates;

  @SerializedName("dateOfBirth")
  @Expose
  private String dateOfBirth;

  @SerializedName("fcmTopic")
  @Expose
  private String fcmTopic;

  @SerializedName("firstName")
  @Expose
  private String firstName;

  @SerializedName("createdDate")
  @Expose
  private String createdDate;

  @SerializedName("statusMsg")
  @Expose
  private String statusMsg;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("mobileDevices")
  @Expose
  private MobileDevicesDetails mobileDevices;

  @SerializedName("_id")
  @Expose
  private String _id;

  @SerializedName("userType")
  @Expose
  private String userType;

  @SerializedName("region")
  @Expose
  private String region;

  @SerializedName("zendeskId")
  @Expose
  private String zendeskId;

  @SerializedName("registrationDateIso")
  @Expose
  private String registrationDateIso;

  @SerializedName("status")
  @Expose
  private String status;

  public ProfileDetails(String googleId, String lastName, String zipCode, String country,
      String lastLogin, String createdISOdate, String city,
      ArrayList<LastStatusLogDetails> statusLogs, String loginType,
      MmjCardDetails mmjCard, String mobileVerified, String mqttTopic, String cityId,
      String password, String countryCode, String userTypeText, String socialMediaId,
      LastStatusLogDetails lastStatusLog, String termsAndCondition, String guestToken,
      String email, String seqId,
      WalletDetails wallet, String registrationDateTimeStamp, String facebookId,
      String profilePic, String createdTimestamp, String registeredFromCity, String mobile,
      LocationData coordinates, String dateOfBirth, String fcmTopic, String firstName,
      String createdDate, String statusMsg, String name,
      MobileDevicesDetails mobileDevices, String _id, String userType, String region,
      String zendeskId, String registrationDateIso, String status) {
    this.googleId = googleId;
    this.lastName = lastName;
    this.zipCode = zipCode;
    this.country = country;
    this.lastLogin = lastLogin;
    this.createdISOdate = createdISOdate;
    this.city = city;
    this.statusLogs = statusLogs;
    this.loginType = loginType;
    this.mmjCard = mmjCard;
    this.mobileVerified = mobileVerified;
    this.mqttTopic = mqttTopic;
    this.cityId = cityId;
    this.password = password;
    this.countryCode = countryCode;
    this.userTypeText = userTypeText;
    this.socialMediaId = socialMediaId;
    this.lastStatusLog = lastStatusLog;
    this.termsAndCondition = termsAndCondition;
    this.guestToken = guestToken;
    this.email = email;
    this.seqId = seqId;
    this.wallet = wallet;
    this.registrationDateTimeStamp = registrationDateTimeStamp;
    this.facebookId = facebookId;
    this.profilePic = profilePic;
    this.createdTimestamp = createdTimestamp;
    this.registeredFromCity = registeredFromCity;
    this.mobile = mobile;
    this.coordinates = coordinates;
    this.dateOfBirth = dateOfBirth;
    this.fcmTopic = fcmTopic;
    this.firstName = firstName;
    this.createdDate = createdDate;
    this.statusMsg = statusMsg;
    this.name = name;
    this.mobileDevices = mobileDevices;
    this._id = _id;
    this.userType = userType;
    this.region = region;
    this.zendeskId = zendeskId;
    this.registrationDateIso = registrationDateIso;
    this.status = status;
  }

  protected ProfileDetails(Parcel in) {
    googleId = in.readString();
    lastName = in.readString();
    zipCode = in.readString();
    country = in.readString();
    lastLogin = in.readString();
    createdISOdate = in.readString();
    city = in.readString();
    statusLogs = in.createTypedArrayList(LastStatusLogDetails.CREATOR);
    loginType = in.readString();
    mmjCard = in.readParcelable(MmjCardDetails.class.getClassLoader());
    mobileVerified = in.readString();
    mqttTopic = in.readString();
    cityId = in.readString();
    password = in.readString();
    countryCode = in.readString();
    userTypeText = in.readString();
    socialMediaId = in.readString();
    lastStatusLog = in.readParcelable(LastStatusLogDetails.class.getClassLoader());
    termsAndCondition = in.readString();
    guestToken = in.readString();
    email = in.readString();
    seqId = in.readString();
    registrationDateTimeStamp = in.readString();
    facebookId = in.readString();
    profilePic = in.readString();
    createdTimestamp = in.readString();
    registeredFromCity = in.readString();
    mobile = in.readString();
    dateOfBirth = in.readString();
    fcmTopic = in.readString();
    firstName = in.readString();
    createdDate = in.readString();
    statusMsg = in.readString();
    name = in.readString();
    mobileDevices = in.readParcelable(MobileDevicesDetails.class.getClassLoader());
    _id = in.readString();
    userType = in.readString();
    region = in.readString();
    zendeskId = in.readString();
    registrationDateIso = in.readString();
    status = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(googleId);
    dest.writeString(lastName);
    dest.writeString(zipCode);
    dest.writeString(country);
    dest.writeString(lastLogin);
    dest.writeString(createdISOdate);
    dest.writeString(city);
    dest.writeTypedList(statusLogs);
    dest.writeString(loginType);
    dest.writeParcelable(mmjCard, flags);
    dest.writeString(mobileVerified);
    dest.writeString(mqttTopic);
    dest.writeString(cityId);
    dest.writeString(password);
    dest.writeString(countryCode);
    dest.writeString(userTypeText);
    dest.writeString(socialMediaId);
    dest.writeParcelable(lastStatusLog, flags);
    dest.writeString(termsAndCondition);
    dest.writeString(guestToken);
    dest.writeString(email);
    dest.writeString(seqId);
    dest.writeString(registrationDateTimeStamp);
    dest.writeString(facebookId);
    dest.writeString(profilePic);
    dest.writeString(createdTimestamp);
    dest.writeString(registeredFromCity);
    dest.writeString(mobile);
    dest.writeString(dateOfBirth);
    dest.writeString(fcmTopic);
    dest.writeString(firstName);
    dest.writeString(createdDate);
    dest.writeString(statusMsg);
    dest.writeString(name);
    dest.writeParcelable(mobileDevices, flags);
    dest.writeString(_id);
    dest.writeString(userType);
    dest.writeString(region);
    dest.writeString(zendeskId);
    dest.writeString(registrationDateIso);
    dest.writeString(status);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ProfileDetails> CREATOR = new Creator<ProfileDetails>() {
    @Override
    public ProfileDetails createFromParcel(Parcel in) {
      return new ProfileDetails(in);
    }

    @Override
    public ProfileDetails[] newArray(int size) {
      return new ProfileDetails[size];
    }
  };

  public String getGoogleId() {
    return googleId;
  }

  public void setGoogleId(String googleId) {
    this.googleId = googleId;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(String lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getCreatedISOdate() {
    return createdISOdate;
  }

  public void setCreatedISOdate(String createdISOdate) {
    this.createdISOdate = createdISOdate;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public ArrayList<LastStatusLogDetails> getStatusLogs() {
    return statusLogs;
  }

  public void setStatusLogs(
      ArrayList<LastStatusLogDetails> statusLogs) {
    this.statusLogs = statusLogs;
  }

  public String getLoginType() {
    return loginType;
  }

  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }

  public MmjCardDetails getMmjCard() {
    return mmjCard;
  }

  public void setMmjCard(MmjCardDetails mmjCard) {
    this.mmjCard = mmjCard;
  }

  public String getMobileVerified() {
    return mobileVerified;
  }

  public void setMobileVerified(String mobileVerified) {
    this.mobileVerified = mobileVerified;
  }

  public String getMqttTopic() {
    return mqttTopic;
  }

  public void setMqttTopic(String mqttTopic) {
    this.mqttTopic = mqttTopic;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getUserTypeText() {
    return userTypeText;
  }

  public void setUserTypeText(String userTypeText) {
    this.userTypeText = userTypeText;
  }

  public String getSocialMediaId() {
    return socialMediaId;
  }

  public void setSocialMediaId(String socialMediaId) {
    this.socialMediaId = socialMediaId;
  }

  public LastStatusLogDetails getLastStatusLog() {
    return lastStatusLog;
  }

  public void setLastStatusLog(
      LastStatusLogDetails lastStatusLog) {
    this.lastStatusLog = lastStatusLog;
  }

  public String getTermsAndCondition() {
    return termsAndCondition;
  }

  public void setTermsAndCondition(String termsAndCondition) {
    this.termsAndCondition = termsAndCondition;
  }

  public String getGuestToken() {
    return guestToken;
  }

  public void setGuestToken(String guestToken) {
    this.guestToken = guestToken;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSeqId() {
    return seqId;
  }

  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }

  public WalletDetails getWallet() {
    return wallet;
  }

  public void setWallet(WalletDetails wallet) {
    this.wallet = wallet;
  }

  public String getRegistrationDateTimeStamp() {
    return registrationDateTimeStamp;
  }

  public void setRegistrationDateTimeStamp(String registrationDateTimeStamp) {
    this.registrationDateTimeStamp = registrationDateTimeStamp;
  }

  public String getFacebookId() {
    return facebookId;
  }

  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public String getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(String createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public String getRegisteredFromCity() {
    return registeredFromCity;
  }

  public void setRegisteredFromCity(String registeredFromCity) {
    this.registeredFromCity = registeredFromCity;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public LocationData getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(LocationData coordinates) {
    this.coordinates = coordinates;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getFcmTopic() {
    return fcmTopic;
  }

  public void setFcmTopic(String fcmTopic) {
    this.fcmTopic = fcmTopic;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MobileDevicesDetails getMobileDevices() {
    return mobileDevices;
  }

  public void setMobileDevices(
      MobileDevicesDetails mobileDevices) {
    this.mobileDevices = mobileDevices;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getZendeskId() {
    return zendeskId;
  }

  public void setZendeskId(String zendeskId) {
    this.zendeskId = zendeskId;
  }

  public String getRegistrationDateIso() {
    return registrationDateIso;
  }

  public void setRegistrationDateIso(String registrationDateIso) {
    this.registrationDateIso = registrationDateIso;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
