package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetFilterParamsRepositoryImpl;
import com.customer.data.repository.TrackingRepositoryImpl;
import com.customer.domain.interactor.GetFilterParamsUseCase;
import com.customer.domain.interactor.TrackingOrderUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetFilterParamsRepository;
import com.customer.domain.repository.TrackingRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class TrackingUseCaseModule {
  @Provides
  TrackingRepository provideRepository(
      TrackingRepositoryImpl trackingRepository) {
    return trackingRepository;
  }

  @Provides
  public UseCase<TrackingOrderUseCase.RequestValues, TrackingOrderUseCase.ResponseValues> getTrackingUseCaseModule(
      TrackingOrderUseCase trackingOrderUseCase) {
    return trackingOrderUseCase;
  }
}
