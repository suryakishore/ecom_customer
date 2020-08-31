package com.customer.domain.repository;

import com.customer.domain.interactor.AddAddressUseCase;
import io.reactivex.Single;

public interface AddAddressRepository {

  Single<AddAddressUseCase.ResponseValues> addAddress(String name,
      String mobileNumber,
      String mobileNumberCode, String locality, String addLine1, String addLine2,
      String landmark, String city, String country,
      String placeId, String pincode, String latitude, String longitude, String taggedAs,
      String state, int tagged);
}
