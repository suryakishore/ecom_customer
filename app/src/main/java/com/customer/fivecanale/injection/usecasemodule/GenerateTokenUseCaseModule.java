package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AppConfigRepositoryImpl;
import com.customer.data.repository.GenerateTokenRepositoryImpl;
import com.customer.domain.interactor.AppConfigUseCase;
import com.customer.domain.interactor.GenerateTokenUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AppConfigRepository;
import com.customer.domain.repository.GenerateTokenRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GenerateTokenUseCaseModule {
  @Provides
  GenerateTokenRepository provideRepository(
      GenerateTokenRepositoryImpl appConfigRepository) {
    return appConfigRepository;
  }

  @Provides
  public UseCase<GenerateTokenUseCase.RequestValues, GenerateTokenUseCase.ResponseValues> getUseCase(
      GenerateTokenUseCase appConfigUseCase) {
    return appConfigUseCase;
  }
}
