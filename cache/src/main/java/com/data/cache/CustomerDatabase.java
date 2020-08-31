package com.data.cache;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.data.cache.dao.AddressDao;
import com.data.cache.dao.CartDao;
import com.data.cache.entities.UserAddress;
import com.data.cache.entities.UserCart;

@Database(entities = {UserAddress.class, UserCart.class}, version = 3)
public abstract class CustomerDatabase extends RoomDatabase {
  private static final String DB_NAME = "customerDatabase.db";
  private static CustomerDatabase INSTANCE;

  public static CustomerDatabase getDatabaseInstance(final Context context) {
    if (INSTANCE == null) {
      synchronized (CustomerDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context,
              CustomerDatabase.class, DB_NAME)
              .fallbackToDestructiveMigration()
              .build();
        }
      }
    }
    return INSTANCE;
  }

  abstract AddressDao addressDao();

  abstract CartDao cartDao();
}