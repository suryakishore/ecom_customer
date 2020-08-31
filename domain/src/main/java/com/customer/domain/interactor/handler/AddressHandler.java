package com.customer.domain.interactor.handler;

import com.customer.domain.model.getaddress.AddressListItemData;

public interface AddressHandler {

  boolean isAddressListEmpty();
  AddressListItemData getDefaultAddress();
}
