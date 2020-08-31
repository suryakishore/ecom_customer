package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetProductOnQrCodeRepositoryImpl;
import com.customer.domain.interactor.GetProductOnQrCodeUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetProductOnQrCodeRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class BarCodeReadUseCaseModule {

  @Provides
  GetProductOnQrCodeRepository provideRepository(
      GetProductOnQrCodeRepositoryImpl getProductOnQrCodeRepository) {
    return getProductOnQrCodeRepository;
  }

  @Provides
  public UseCase<GetProductOnQrCodeUseCase.RequestValues,
      GetProductOnQrCodeUseCase.ResponseValues> getUseCase(
      GetProductOnQrCodeUseCase getProductOnQrCodeUseCase) {
    return getProductOnQrCodeUseCase;
  }
}
