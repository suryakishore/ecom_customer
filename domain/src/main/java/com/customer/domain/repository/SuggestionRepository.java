package com.customer.domain.repository;

import com.customer.domain.interactor.GetSuggestionUseCase;
import io.reactivex.Single;

public interface SuggestionRepository {
  Single<GetSuggestionUseCase.ResponseValues> suggestion(String searchItem);

}
