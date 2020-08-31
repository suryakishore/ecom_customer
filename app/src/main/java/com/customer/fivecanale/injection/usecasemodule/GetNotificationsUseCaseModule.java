package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.FilterRepositoryImpl;
import com.customer.data.repository.GetNotificationsRepositoryImpl;
import com.customer.domain.interactor.FilterUseCase;
import com.customer.domain.interactor.GetNotificationsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.FilterRepository;
import com.customer.domain.repository.GetNotificationsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetNotificationsUseCaseModule {
  @Provides
  GetNotificationsRepository provideRepository(GetNotificationsRepositoryImpl filterRepository) {
    return filterRepository;
  }

  @Provides
  public UseCase<GetNotificationsUseCase.RequestValues, GetNotificationsUseCase.ResponseValues> provideUseCase(
      GetNotificationsUseCase filterUseCase) {
    return filterUseCase;
  }
}
