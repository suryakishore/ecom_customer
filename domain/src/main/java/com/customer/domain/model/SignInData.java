package com.customer.domain.model;

import android.util.Log;
import com.customer.domain.model.guestsignin.Token;
import com.customer.domain.model.signin.IdentityCard;
import com.customer.domain.model.signup.LocationData;

public class SignInData {

  private String accessToken;

  private String country;

  private String city;

  private String googleMapKeyMqtt;

  private IdentityCard mmjCard;

  private String mobile;

  private String fcmTopic;

  private String mqttTopic;

  private IdentityCard identityCard;

  private String userId;

  private Token token;

  private String countryCode;

  private String name;

  private LocationData location;

  private String region;

  private String email;

  private String status;

  private String requesterId;

  private String otpId;

  private int otpExpiryTime;
  private String expireTime;

  public SignInData(String accessToken, String otpId, int otpExpiryTime) {
    this.accessToken = accessToken;
    this.otpId = otpId;
    this.otpExpiryTime = otpExpiryTime;
  }

  public SignInData(String country, String city, String googleMapKeyMqtt,
      IdentityCard mmjCard, String mobile, String fcmTopic, String mqttTopic,
      IdentityCard identityCard, String userId, Token token, String countryCode,
      String name, LocationData location, String region, String email, String status,
      String requesterId,String expireTime) {
    this.country = country;
    this.city = city;
    this.googleMapKeyMqtt = googleMapKeyMqtt;
    this.mmjCard = mmjCard;
    this.mobile = mobile;
    this.fcmTopic = fcmTopic;
    this.mqttTopic = mqttTopic;
    this.identityCard = identityCard;
    this.userId = userId;
    this.token = token;
    this.countryCode = countryCode;
    this.name = name;
    this.location = location;
    this.region = region;
    this.email = email;
    this.status = status;
    this.requesterId = requesterId;
    this.expireTime=expireTime;
  }

  public int getOtpExpiryTime() {
    return otpExpiryTime;
  }

  public void setOtpExpiryTime(int otpExpiryTime) {
    this.otpExpiryTime = otpExpiryTime;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getGoogleMapKeyMqtt() {
    return googleMapKeyMqtt;
  }

  public void setGoogleMapKeyMqtt(String googleMapKeyMqtt) {
    this.googleMapKeyMqtt = googleMapKeyMqtt;
  }

  public IdentityCard getMmjCard() {
    return mmjCard;
  }

  public void setMmjCard(IdentityCard mmjCard) {
    this.mmjCard = mmjCard;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getFcmTopic() {
    return fcmTopic;
  }

  public void setFcmTopic(String fcmTopic) {
    this.fcmTopic = fcmTopic;
  }

  public String getMqttTopic() {
    return mqttTopic;
  }

  public void setMqttTopic(String mqttTopic) {
    this.mqttTopic = mqttTopic;
  }

  public IdentityCard getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(IdentityCard identityCard) {
    this.identityCard = identityCard;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocationData getLocation() {
    return location;
  }

  public void setLocation(LocationData location) {
    this.location = location;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRequesterId() {
    return requesterId;
  }

  public void setRequesterId(String requesterId) {
    this.requesterId = requesterId;
  }

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public String getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }
}
