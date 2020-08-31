package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetSellerListRepositoryImpl;
import com.customer.data.repository.VerifyProfileOtpRepositoryImpl;
import com.customer.domain.interactor.GetSellerListUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.VerifyProfileOtpUseCase;
import com.customer.domain.repository.GetSellerListRepository;
import com.customer.domain.repository.VerifyProfileOtpRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ViewMoreSellersUseCaseModule {
  @Provides
  public GetSellerListRepository provideRepository(
      GetSellerListRepositoryImpl viewMoreRepository) {
    return viewMoreRepository;
  }

  @Provides
  public UseCase<GetSellerListUseCase.RequestValues, GetSellerListUseCase.ResponseValues> getCase(
      GetSellerListUseCase getProfileDetailsUseCase) {
    return getProfileDetailsUseCase;
  }
}
