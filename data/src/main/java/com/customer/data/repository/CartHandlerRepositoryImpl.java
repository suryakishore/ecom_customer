package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.LOWER_BOUND;

import android.util.Log;
import com.customer.data.DataSource;
import com.customer.domain.repository.CartHandlerRepository;
import com.data.cache.DatabaseManager;
import javax.inject.Inject;

public class CartHandlerRepositoryImpl extends BaseRepository implements CartHandlerRepository {
  private static final int NUMBER_OF_THREADS = 4;
  private DatabaseManager mDatabaseManager;

  @Inject
  public CartHandlerRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDatabaseManager = dataSource.db();
  }

  @Override
  public boolean isCartEmpty() {
    final boolean isCartEmpty;
    isCartEmpty = mDatabaseManager.cart().getRowCount() <= LOWER_BOUND;
    return isCartEmpty;
  }

  @Override
  public boolean isProductExistInCart(final String productId) {
    final boolean isProductExist;
    isProductExist = mDatabaseManager.cart().getProductCount(productId) > LOWER_BOUND;
    Log.d("ItemExistInCart", "" + isProductExist);
    return isProductExist;
  }

  @Override
  public int getQuantity(String productId) {

    return mDatabaseManager.cart().getProductById(productId);
  }

  @Override
  public int getTotalCartItems() {
    return mDatabaseManager.cart().getRowCount();
  }
}
