package com.customer.fivecanale.injection.handler;

import com.customer.data.repository.handler.PermissionRepositoryImpl;
import com.customer.domain.interactor.handler.PermissionHandler;
import com.customer.domain.interactor.handler.PermissionHandlerImpl;
import com.customer.domain.repository.PermissionRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class PermissionHandlerModule {

  @Provides
  PermissionRepository provideVerifyOTPRepository(PermissionRepositoryImpl permissionRepository) {
    return permissionRepository;
  }

  @Provides
  public PermissionHandler getVerifyOtpUseCase(PermissionHandlerImpl permissionHandler) {
    return permissionHandler;
  }
}
