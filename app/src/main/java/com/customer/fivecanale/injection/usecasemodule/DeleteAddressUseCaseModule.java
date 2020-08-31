package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.DeleteAddressRepositoryImpl;
import com.customer.domain.interactor.DeleteAddressUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.DeleteAddressRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class DeleteAddressUseCaseModule {
  @Provides
  DeleteAddressRepository provideRepository(DeleteAddressRepositoryImpl deleteAddressRepository) {
    return deleteAddressRepository;
  }

  @Provides
  public UseCase<DeleteAddressUseCase.RequestValues, DeleteAddressUseCase.ResponseValues> getUseCase(
      DeleteAddressUseCase deleteAddressUseCase) {
    return deleteAddressUseCase;
  }
}
