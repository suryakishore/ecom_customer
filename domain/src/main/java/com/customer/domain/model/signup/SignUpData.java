package com.customer.domain.model.signup;

import com.customer.domain.model.guestsignin.Token;

public class SignUpData {

  private String country;

  private String statusMsg;

  private String city;

  private String name;

  private String mobile;

  private LocationData location;

  private String region;

  private String userId;

  private String toEmail;

  private Token token;

  private String status;

  private String fcmTopic;

  public SignUpData(String country, String statusMsg, String city, String name, String mobile,
      LocationData location, String region, String userId, String toEmail, Token token,
      String status, String fcmTopic) {
    this.country = country;
    this.statusMsg = statusMsg;
    this.city = city;
    this.name = name;
    this.mobile = mobile;
    this.location = location;
    this.region = region;
    this.userId = userId;
    this.toEmail = toEmail;
    this.token = token;
    this.status = status;
  }

  public String getFcmTopic() {
    return fcmTopic;
  }

  public void setFcmTopic(String fcmTopic) {
    this.fcmTopic = fcmTopic;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getToEmail() {
    return toEmail;
  }

  public void setToEmail(String toEmail) {
    this.toEmail = toEmail;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
