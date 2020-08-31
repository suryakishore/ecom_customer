package com.customer.domain.interactor.handler;

public interface PermissionHandler {

  boolean isFirstTimeAsking(String permission);

  void firstTimeAsking(String permission, boolean isFirstTime);
}
