package com.customer.domain.model.guestsignin;

public class Token {

  private String accessToken;

  private String accessExpiry;

  private String refreshToken;

  public Token(String accessToken, String accessExpiry, String refreshToken) {
    this.accessToken = accessToken;
    this.accessExpiry = accessExpiry;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessExpiry() {
    return accessExpiry;
  }

  public void setAccessExpiry(String accessExpiry) {
    this.accessExpiry = accessExpiry;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
