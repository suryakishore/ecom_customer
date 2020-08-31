package com.customer.domain.repository;

import com.customer.domain.interactor.PdpUseCase;
import io.reactivex.Single;

public interface ProductDetailsRepository {
  Single<PdpUseCase.ResponseValues> getPdp(String productId, String parentProductId,double lat, double longitude);
}
