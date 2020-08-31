package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AllPromoCodesRepositoryImpl;
import com.customer.data.repository.ApplyPromoCodesRepositoryImpl;
import com.customer.domain.interactor.ApplyPromoCodesUseCase;
import com.customer.domain.interactor.GetAllPromoCodesUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AllPromoCodesRepository;
import com.customer.domain.repository.ApplyPromoCodeRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplyPromoCodesUseCaseModule {
  @Provides
  ApplyPromoCodeRepository provideRepository(ApplyPromoCodesRepositoryImpl applyPromoCodesRepository) {
    return applyPromoCodesRepository;
  }
  @Provides
  public UseCase<ApplyPromoCodesUseCase.RequestValues, ApplyPromoCodesUseCase.ResponseValues> provideUseCase(
      ApplyPromoCodesUseCase promoCodesUseCase) {
    return promoCodesUseCase;
  }
}
