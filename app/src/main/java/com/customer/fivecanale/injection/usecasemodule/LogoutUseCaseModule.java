package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.LogoutRepositoryImpl;
import com.customer.domain.interactor.LogoutUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.LogoutRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class LogoutUseCaseModule {

  @Provides
  LogoutRepository provideRepository(LogoutRepositoryImpl logoutRepository) {
    return logoutRepository;
  }

  @Provides
  public UseCase<LogoutUseCase.RequestValues, LogoutUseCase.ResponseValues> getLoginDetailsUseCase(
      LogoutUseCase logoutUseCase) {
    return logoutUseCase;
  }
}
