package com.customer.domain.repository;

import com.customer.domain.interactor.AppConfigUseCase;
import io.reactivex.Single;

public interface AppConfigRepository {
  Single<AppConfigUseCase.ResponseValues> appConfig(int backGroundFlag);
}
