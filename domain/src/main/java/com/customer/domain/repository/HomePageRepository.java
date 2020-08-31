package com.customer.domain.repository;

import com.customer.domain.interactor.HomePageUseCase.ResponseValues;
import io.reactivex.Single;

public interface HomePageRepository {

  Single<ResponseValues> homePage();
}
