package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.EditAddressRepositoryImpl;
import com.customer.domain.interactor.EditAddressUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.EditAddressRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class EditAddressUseCaseModule {

  @Provides
  EditAddressRepository provideRepository(EditAddressRepositoryImpl editAddressRepository) {
    return editAddressRepository;
  }

  @Provides
  public UseCase<EditAddressUseCase.RequestValues, EditAddressUseCase.ResponseValues> getLoginDetailsUseCase(
      EditAddressUseCase editAddressUseCase) {
    return editAddressUseCase;
  }
}
