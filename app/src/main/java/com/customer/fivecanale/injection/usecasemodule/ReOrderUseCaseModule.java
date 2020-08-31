package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.ReorderRepositoryImpl;
import com.customer.domain.interactor.ReorderUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.ReorderRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ReOrderUseCaseModule {
  @Provides
  ReorderRepository provideRepository(ReorderRepositoryImpl getAddressRepository) {
    return getAddressRepository;
  }

  @Provides
  public UseCase<ReorderUseCase.RequestValues, ReorderUseCase.ResponseValues> getLoginDetailsUseCase(
      ReorderUseCase getAddressUseCase) {
    return getAddressUseCase;
  }
}
