package com.customer.domain.repository;

import com.customer.domain.interactor.IpAddressToLocationUseCase;
import io.reactivex.Single;

public interface IpAddressToLocationRepository {

  Single<IpAddressToLocationUseCase.ResponseValues> ipAddressToLocation(String ipAddress);

}
