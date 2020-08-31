package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.PlaceOrderRepositoryImpl;
import com.customer.domain.interactor.PlaceOrderUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.PlaceOrderRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class PlaceOrderUseCaseModule {
  @Provides
  PlaceOrderRepository provideRepository(
      PlaceOrderRepositoryImpl placeOrderRepository) {
    return placeOrderRepository;
  }

  @Provides
  public UseCase<PlaceOrderUseCase.RequestValues, PlaceOrderUseCase.ResponseValues> getUseCase(
      PlaceOrderUseCase placeOrderUseCase) {
    return placeOrderUseCase;
  }
}
