package com.customer.domain.repository;

import com.customer.domain.interactor.GetProfileDetailsUseCase;
import io.reactivex.Single;

public interface GetProfileDetailsRepository {

  Single<GetProfileDetailsUseCase.ResponseValues> getProfileData();
}
