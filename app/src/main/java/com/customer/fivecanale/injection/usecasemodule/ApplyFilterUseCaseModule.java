package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.FilterRepositoryImpl;
import com.customer.domain.interactor.FilterUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.FilterRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplyFilterUseCaseModule {
  @Provides
  FilterRepository provideRepository(FilterRepositoryImpl filterRepository) {
    return filterRepository;
  }

  @Provides
  public UseCase<FilterUseCase.RequestValues, FilterUseCase.ResponseValues> provideUseCase(
      FilterUseCase filterUseCase) {
    return filterUseCase;
  }
}
