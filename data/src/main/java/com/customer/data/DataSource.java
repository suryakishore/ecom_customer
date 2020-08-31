package com.customer.data;

import com.customer.data.preference.PreferenceManager;
import com.customer.remote.http.NetworkManager;
import com.data.cache.DatabaseManager;

public interface DataSource {
  NetworkManager api();

  DatabaseManager db();

  PreferenceManager preference();
}
