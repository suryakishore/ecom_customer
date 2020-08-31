package com.customer.domain.repository;

import com.customer.domain.interactor.WalletTransactionsUseCase;
import io.reactivex.Single;

public interface WalletTransactionsRepository {
  Single<WalletTransactionsUseCase.ResponseValues> walletTransactions(String walletId,
      int fetchSize, String pageState);
}
