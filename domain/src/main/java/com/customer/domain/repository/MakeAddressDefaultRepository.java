package com.customer.domain.repository;

import com.customer.domain.interactor.MakeAddressDefaultUseCase;
import io.reactivex.Single;

public interface MakeAddressDefaultRepository {

  Single<MakeAddressDefaultUseCase.ResponseValues> makeAddressDefault(String addressId);
}
