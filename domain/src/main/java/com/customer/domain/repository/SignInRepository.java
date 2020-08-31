package com.customer.domain.repository;

import com.customer.domain.interactor.SignInUseCase;
import io.reactivex.Single;

public interface SignInRepository {
  Single<SignInUseCase.ResponseValues> signIn(String mobile, String countryCode, String email,
      String password, int verifyType, int loginType, String deviceId, int deviceType,
      String ipAddress, String googleId, String facebookId,
      String deviceModel, String deviceMake, String browserName, String deviceOsVersion,
      String latitude, String longitude,String appVersion);
}
