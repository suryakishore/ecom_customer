package com.customer.domain.repository;

import com.customer.domain.interactor.VerifyProfileOtpUseCase;
import io.reactivex.Single;

public interface VerifyProfileOtpRepository {

  Single<VerifyProfileOtpUseCase.ResponseValues> verifyProfileOtp(String countryCode, String otpId,
      String otpCode, String mobile,
      String mail, int type);
}
