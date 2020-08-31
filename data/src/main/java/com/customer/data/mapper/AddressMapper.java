package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.DEFAULT_TYPE;

import com.customer.domain.model.getaddress.AddressListItemData;
import com.data.cache.entities.UserAddress;

public class AddressMapper {
  public AddressListItemData mapper(UserAddress userAddress) {
    if (userAddress != null) {
      return new AddressListItemData(userAddress.mobileNumberCode,
          userAddress.country, userAddress.pinCode, userAddress.city, userAddress.mobileNumber,
          userAddress.flatNumber, userAddress.latitude, userAddress.alternatePhoneCode,
          userAddress.alternatePhone, userAddress.locality, userAddress.placeId,
          userAddress.createdTimeStamp, userAddress.userId, userAddress.createdIsoDate,
          userAddress.name, userAddress.addLine1, userAddress.id, userAddress.addLine2,
          userAddress.state, userAddress.userType, userAddress.landmark,
          userAddress.taggedAs, userAddress.longitude, userAddress.defaultAd.equals(DEFAULT_TYPE),
          userAddress.tagged,userAddress.cityId,userAddress.countryId);
    }
    return new AddressListItemData();
  }
}
