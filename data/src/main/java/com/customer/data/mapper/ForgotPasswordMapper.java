package com.customer.data.mapper;

import com.customer.domain.model.ForgotPasswordData;
import com.customer.remote.http.model.response.ForgotPasswordDetails;

public class ForgotPasswordMapper {
  public ForgotPasswordData map(ForgotPasswordDetails forgotPasswordDetails) {
    return new ForgotPasswordData(forgotPasswordDetails.getOtpId(),
        forgotPasswordDetails.getOtpExpiryTime());
  }
}
