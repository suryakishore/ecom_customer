package com.customer.domain.model.profile;

public class MmjCardData {

  private String verified;

  private String url;

  public MmjCardData(String verified, String url) {
    this.verified = verified;
    this.url = url;
  }

  public String getVerified() {
    return verified;
  }

  public void setVerified(String verified) {
    this.verified = verified;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
