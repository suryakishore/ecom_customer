package com.customer.domain.repository;

import com.customer.domain.interactor.SendOtpToUpdateProfileUseCase;
import io.reactivex.Single;

public interface SendOtpToUpdateProfileRepository {

  Single<SendOtpToUpdateProfileUseCase.ResponseValues> getOtpToUpdateProfile(String mobNum,
      String countryCode, String mail, int type);
}
