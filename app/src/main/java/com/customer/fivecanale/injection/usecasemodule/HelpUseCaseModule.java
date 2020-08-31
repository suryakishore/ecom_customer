package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.ChangeLanguageRepositoryImpl;
import com.customer.data.repository.HelpRepositoryImpl;
import com.customer.domain.interactor.ChangeLanguageUseCase;
import com.customer.domain.interactor.HelpUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.ChangeLanguageRepository;
import com.customer.domain.repository.HelpRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class HelpUseCaseModule {
  @Provides
  HelpRepository provideRepository(HelpRepositoryImpl helpRepository) {
    return helpRepository;
  }

  @Provides
  public UseCase<HelpUseCase.RequestValues, HelpUseCase.ResponseValues> provideUseCase(
      HelpUseCase filterUseCase) {
    return filterUseCase;
  }
}
