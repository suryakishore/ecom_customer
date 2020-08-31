package com.customer.data.repository;

import android.util.Log;
import com.customer.data.DataSource;
import com.customer.data.mapper.AddressMapper;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.repository.AddressHandlerRepository;
import com.data.cache.DatabaseManager;
import com.data.cache.entities.UserAddress;
import javax.inject.Inject;

public class AddressHandlerRepositoryImpl extends BaseRepository implements
    AddressHandlerRepository {
  private DatabaseManager mDatabaseManager;
  private AddressMapper mMapper = new AddressMapper();

  @Inject
  public AddressHandlerRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDatabaseManager = dataSource.db();
  }

  @Override
  public boolean isAddressListEmpty() {
    if (mDatabaseManager != null) {
      final boolean[] isEmpty = {true};
      isEmpty[0] = mDatabaseManager.address().getRowCount() <= 0;
      Log.d("exe", "isEmpty[0]" + isEmpty[0] + " " + mDatabaseManager.address().getRowCount());
      return isEmpty[0];
    }
    return true;
  }

  @Override
  public AddressListItemData getDefaultAddress() {
    if (mDatabaseManager != null) {
      final UserAddress[] userAddress = new UserAddress[1];
      /*Thread t = new Thread() {
        public void run() {*/
      userAddress[0] = mDatabaseManager.address().getDefaultAddress();
      /* }
      };
      t.start();*/
      return mMapper.mapper(userAddress[0]);
    }
    return null;
  }
}
