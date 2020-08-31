package com.customer.fivecanale.injection.handler;

import com.customer.data.repository.AddressHandlerRepositoryImpl;
import com.customer.data.repository.CartHandlerRepositoryImpl;
import com.customer.data.repository.handler.UserInfoRepoImpl;
import com.customer.domain.interactor.handler.AddressHandler;
import com.customer.domain.interactor.handler.AddressHandlerImpl;
import com.customer.domain.interactor.handler.CartHandler;
import com.customer.domain.interactor.handler.CartHandlerImpl;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.interactor.handler.UserInfoHandlerImpl;
import com.customer.domain.repository.AddressHandlerRepository;
import com.customer.domain.repository.CartHandlerRepository;
import com.customer.domain.repository.UserInfoRepo;
import dagger.Module;
import dagger.Provides;

@Module
public class UserInfoModule {
  @Provides
  UserInfoRepo provideVerifyOTPRepository(UserInfoRepoImpl userInfoRepo) {
    return userInfoRepo;
  }

  @Provides
  public UserInfoHandler getVerifyOtpUseCase(UserInfoHandlerImpl userInfoHandler) {
    return userInfoHandler;
  }

  @Provides
  AddressHandlerRepository provideRepository(AddressHandlerRepositoryImpl userInfoRepo) {
    return userInfoRepo;
  }

  @Provides
  public AddressHandler getUseCase(AddressHandlerImpl userInfoHandler) {
    return userInfoHandler;
  }

  @Provides
  CartHandlerRepository provideCartRepository(CartHandlerRepositoryImpl userInfoRepo) {
    return userInfoRepo;
  }

  @Provides
  public CartHandler getCartUseCase(CartHandlerImpl userInfoHandler) {
    return userInfoHandler;
  }



}