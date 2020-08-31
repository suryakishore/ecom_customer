package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AddAddressRepositoryImpl;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AddAddressRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class AddAddressUseCaseModule {

  @Provides
  AddAddressRepository provideRepository(AddAddressRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<AddAddressUseCase.RequestValues, AddAddressUseCase.ResponseValues> getLoginDetailsUseCase(
      AddAddressUseCase addAddressUseCase) {
    return addAddressUseCase;
  }
}
