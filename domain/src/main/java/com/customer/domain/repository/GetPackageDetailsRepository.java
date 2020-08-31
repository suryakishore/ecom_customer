package com.customer.domain.repository;

import com.customer.domain.interactor.GetPackageDetailsUseCase;
import io.reactivex.Single;

public interface GetPackageDetailsRepository {
  Single<GetPackageDetailsUseCase.ResponseValues> getPackageDetails(String productOrderId,
      String packageID);
}
