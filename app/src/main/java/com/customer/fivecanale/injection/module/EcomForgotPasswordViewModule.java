package com.customer.fivecanale.injection.module;

import com.customer.data.repository.ForgotPasswordRepositoryImpl;
import com.customer.data.repository.ResetPasswordRepositoryImpl;
import com.customer.data.repository.SignUpRepositoryImpl;
import com.customer.domain.interactor.ForgotPasswordUseCase;
import com.customer.domain.interactor.ResetPasswordUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.signup.SignUpUseCase;
import com.customer.domain.repository.ForgotPasswordRepository;
import com.customer.domain.repository.ResetPasswordRepository;
import com.customer.domain.repository.SignUpRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class EcomForgotPasswordViewModule {
  @Provides
  ForgotPasswordRepository provideRepository(ForgotPasswordRepositoryImpl signInRepository) {
    return signInRepository;
  }

  @Provides
  public UseCase<ForgotPasswordUseCase.RequestValues, ForgotPasswordUseCase.ResponseValues> getLoginDetailsUseCase(
      ForgotPasswordUseCase signInUseCase) {
    return signInUseCase;
  }

  @Provides
  ResetPasswordRepository provideVerifyOTPRepository(
      ResetPasswordRepositoryImpl verifyOTPRepository) {
    return verifyOTPRepository;
  }

  @Provides
  public UseCase<ResetPasswordUseCase.RequestValues, ResetPasswordUseCase.ResponseValues> getVerifyOtpUseCase(
      ResetPasswordUseCase signInUseCase) {
    return signInUseCase;
  }

  @Provides
  SignUpRepository signUpRepository(SignUpRepositoryImpl signInRepository) {
    return signInRepository;
  }

  @Provides
  public UseCase<SignUpUseCase.RequestValues, SignUpUseCase.ResponseValues> signUp(
      SignUpUseCase signUpUseCase) {
    return signUpUseCase;
  }
}
