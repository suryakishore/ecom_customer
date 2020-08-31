package com.customer.domain.interactor.handler;

import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.repository.AddressHandlerRepository;
import javax.inject.Inject;

public class AddressHandlerImpl implements AddressHandler {
  private AddressHandlerRepository mRepository;

  @Inject
  public AddressHandlerImpl(AddressHandlerRepository permissionRepository) {
    this.mRepository = permissionRepository;
  }

  @Override
  public boolean isAddressListEmpty() {
    return mRepository.isAddressListEmpty();
  }

  @Override
  public AddressListItemData getDefaultAddress() {
    return mRepository.getDefaultAddress();
  }
}
