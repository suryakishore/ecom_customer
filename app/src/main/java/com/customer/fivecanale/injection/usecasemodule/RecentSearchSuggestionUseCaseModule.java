package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetRecentSearchSuggestionRepositoryImpl;
import com.customer.domain.interactor.GetRecentSearchSuggestionUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetRecentSearchSuggestionRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class RecentSearchSuggestionUseCaseModule {

  @Provides
  public UseCase<GetRecentSearchSuggestionUseCase.RequestValues,
      GetRecentSearchSuggestionUseCase.ResponseValues> getLoginDetailsUseCase(
      GetRecentSearchSuggestionUseCase recentSearchSuggestionUseCase) {
    return recentSearchSuggestionUseCase;
  }

  @Provides
  GetRecentSearchSuggestionRepository provideRepository(
      GetRecentSearchSuggestionRepositoryImpl getRecentSearchSuggestionRepository) {
    return getRecentSearchSuggestionRepository;
  }
}
