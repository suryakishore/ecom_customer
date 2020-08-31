package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetCartProductsRepositoryImpl;
import com.customer.domain.interactor.GetCartProductsUseCase;
import com.customer.domain.interactor.GetCartProductsUseCase.RequestValues;
import com.customer.domain.interactor.GetCartProductsUseCase.ResponseValues;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetCartProductsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetCartUseCaseModule {
  @Provides
  GetCartProductsRepository provideRepository(
      GetCartProductsRepositoryImpl getCartProductsRepository) {
    return getCartProductsRepository;
  }

  @Provides
  public UseCase<RequestValues, ResponseValues> getUseCase(
      GetCartProductsUseCase getCartProductsUseCase) {
    return getCartProductsUseCase;
  }
}
