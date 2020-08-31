package com.customer.domain.repository;

import com.customer.domain.interactor.WalletAddMoneyUseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import io.reactivex.Single;

public interface WalletAddMoneyRepository {
  Single<WalletAddMoneyUseCase.ResponseValues> walletAddMoney(String cardId,String currency,String amount);
}
