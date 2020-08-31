package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetProfileDetailsRepositoryImpl;
import com.customer.data.repository.SendOtpToUpdateProfileRepositoryImpl;
import com.customer.domain.interactor.GetProfileDetailsUseCase;
import com.customer.domain.interactor.SendOtpToUpdateProfileUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetProfileDetailsRepository;
import com.customer.domain.repository.SendOtpToUpdateProfileRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class SendOtpToUpdateProfileUseCaseModule {

  @Provides
  public SendOtpToUpdateProfileRepository provideRepository(
      SendOtpToUpdateProfileRepositoryImpl profileDetailsRepository) {
    return profileDetailsRepository;
  }

  @Provides
  public UseCase<SendOtpToUpdateProfileUseCase.RequestValues, SendOtpToUpdateProfileUseCase.ResponseValues> getseCase(
      SendOtpToUpdateProfileUseCase getProfileDetailsUseCase) {
    return getProfileDetailsUseCase;
  }
}
