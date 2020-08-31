package com.customer.domain.repository;

import com.customer.domain.interactor.TrackingOrderUseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import io.reactivex.Single;

public interface WalletAmtRepository {
  Single<WalletAmtUseCase.ResponseValues> walletAmt(String userType);

}
