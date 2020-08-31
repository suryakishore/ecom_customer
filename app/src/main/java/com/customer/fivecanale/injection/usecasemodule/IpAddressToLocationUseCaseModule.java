package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.IpAddressToLocationRepositoryImpl;
import com.customer.domain.interactor.IpAddressToLocationUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.IpAddressToLocationRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class IpAddressToLocationUseCaseModule {
  @Provides
  IpAddressToLocationRepository provideRepository(
      IpAddressToLocationRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<IpAddressToLocationUseCase.RequestValues,
      IpAddressToLocationUseCase.ResponseValues> getLoginDetailsUseCase(
      IpAddressToLocationUseCase addAddressUseCase) {
    return addAddressUseCase;
  }
}
