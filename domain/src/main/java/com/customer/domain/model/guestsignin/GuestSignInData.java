package com.customer.domain.model.guestsignin;

public class GuestSignInData {

  private String googleMapKeyMqtt;

  private String type;

  private String sid;

  private Token token;

  public GuestSignInData(String googleMapKeyMqtt, String type, String sid, Token token) {
    this.googleMapKeyMqtt = googleMapKeyMqtt;
    this.type = type;
    this.sid = sid;
    this.token = token;
  }

  public GuestSignInData() {

  }

  public String getGoogleMapKeyMqtt() {
    return googleMapKeyMqtt;
  }

  public void setGoogleMapKeyMqtt(String googleMapKeyMqtt) {
    this.googleMapKeyMqtt = googleMapKeyMqtt;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

}
