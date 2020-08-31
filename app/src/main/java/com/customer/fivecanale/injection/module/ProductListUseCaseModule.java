package com.customer.fivecanale.injection.module;

import com.customer.data.repository.FilteredProductsListRepositoryImpl;
import com.customer.domain.interactor.FilteredProductsUseCase;
import com.customer.domain.interactor.FilteredProductsUseCase.RequestValues;
import com.customer.domain.interactor.FilteredProductsUseCase.ResponseValues;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.FilteredProductsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ProductListUseCaseModule {
  @Provides
  public FilteredProductsRepository providePrdCat_Repository(
      FilteredProductsListRepositoryImpl filProdRepo) {
    return filProdRepo;
  }

  @Provides
  public UseCase<RequestValues, ResponseValues> getPrdCat_UseCase(
      FilteredProductsUseCase filteredProductsUseCase) {
    return filteredProductsUseCase;
  }
}
