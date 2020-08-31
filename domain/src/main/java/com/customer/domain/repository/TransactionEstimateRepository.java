package com.customer.domain.repository;

import com.customer.domain.interactor.TransactionEstimateUseCase;
import io.reactivex.Single;

public interface TransactionEstimateRepository {
  Single<TransactionEstimateUseCase.ResponseValues> walletTransactions(String fromCurrency,
      String toCurrency, float amount);
}
