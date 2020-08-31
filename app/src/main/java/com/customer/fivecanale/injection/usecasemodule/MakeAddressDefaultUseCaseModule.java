package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.MakeAddressDefaultRepositoryImpl;
import com.customer.domain.interactor.MakeAddressDefaultUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.MakeAddressDefaultRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class MakeAddressDefaultUseCaseModule {

  @Provides
  MakeAddressDefaultRepository provideRepository(
      MakeAddressDefaultRepositoryImpl makeAddressDefaultRepository) {
    return makeAddressDefaultRepository;
  }

  @Provides
  public UseCase<MakeAddressDefaultUseCase.RequestValues,
      MakeAddressDefaultUseCase.ResponseValues> getLoginDetailsUseCase(
      MakeAddressDefaultUseCase makeAddressDefaultUseCase) {
    return makeAddressDefaultUseCase;
  }
}
