package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetOrderDetailsRepositoryImpl;
import com.customer.data.repository.GetRecentlyViewedProductsRepositoryImpl;
import com.customer.domain.interactor.GetOrderDetailsUseCase;
import com.customer.domain.interactor.GetRecentlyViewedProductsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetOrderDetailsRepository;
import com.customer.domain.repository.GetRecentlyViewedProductsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class HistoryOrderDetailUseCaseModule {
  @Provides
  public GetOrderDetailsRepository getRecentlyViewedProductsRepository(
      GetOrderDetailsRepositoryImpl repository) {
    return repository;
  }

  @Provides
  public UseCase<GetOrderDetailsUseCase.RequestValues,
      GetOrderDetailsUseCase.ResponseValues> getRecentlyViewedProducts(
      GetOrderDetailsUseCase prodCatUseCase) {
    return prodCatUseCase;
  }
}
