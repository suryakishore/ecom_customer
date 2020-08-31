package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.WalletAmtRepositoryImpl;
import com.customer.data.repository.WalletTransactionsRepositoryImpl;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import com.customer.domain.interactor.WalletTransactionsUseCase;
import com.customer.domain.repository.WalletAmtRepository;
import com.customer.domain.repository.WalletTransactionsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class WalletTransactionsUseCaseModule {

  @Provides
  WalletTransactionsRepository provideRepository(
      WalletTransactionsRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<WalletTransactionsUseCase.RequestValues, WalletTransactionsUseCase.ResponseValues> getWalletAmtUseCase(
      WalletTransactionsUseCase walletAmtUseCase) {
    return walletAmtUseCase;
  }
}
