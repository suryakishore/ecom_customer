package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AddAddressRepositoryImpl;
import com.customer.data.repository.OrderCountRepositoryImpl;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.OrderCountUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AddAddressRepository;
import com.customer.domain.repository.OrderCountRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class OrderCountUseCaseModule {

  @Provides
  OrderCountRepository provideRepository(OrderCountRepositoryImpl orderCountRepository) {
    return orderCountRepository;
  }

  @Provides
  public UseCase<OrderCountUseCase.RequestValues, OrderCountUseCase.ResponseValues> getOrderCountUseCase(
      OrderCountUseCase orderCountUseCase) {
    return orderCountUseCase;
  }
}
