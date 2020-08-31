package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AllBrandsRepositoryImpl;
import com.customer.data.repository.ChangeLanguageRepositoryImpl;
import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.interactor.ChangeLanguageUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AllBrandsRepository;
import com.customer.domain.repository.ChangeLanguageRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ChangeLanguageUseCaseModule {
  @Provides
  ChangeLanguageRepository provideRepository(ChangeLanguageRepositoryImpl allBrandsRepository) {
    return allBrandsRepository;
  }

  @Provides
  public UseCase<ChangeLanguageUseCase.RequestValues, ChangeLanguageUseCase.ResponseValues> provideUseCase(
      ChangeLanguageUseCase filterUseCase) {
    return filterUseCase;
  }
}
