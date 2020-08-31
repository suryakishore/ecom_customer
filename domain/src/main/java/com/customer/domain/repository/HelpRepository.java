package com.customer.domain.repository;

import com.customer.domain.interactor.ChangeLanguageUseCase;
import com.customer.domain.interactor.HelpUseCase;
import io.reactivex.Single;

public interface HelpRepository {
  Single<HelpUseCase.ResponseValues> help();
}
