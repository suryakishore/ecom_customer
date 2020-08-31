package com.customer.domain.repository;

import com.customer.domain.interactor.TrackingOrderUseCase;
import io.reactivex.Single;

public interface TrackingRepository {
  Single<TrackingOrderUseCase.ResponseValues> trackOrder(String productOrderId);
}
