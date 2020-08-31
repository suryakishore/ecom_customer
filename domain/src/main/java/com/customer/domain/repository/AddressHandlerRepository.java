package com.customer.domain.repository;

import com.customer.domain.model.getaddress.AddressListItemData;

public interface AddressHandlerRepository {
  boolean isAddressListEmpty();
  AddressListItemData getDefaultAddress();
}
