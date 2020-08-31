package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.SuggestionRepositoryImpl;
import com.customer.domain.interactor.GetSuggestionUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.SuggestionRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class SuggestionsUseCaseModule {
  @Provides
  SuggestionRepository provideRepository(SuggestionRepositoryImpl suggestionRepository) {
    return suggestionRepository;
  }

  @Provides
  public UseCase<GetSuggestionUseCase.RequestValues, GetSuggestionUseCase.ResponseValues> getLoginDetailsUseCase(
      GetSuggestionUseCase suggestionUseCase) {
    return suggestionUseCase;
  }
}
