package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.AddAddressRepositoryImpl;
import com.customer.data.repository.WalletAmtRepositoryImpl;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import com.customer.domain.repository.AddAddressRepository;
import com.customer.domain.repository.WalletAmtRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class WalletAmtUseCaseModule {

  @Provides
  WalletAmtRepository provideRepository(WalletAmtRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<WalletAmtUseCase.RequestValues, WalletAmtUseCase.ResponseValues> getWalletAmtUseCase(
      WalletAmtUseCase walletAmtUseCase) {
    return walletAmtUseCase;
  }
}
