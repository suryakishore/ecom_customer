package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.WalletAddMoneyRepositoryImpl;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.WalletAddMoneyUseCase;
import com.customer.domain.repository.WalletAddMoneyRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class WalletAddAmountUseCaseModule {
  @Provides
  WalletAddMoneyRepository provideRepository(
      WalletAddMoneyRepositoryImpl walletAddMoneyRepository) {
    return walletAddMoneyRepository;
  }

  @Provides
  public UseCase<WalletAddMoneyUseCase.RequestValues, WalletAddMoneyUseCase.ResponseValues> getWalletAmtUseCase(
      WalletAddMoneyUseCase walletAddMoneyUseCase) {
    return walletAddMoneyUseCase;
  }
}
