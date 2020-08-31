package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.UploadImageRepositoryImpl;
import com.customer.domain.interactor.UploadImageUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.UploadImageRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class UploadImageUseCaseModule {
  @Provides
  UploadImageRepository provideRepository(
      UploadImageRepositoryImpl placeOrderRepository) {
    return placeOrderRepository;
  }

  @Provides
  public UseCase<UploadImageUseCase.RequestValues, UploadImageUseCase.ResponseValues> getUseCase(
      UploadImageUseCase placeOrderUseCase) {
    return placeOrderUseCase;
  }
}
