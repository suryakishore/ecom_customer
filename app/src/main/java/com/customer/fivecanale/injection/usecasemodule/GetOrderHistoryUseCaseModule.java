package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AddAddressRepositoryImpl;
import com.customer.data.repository.GetOrderHistoryRepositoryImpl;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.GetOrderHistoryUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AddAddressRepository;
import com.customer.domain.repository.GetOrderHistoryRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetOrderHistoryUseCaseModule {
  @Provides
  GetOrderHistoryRepository provideRepository(GetOrderHistoryRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<GetOrderHistoryUseCase.RequestValues, GetOrderHistoryUseCase.ResponseValues> getLoginDetailsUseCase(
      GetOrderHistoryUseCase addAddressUseCase) {
    return addAddressUseCase;
  }
}
