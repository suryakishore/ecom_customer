package com.customer.domain.model.profile;

import com.customer.domain.model.signup.LocationData;
import java.util.ArrayList;

public class ProfileData {

  private String googleId;

  private String lastName;

  private String zipCode;

  private String country;

  private String lastLogin;

  private String createdISOdate;

  private String city;

  private ArrayList<LastStatusLogData> statusLogs;

  private String loginType;

  private MmjCardData mmjCard;

  private String mobileVerified;

  private String mqttTopic;

  private String cityId;

  private String password;

  private String countryCode;

  private String userTypeText;

  private String socialMediaId;

  private LastStatusLogData lastStatusLog;

  private String termsAndCondition;

  private String guestToken;

  private String email;

  private String seqId;

  private WalletData wallet;

  private String registrationDateTimeStamp;

  private String facebookId;

  private String profilePic;

  private String createdTimestamp;

  private String registeredFromCity;

  private String mobile;

  private LocationData coordinates;

  private String dateOfBirth;

  private String fcmTopic;

  private String firstName;

  private String createdDate;

  private String statusMsg;

  private String name;

  private MobileDevicesData mobileDevices;

  private String mId;

  private String userType;

  private String region;

  private String zendeskId;

  private String registrationDateIso;

  private String status;

  public ProfileData(String googleId, String lastName, String zipCode, String country,
      String lastLogin, String createdISOdate, String city,
      ArrayList<LastStatusLogData> statusLogs, String loginType,
      MmjCardData mmjCard, String mobileVerified, String mqttTopic, String cityId,
      String password, String countryCode, String userTypeText, String socialMediaId,
      LastStatusLogData lastStatusLog, String termsAndCondition, String guestToken,
      String email, String seqId, WalletData wallet, String registrationDateTimeStamp,
      String facebookId, String profilePic, String createdTimestamp,
      String registeredFromCity, String mobile,
      LocationData coordinates, String dateOfBirth, String fcmTopic, String firstName,
      String createdDate, String statusMsg, String name,
      MobileDevicesData mobileDevices, String id, String userType, String region,
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
    this.mId = id;
    this.userType = userType;
    this.region = region;
    this.zendeskId = zendeskId;
    this.registrationDateIso = registrationDateIso;
    this.status = status;
  }

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

  public ArrayList<LastStatusLogData> getStatusLogs() {
    return statusLogs;
  }

  public void setStatusLogs(
      ArrayList<LastStatusLogData> statusLogs) {
    this.statusLogs = statusLogs;
  }

  public String getLoginType() {
    return loginType;
  }

  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }

  public MmjCardData getMmjCard() {
    return mmjCard;
  }

  public void setMmjCard(MmjCardData mmjCard) {
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

  public LastStatusLogData getLastStatusLog() {
    return lastStatusLog;
  }

  public void setLastStatusLog(LastStatusLogData lastStatusLog) {
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

  public WalletData getWallet() {
    return wallet;
  }

  public void setWallet(WalletData wallet) {
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

  public MobileDevicesData getMobileDevices() {
    return mobileDevices;
  }

  public void setMobileDevices(MobileDevicesData mobileDevices) {
    this.mobileDevices = mobileDevices;
  }

  public String getId() {
    return mId;
  }

  public void setId(String id) {
    this.mId = id;
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
}
