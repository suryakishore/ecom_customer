package com.customer.domain.repository;

import com.customer.domain.interactor.GetSellerListUseCase;
import io.reactivex.Single;

public interface GetSellerListRepository {

  Single<GetSellerListUseCase.ResponseValues> getSellerList(String productId,
      String parentProductId, String loginType);
}
