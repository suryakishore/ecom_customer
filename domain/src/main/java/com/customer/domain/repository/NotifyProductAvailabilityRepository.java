package com.customer.domain.repository;

import com.customer.domain.interactor.NotifyProductAvailabilityUseCase;
import io.reactivex.Single;

public interface NotifyProductAvailabilityRepository {

  Single<NotifyProductAvailabilityUseCase.ResponseValues> notifyProductAvailabilityRepository(
      String childProductId, String email, String parentProductId);
}
