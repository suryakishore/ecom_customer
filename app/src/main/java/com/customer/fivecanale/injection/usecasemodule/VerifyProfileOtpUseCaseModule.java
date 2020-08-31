package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.SendOtpToUpdateProfileRepositoryImpl;
import com.customer.data.repository.VerifyProfileOtpRepositoryImpl;
import com.customer.domain.interactor.SendOtpToUpdateProfileUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.VerifyProfileOtpUseCase;
import com.customer.domain.repository.SendOtpToUpdateProfileRepository;
import com.customer.domain.repository.VerifyProfileOtpRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class VerifyProfileOtpUseCaseModule {
  @Provides
  public VerifyProfileOtpRepository provideRepository(
      VerifyProfileOtpRepositoryImpl profileDetailsRepository) {
    return profileDetailsRepository;
  }

  @Provides
  public UseCase<VerifyProfileOtpUseCase.RequestValues, VerifyProfileOtpUseCase.ResponseValues> getseCase(
      VerifyProfileOtpUseCase getProfileDetailsUseCase) {
    return getProfileDetailsUseCase;
  }
}
