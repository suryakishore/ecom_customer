package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.TransactionEstimateRepositoryImpl;
import com.customer.domain.interactor.TransactionEstimateUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.TransactionEstimateRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class TransactionEstimateUseCaseModule {
  @Provides
  TransactionEstimateRepository provideRepository(
      TransactionEstimateRepositoryImpl getAddressRepository) {
    return getAddressRepository;
  }

  @Provides
  public UseCase<TransactionEstimateUseCase.RequestValues,
      TransactionEstimateUseCase.ResponseValues> getLoginDetailsUseCase(
      TransactionEstimateUseCase getAddressUseCase) {
    return getAddressUseCase;
  }
}
