package com.customer.data.repository.handler;

import com.customer.data.DataSource;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.BaseRepository;
import com.customer.domain.repository.PermissionRepository;
import javax.inject.Inject;

public class PermissionRepositoryImpl extends BaseRepository implements PermissionRepository {
  private PreferenceManager preference;

  @Inject
  public PermissionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    preference = dataSource.preference();
  }

  @Override
  public boolean isFirstTimeAsking(String permission) {
    return preference.isFirstTimeAsking(permission);
  }

  @Override
  public void firstTimeAsking(String permission, boolean isFirstTime) {
    preference.firstTimeAsking(permission, isFirstTime);
  }
}
