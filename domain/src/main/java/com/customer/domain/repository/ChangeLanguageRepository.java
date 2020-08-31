package com.customer.domain.repository;

import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.interactor.ChangeLanguageUseCase;
import io.reactivex.Single;

public interface ChangeLanguageRepository {
  Single<ChangeLanguageUseCase.ResponseValues> changeLanguage();

}
