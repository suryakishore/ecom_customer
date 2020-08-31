package com.customer.domain.repository;

import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.domain.interactor.GenerateTokenUseCase;
import io.reactivex.Single;

public interface GenerateTokenRepository {

  Single<GenerateTokenUseCase.ResponseValues> generateToken();
}
