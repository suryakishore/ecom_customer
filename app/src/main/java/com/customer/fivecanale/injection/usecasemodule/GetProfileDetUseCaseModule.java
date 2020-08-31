package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetProfileDetailsRepositoryImpl;
import com.customer.domain.interactor.GetProfileDetailsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetProfileDetailsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetProfileDetUseCaseModule {

  @Provides
  public GetProfileDetailsRepository provideRepository(
      GetProfileDetailsRepositoryImpl profileDetailsRepository) {
    return profileDetailsRepository;
  }

  @Provides
  public UseCase<GetProfileDetailsUseCase.RequestValues, GetProfileDetailsUseCase.ResponseValues> getseCase(
      GetProfileDetailsUseCase getProfileDetailsUseCase) {
    return getProfileDetailsUseCase;
  }
}
