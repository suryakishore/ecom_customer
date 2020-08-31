package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.NotifyProductAvailabilityRepositoryImpl;
import com.customer.domain.interactor.NotifyProductAvailabilityUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.NotifyProductAvailabilityRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class NotifyProductUseCaseModule {
  @Provides
  NotifyProductAvailabilityRepository provideRepository(
      NotifyProductAvailabilityRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<NotifyProductAvailabilityUseCase.RequestValues,
      NotifyProductAvailabilityUseCase.ResponseValues> getLoginDetailsUseCase(
      NotifyProductAvailabilityUseCase addAddressUseCase) {
    return addAddressUseCase;
  }
}
