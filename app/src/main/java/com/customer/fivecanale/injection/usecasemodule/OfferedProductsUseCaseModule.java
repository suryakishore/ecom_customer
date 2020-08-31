package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetOfferProductsRepositoryImpl;
import com.customer.domain.interactor.GetOfferProductsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetOfferProductsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class OfferedProductsUseCaseModule {

  @Provides
  GetOfferProductsRepository provideRepository(
      GetOfferProductsRepositoryImpl offerProductsRepository) {
    return offerProductsRepository;
  }

  @Provides
  public UseCase<GetOfferProductsUseCase.RequestValues,
      GetOfferProductsUseCase.ResponseValues> getLoginDetailsUseCase(
      GetOfferProductsUseCase offerProductsUseCase) {
    return offerProductsUseCase;
  }
}
