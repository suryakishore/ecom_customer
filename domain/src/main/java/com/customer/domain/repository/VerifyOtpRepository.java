package com.customer.domain.repository;

import com.customer.domain.interactor.VerifyOTPUseCase;
import io.reactivex.Single;

public interface VerifyOtpRepository {
  Single<VerifyOTPUseCase.ResponseValues> verifyOtp(String otpId, String otpCode, int verifyType);
}
