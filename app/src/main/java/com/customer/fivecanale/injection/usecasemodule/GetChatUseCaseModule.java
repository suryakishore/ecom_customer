package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AddAddressRepositoryImpl;
import com.customer.data.repository.GetChatRepositoryImpl;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.GetCustomerChatUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AddAddressRepository;
import com.customer.domain.repository.GetCustomerChatRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetChatUseCaseModule {

  @Provides
  GetCustomerChatRepository provideRepository(GetChatRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<GetCustomerChatUseCase.RequestValues, GetCustomerChatUseCase.ResponseValues> getLoginDetailsUseCase(
      GetCustomerChatUseCase addAddressUseCase) {
    return addAddressUseCase;
  }
}
