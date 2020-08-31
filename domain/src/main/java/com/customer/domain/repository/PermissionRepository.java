package com.customer.domain.repository;

public interface PermissionRepository {

  boolean isFirstTimeAsking(String permission);

  void firstTimeAsking(String permission, boolean isFirstTime);
}
