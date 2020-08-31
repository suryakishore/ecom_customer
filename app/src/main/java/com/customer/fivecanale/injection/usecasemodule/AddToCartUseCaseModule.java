package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AddAddressRepositoryImpl;
import com.customer.data.repository.AddProductToCartRepositoryImpl;
import com.customer.data.repository.UpdateCartRepositoryImpl;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.AddProductToCartUseCase;
import com.customer.domain.interactor.UpdateCartUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AddAddressRepository;
import com.customer.domain.repository.AddProductToCartRepository;
import com.customer.domain.repository.UpdateCartRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class AddToCartUseCaseModule {

  @Provides
  AddProductToCartRepository provideRepository(AddProductToCartRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<AddProductToCartUseCase.RequestValues, AddProductToCartUseCase.ResponseValues> getLoginDetailsUseCase(
      AddProductToCartUseCase addAddressUseCase) {
    return addAddressUseCase;
  }

  @Provides
  UpdateCartRepository provideUpdateRepository(UpdateCartRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<UpdateCartUseCase.RequestValues, UpdateCartUseCase.ResponseValues> updateUseCase(
      UpdateCartUseCase addAddressUseCase) {
    return addAddressUseCase;
  }


}
