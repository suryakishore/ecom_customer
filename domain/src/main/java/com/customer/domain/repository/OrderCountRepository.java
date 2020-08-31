package com.customer.domain.repository;

import com.customer.domain.interactor.AppConfigUseCase;
import com.customer.domain.interactor.OrderCountUseCase;
import io.reactivex.Single;

public interface OrderCountRepository {
  Single<OrderCountUseCase.ResponseValues> orderCount();
}
