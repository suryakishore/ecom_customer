package com.customer.fivecanale.injection.module;

import com.customer.data.repository.ProductDetailsRepositoryImpl;
import com.customer.domain.interactor.PdpUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.ProductDetailsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class EcomProductDetailsViewModule {

  @Provides
  ProductDetailsRepository provideRepository(
      ProductDetailsRepositoryImpl productDetailsRepository) {
    return productDetailsRepository;
  }

  @Provides
  public UseCase<PdpUseCase.RequestValues, PdpUseCase.ResponseValues> getLoginDetailsUseCase(
      PdpUseCase pdpUseCase) {
    return pdpUseCase;
  }

}
