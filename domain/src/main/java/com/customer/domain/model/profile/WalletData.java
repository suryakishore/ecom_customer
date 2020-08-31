package com.customer.domain.model.profile;

public class WalletData {
  private String softLimitHit;

  private String balance;

  private String blocked;

  private String hardLimitHit;

  private String hardLimit;

  private String softLimit;

  public WalletData(String softLimitHit, String balance, String blocked,
      String hardLimitHit, String hardLimit, String softLimit) {
    this.softLimitHit = softLimitHit;
    this.balance = balance;
    this.blocked = blocked;
    this.hardLimitHit = hardLimitHit;
    this.hardLimit = hardLimit;
    this.softLimit = softLimit;
  }

  public String getSoftLimitHit() {
    return softLimitHit;
  }

  public void setSoftLimitHit(String softLimitHit) {
    this.softLimitHit = softLimitHit;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getBlocked() {
    return blocked;
  }

  public void setBlocked(String blocked) {
    this.blocked = blocked;
  }

  public String getHardLimitHit() {
    return hardLimitHit;
  }

  public void setHardLimitHit(String hardLimitHit) {
    this.hardLimitHit = hardLimitHit;
  }

  public String getHardLimit() {
    return hardLimit;
  }

  public void setHardLimit(String hardLimit) {
    this.hardLimit = hardLimit;
  }

  public String getSoftLimit() {
    return softLimit;
  }

  public void setSoftLimit(String softLimit) {
    this.softLimit = softLimit;
  }
}
