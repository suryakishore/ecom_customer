package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetProductOnQrCodeRepositoryImpl;
import com.customer.domain.interactor.GetProductOnQrCodeUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetProductOnQrCodeRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetProductOnQrCodeUseCaseModule {

  @Provides
  public GetProductOnQrCodeRepository providePrdCat_Repository(
      GetProductOnQrCodeRepositoryImpl repository) {
    return repository;
  }

  @Provides
  public UseCase<GetProductOnQrCodeUseCase.RequestValues,
      GetProductOnQrCodeUseCase.ResponseValues> getPrdCat_UseCase(
      GetProductOnQrCodeUseCase prodCatUseCase) {
    return prodCatUseCase;
  }
}
