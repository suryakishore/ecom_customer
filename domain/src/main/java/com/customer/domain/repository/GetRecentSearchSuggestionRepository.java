package com.customer.domain.repository;

import com.customer.domain.interactor.GetRecentSearchSuggestionUseCase;
import io.reactivex.Single;

public interface GetRecentSearchSuggestionRepository {

  Single<GetRecentSearchSuggestionUseCase.ResponseValues> getRecentSearchSuggestion();
}
