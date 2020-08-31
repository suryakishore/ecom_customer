package com.customer.domain.repository;

import com.customer.domain.interactor.UpdateProfileUseCase;
import io.reactivex.Single;

public interface UpdateProfileRepository {
  Single<UpdateProfileUseCase.ResponseValues> updateProfile(String firstName, String lastName,
      String countryCode,
      String mobNum, String email);

  Single<UpdateProfileUseCase.ResponseValues> updateProfilePic(String profilePick);
}
