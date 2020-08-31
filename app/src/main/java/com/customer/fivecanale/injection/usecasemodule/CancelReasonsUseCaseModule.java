package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.CancelOrderRepositoryImpl;
import com.customer.data.repository.GetReasonsRepositoryImpl;
import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.domain.interactor.GetCancelReasonsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.CancelOrderRepository;
import com.customer.domain.repository.GetReasonsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class CancelReasonsUseCaseModule {
  @Provides
  GetReasonsRepository provideRepository(
      GetReasonsRepositoryImpl getReasonsRepository) {
    return getReasonsRepository;
  }

  @Provides
  public UseCase<GetCancelReasonsUseCase.RequestValues, GetCancelReasonsUseCase.ResponseValues> getUseCase(
      GetCancelReasonsUseCase getCancelReasonsUseCase) {
    return getCancelReasonsUseCase;
  }
}
