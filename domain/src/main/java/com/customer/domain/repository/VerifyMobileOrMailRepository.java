package com.customer.domain.repository;

import com.customer.domain.interactor.VerifyMobileOrMailUseCase.ResponseValues;
import io.reactivex.Single;

public interface VerifyMobileOrMailRepository {
  Single<ResponseValues> signIn(String countryCode, String mobile, int verifyType, String email);
}
