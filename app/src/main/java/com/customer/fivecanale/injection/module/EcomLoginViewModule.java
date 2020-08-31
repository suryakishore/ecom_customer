package com.customer.fivecanale.injection.module;

import com.customer.data.repository.SignInRepositoryImpl;
import com.customer.domain.interactor.SignInUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.SignInRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class EcomLoginViewModule {
  @Provides
  SignInRepository provideRepository(SignInRepositoryImpl signInRepository) {
    return signInRepository;
  }

  @Provides
  public UseCase<SignInUseCase.RequestValues, SignInUseCase.ResponseValues> getLoginDetailsUseCase(
      SignInUseCase signInUseCase) {
    return signInUseCase;
  }
}
