package com.customer.domain.repository;

import com.customer.domain.interactor.ForgotPasswordUseCase.ResponseValues;
import io.reactivex.Single;

public interface ForgotPasswordRepository {

  Single<ResponseValues> forgotPassword(int verifyType, String email, String countryCode,
      String mobile,
      String deviceId, String deviceMake, String deviceModel, String deviceType);

}
