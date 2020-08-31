package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AppConfigRepositoryImpl;
import com.customer.data.repository.CancelOrderRepositoryImpl;
import com.customer.domain.interactor.AppConfigUseCase;
import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AppConfigRepository;
import com.customer.domain.repository.CancelOrderRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class AppConfigUseCaseModule {
  @Provides
  AppConfigRepository provideRepository(
      AppConfigRepositoryImpl appConfigRepository) {
    return appConfigRepository;
  }

  @Provides
  public UseCase<AppConfigUseCase.RequestValues, AppConfigUseCase.ResponseValues> getUseCase(
      AppConfigUseCase appConfigUseCase) {
    return appConfigUseCase;
  }
}
