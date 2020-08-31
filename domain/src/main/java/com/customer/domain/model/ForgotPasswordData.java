package com.customer.domain.model;

import android.text.TextUtils;

public class ForgotPasswordData {
  private String otpId;
  private int otpExpiryTime;

  public ForgotPasswordData(String otpId, String otpExpiryTime) {
    this.otpId = otpId;
    this.otpExpiryTime = !TextUtils.isEmpty(otpExpiryTime) && TextUtils.isDigitsOnly(otpExpiryTime)
        ? Integer.parseInt(otpExpiryTime) : 0;
  }

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public int getOtpExpiryTime() {
    return otpExpiryTime;
  }

  public void setOtpExpiryTime(int otpExpiryTime) {
    this.otpExpiryTime = otpExpiryTime;
  }
}
