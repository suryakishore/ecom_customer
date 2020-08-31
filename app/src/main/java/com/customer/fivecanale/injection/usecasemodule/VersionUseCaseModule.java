package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AppConfigRepositoryImpl;
import com.customer.data.repository.VersionRepositoryImpl;
import com.customer.domain.interactor.AppConfigUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.VersionUseCase;
import com.customer.domain.repository.AppConfigRepository;
import com.customer.domain.repository.VersionRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class VersionUseCaseModule {
  @Provides
  VersionRepository provideRepository(
      VersionRepositoryImpl versionRepository) {
    return versionRepository;
  }

  @Provides
  public UseCase<VersionUseCase.RequestValues, VersionUseCase.ResponseValues> getUseCase(
      VersionUseCase versionUseCase) {
    return versionUseCase;
  }
}
