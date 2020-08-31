package com.customer.domain.repository;

import com.customer.domain.interactor.guestlogin.GuestApiUseCase;
import io.reactivex.Single;

public interface GuestLogInRepository {
  Single<GuestApiUseCase.ResponseValues> guestSignIn(String deviceId, String appVersion,
      int deviceType, String deviceMake, String deviceModel, String deviceOsVersion ,String ipAddress, String browserName,
      String browserVersion, String deviceTime,String latitude,String longitude);
}
