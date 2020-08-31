package com.customer.domain.repository;

import com.customer.domain.interactor.ResetPasswordUseCase.ResponseValues;
import io.reactivex.Single;

public interface ResetPasswordRepository {

  Single<ResponseValues> resetPassword(String newPassword, String oldPassword, int resetType);
}
