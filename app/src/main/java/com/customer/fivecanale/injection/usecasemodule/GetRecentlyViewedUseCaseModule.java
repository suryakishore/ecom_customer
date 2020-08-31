package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetRecentlyViewedProductsRepositoryImpl;
import com.customer.domain.interactor.GetRecentlyViewedProductsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetRecentlyViewedProductsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetRecentlyViewedUseCaseModule {
  @Provides
  public GetRecentlyViewedProductsRepository getRecentlyViewedProductsRepository(
      GetRecentlyViewedProductsRepositoryImpl repository) {
    return repository;
  }

  @Provides
  public UseCase<GetRecentlyViewedProductsUseCase.RequestValues,
      GetRecentlyViewedProductsUseCase.ResponseValues> getRecentlyViewedProducts(
      GetRecentlyViewedProductsUseCase prodCatUseCase) {
    return prodCatUseCase;
  }
}
