package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AllBrandsRepositoryImpl;
import com.customer.data.repository.GetNotificationsRepositoryImpl;
import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.interactor.GetNotificationsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AllBrandsRepository;
import com.customer.domain.repository.GetNotificationsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetAllBrandsUseCaseModule {
  @Provides
  AllBrandsRepository provideRepository(AllBrandsRepositoryImpl allBrandsRepository) {
    return allBrandsRepository;
  }

  @Provides
  public UseCase<AllBrandsUseCase.RequestValues, AllBrandsUseCase.ResponseValues> provideUseCase(
      AllBrandsUseCase filterUseCase) {
    return filterUseCase;
  }
}
