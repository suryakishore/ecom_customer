package com.customer.domain.interactor.handler;

import com.customer.domain.repository.PermissionRepository;
import javax.inject.Inject;

public class PermissionHandlerImpl implements PermissionHandler {
  private PermissionRepository mRepository;

  @Inject
  public PermissionHandlerImpl(PermissionRepository permissionRepository) {
    this.mRepository = permissionRepository;
  }

  @Override
  public boolean isFirstTimeAsking(String permission) {
    return mRepository.isFirstTimeAsking(permission);
  }

  @Override
  public void firstTimeAsking(String permission, boolean isFirstTime) {

    mRepository.firstTimeAsking(permission, isFirstTime);
  }
}
