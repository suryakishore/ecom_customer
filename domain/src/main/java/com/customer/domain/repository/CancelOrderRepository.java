package com.customer.domain.repository;

import com.customer.domain.interactor.CancelOrderUseCase;
import io.reactivex.Single;

public interface CancelOrderRepository {

  Single<CancelOrderUseCase.ResponseValues> cancelOrder(String type, String orderId, String reason,
      String comment);
}
