package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetWebPageDataRepositoryImpl;
import com.customer.domain.interactor.GetWebPageDataUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetWebPageDataRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetWebPageUseCaseModule {
  @Provides
  GetWebPageDataRepository provideRepository(GetWebPageDataRepositoryImpl filterRepository) {
    return filterRepository;
  }

  @Provides
  public UseCase<GetWebPageDataUseCase.RequestValues, GetWebPageDataUseCase.ResponseValues> provideUseCase(
      GetWebPageDataUseCase filterUseCase) {
    return filterUseCase;
  }
}
