package com.customer.domain.repository;

import com.customer.domain.interactor.LogoutUseCase;
import io.reactivex.Single;

public interface LogoutRepository {

  Single<LogoutUseCase.ResponseValues> logout(String ipAddress, double latitude,
      double longitude);
}
