package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetAddressRepositoryImpl;
import com.customer.domain.interactor.GetAddressUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetAddressRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class SavedAddressListUseCaseModule {
  @Provides
  GetAddressRepository provideRepository(GetAddressRepositoryImpl getAddressRepository) {
    return getAddressRepository;
  }

  @Provides
  public UseCase<GetAddressUseCase.RequestValues, GetAddressUseCase.ResponseValues> getLoginDetailsUseCase(
      GetAddressUseCase getAddressUseCase) {
    return getAddressUseCase;
  }
}
