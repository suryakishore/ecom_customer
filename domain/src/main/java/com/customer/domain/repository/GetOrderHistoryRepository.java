package com.customer.domain.repository;

import com.customer.domain.interactor.GetOrderHistoryUseCase;
import io.reactivex.Single;

public interface GetOrderHistoryRepository {

  Single<GetOrderHistoryUseCase.ResponseValues> getOrderHistory(int limit, int skip, int status,
      int orderType, int storeType, int orderBy, String search,String orderTime);
}
