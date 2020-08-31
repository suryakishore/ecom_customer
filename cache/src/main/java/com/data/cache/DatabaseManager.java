package com.data.cache;

import com.data.cache.dao.AddressDao;
import com.data.cache.dao.CartDao;

public interface DatabaseManager {
  AddressDao address();

  CartDao cart();
}
