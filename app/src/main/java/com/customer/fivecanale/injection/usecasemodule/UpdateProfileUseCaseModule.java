package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.UpdateProfileRepositoryImpl;
import com.customer.domain.interactor.UpdateProfileUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.UpdateProfileRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class UpdateProfileUseCaseModule {

  @Provides
  UpdateProfileRepository provideRepository(UpdateProfileRepositoryImpl updateProfileRepository) {
    return updateProfileRepository;
  }

  @Provides
  public UseCase<UpdateProfileUseCase.RequestValues, UpdateProfileUseCase.ResponseValues> getLoginDetailsUseCase(
      UpdateProfileUseCase updateProfileUseCase) {
    return updateProfileUseCase;
  }
}
