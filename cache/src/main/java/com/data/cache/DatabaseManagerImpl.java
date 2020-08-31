package com.data.cache;

import com.data.cache.dao.AddressDao;
import com.data.cache.dao.CartDao;

public class DatabaseManagerImpl implements DatabaseManager {

  private CustomerDatabase mCustomerDatabase;

  public DatabaseManagerImpl(CustomerDatabase customerDatabase) {
    mCustomerDatabase = customerDatabase;
  }

  @Override
  public AddressDao address() {
    return mCustomerDatabase.addressDao();
  }

  @Override
  public CartDao cart() {
    return mCustomerDatabase.cartDao();
  }
}
