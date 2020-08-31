package com.customer.domain.repository;

import com.customer.domain.interactor.signup.SignUpUseCase;
import io.reactivex.Single;

public interface SignUpRepository {
  Single<SignUpUseCase.ResponseValues> signUp(String deviceId, String mobile, String countryCode,
      String email, String password, int deviceType, int signUpType, int userType,int customerType, int termsAndCond,
      String ipAddress, String firstName, String lastName, String profilePicture, String deviceModel, String deviceMake, String deviceTime,
      String browserName, String deviceOsVersion, String latitude, String longitude,
      String appVersion);
}
