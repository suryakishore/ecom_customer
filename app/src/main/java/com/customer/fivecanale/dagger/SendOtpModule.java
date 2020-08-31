package com.customer.fivecanale.dagger;

import com.customer.data.repository.SendOtpRepositoryImpl;
import com.customer.domain.interactor.SendOtpUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.SendOtpRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class SendOtpModule {
  @Provides
  SendOtpRepository provideSendOTPRepository(SendOtpRepositoryImpl sendOtpRepository) {
    return sendOtpRepository;
  }

  @Provides
  public UseCase<SendOtpUseCase.RequestValues, SendOtpUseCase.ResponseValues> getSendOtpUseCase(
      SendOtpUseCase signInUseCase) {
    return signInUseCase;
  }
}
