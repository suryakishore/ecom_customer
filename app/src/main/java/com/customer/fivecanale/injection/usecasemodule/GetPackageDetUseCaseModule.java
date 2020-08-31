package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetPackageDetailsRepositoryImpl;
import com.customer.domain.interactor.GetPackageDetailsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetPackageDetailsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetPackageDetUseCaseModule {
  @Provides
  GetPackageDetailsRepository provideRepository(
      GetPackageDetailsRepositoryImpl getAddressRepository) {
    return getAddressRepository;
  }

  @Provides
  public UseCase<GetPackageDetailsUseCase.RequestValues, GetPackageDetailsUseCase.ResponseValues> loginDetailsUseCase(
      GetPackageDetailsUseCase getAddressUseCase) {
    return getAddressUseCase;
  }
}
