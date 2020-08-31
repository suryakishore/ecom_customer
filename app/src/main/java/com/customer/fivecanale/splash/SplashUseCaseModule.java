package com.customer.fivecanale.splash;

import com.customer.data.repository.GuestSignRepositoryImpl;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase.RequestValues;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase.ResponseValues;
import com.customer.domain.repository.GuestLogInRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class SplashUseCaseModule {
  @Provides
  public GuestLogInRepository provideRepository(GuestSignRepositoryImpl guestSignRepositoryImpl) {
    return guestSignRepositoryImpl;
  }

  @Provides
  public UseCase<RequestValues, ResponseValues> getLoginDetailsUseCase(GuestApiUseCase getLoginDetailsUseCase) {
    return getLoginDetailsUseCase;
  }

}
