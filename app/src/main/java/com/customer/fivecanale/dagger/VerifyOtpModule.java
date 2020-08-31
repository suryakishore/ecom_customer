package com.customer.fivecanale.dagger;

import com.customer.data.repository.VerifyOtpRepositoryImpl;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.VerifyOTPUseCase;
import com.customer.domain.repository.VerifyOtpRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class VerifyOtpModule {
  @Provides
  VerifyOtpRepository provideVerifyOTPRepository(VerifyOtpRepositoryImpl verifyOTPRepository) {
    return verifyOTPRepository;
  }

  @Provides
  public UseCase<VerifyOTPUseCase.RequestValues, VerifyOTPUseCase.ResponseValues> getVerifyOtpUseCase(
      VerifyOTPUseCase signInUseCase) {
    return signInUseCase;
  }
}
