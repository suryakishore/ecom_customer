package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetFilterParamsRepositoryImpl;
import com.customer.domain.interactor.GetFilterParamsUseCase;
import com.customer.domain.interactor.GetFilterParamsUseCase.RequestValues;
import com.customer.domain.interactor.GetFilterParamsUseCase.ResponseValues;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetFilterParamsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class FilterUseCaseModule {

  @Provides
  GetFilterParamsRepository provideRepository(
      GetFilterParamsRepositoryImpl filterParamsRepository) {
    return filterParamsRepository;
  }

  @Provides
  public UseCase<RequestValues, ResponseValues> getLoginDetailsUseCase(
      GetFilterParamsUseCase filterParamsUseCase) {
    return filterParamsUseCase;
  }

}
