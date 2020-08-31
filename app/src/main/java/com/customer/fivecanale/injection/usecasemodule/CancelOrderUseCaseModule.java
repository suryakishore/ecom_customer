package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.CancelOrderRepositoryImpl;
import com.customer.data.repository.GetCartProductsRepositoryImpl;
import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.domain.interactor.GetCartProductsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.CancelOrderRepository;
import com.customer.domain.repository.GetCartProductsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class CancelOrderUseCaseModule {
  @Provides
  CancelOrderRepository provideRepository(
      CancelOrderRepositoryImpl getCartProductsRepository) {
    return getCartProductsRepository;
  }

  @Provides
  public UseCase<CancelOrderUseCase.RequestValues, CancelOrderUseCase.ResponseValues> getUseCase(
      CancelOrderUseCase getCartProductsUseCase) {
    return getCartProductsUseCase;
  }
}
