package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.ProductCategoryRepositoryImpl;
import com.customer.domain.interactor.ProductCategoryUseCase;
import com.customer.domain.interactor.ProductCategoryUseCase.RequestValues;
import com.customer.domain.interactor.ProductCategoryUseCase.ResponseValues;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.ProductCategoryRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetCategoryUseCaseModule {

  @Provides
  public ProductCategoryRepository providePrdCat_Repository(
      ProductCategoryRepositoryImpl prductCatRepo) {
    return prductCatRepo;
  }

  @Provides
  public UseCase<RequestValues, ResponseValues> getPrdCat_UseCase(
      ProductCategoryUseCase prodCatUseCase) {
    return prodCatUseCase;
  }
}
