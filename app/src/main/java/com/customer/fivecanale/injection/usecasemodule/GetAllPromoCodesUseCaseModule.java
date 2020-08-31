package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AllBrandsRepositoryImpl;
import com.customer.data.repository.AllPromoCodesRepositoryImpl;
import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.interactor.GetAllPromoCodesUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AllBrandsRepository;
import com.customer.domain.repository.AllPromoCodesRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetAllPromoCodesUseCaseModule {
  @Provides
  AllPromoCodesRepository provideRepository(AllPromoCodesRepositoryImpl allBrandsRepository) {
    return allBrandsRepository;
  }

  @Provides
  public UseCase<GetAllPromoCodesUseCase.RequestValues, GetAllPromoCodesUseCase.ResponseValues> provideUseCase(
      GetAllPromoCodesUseCase filterUseCase) {
    return filterUseCase;
  }
}
