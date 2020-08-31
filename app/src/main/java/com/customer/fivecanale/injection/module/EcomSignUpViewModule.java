package com.customer.fivecanale.injection.module;

import com.customer.data.repository.VerifyMobileOrMailRepositoryImpl;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.VerifyMobileOrMailUseCase;
import com.customer.domain.repository.VerifyMobileOrMailRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class EcomSignUpViewModule {
  @Provides
  VerifyMobileOrMailRepository provideVerifyOTPRepository(
      VerifyMobileOrMailRepositoryImpl verifyMobileOrMailRepository) {
    return verifyMobileOrMailRepository;
  }

  @Provides
  public UseCase<VerifyMobileOrMailUseCase.RequestValues,
      VerifyMobileOrMailUseCase.ResponseValues> getVerifyOtpUseCase(
      VerifyMobileOrMailUseCase verifyMobileOrMailUseCase) {
    return verifyMobileOrMailUseCase;
  }
}
