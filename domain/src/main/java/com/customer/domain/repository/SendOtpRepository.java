package com.customer.domain.repository;

import com.customer.domain.interactor.SendOtpUseCase;
import io.reactivex.Single;

public interface SendOtpRepository {
  Single<SendOtpUseCase.ResponseValues> sendOTP(String mobile, String countryCode, String email,
      int verifyType, String triggeredBy, String userName, int type);
}
