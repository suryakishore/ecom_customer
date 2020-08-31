package com.customer.domain.repository;

import com.customer.domain.interactor.ReorderUseCase;
import io.reactivex.Single;

public interface ReorderRepository {

  Single<ReorderUseCase.ResponseValues> makeReorder(String type, String orderId);
}
