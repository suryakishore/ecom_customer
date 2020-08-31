package com.customer.domain.repository;

import com.customer.domain.interactor.GetOrderDetailsUseCase;
import io.reactivex.Single;

public interface GetOrderDetailsRepository {

  Single<GetOrderDetailsUseCase.ResponseValues> getOrderDetails(String type, String orderId);
}
