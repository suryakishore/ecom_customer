package com.customer.domain.repository;

import com.customer.domain.interactor.DeleteAddressUseCase;
import io.reactivex.Single;

public interface DeleteAddressRepository {

  Single<DeleteAddressUseCase.ResponseValues> deleteAddress(String addressId);

}
