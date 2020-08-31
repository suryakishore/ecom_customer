package com.customer.fivecanale.wallet;

import static com.customer.fivecanale.util.EcomConstants.ALL_WALLET;
import static com.customer.fivecanale.util.EcomConstants.CREDIT_WALLET;
import static com.customer.fivecanale.util.EcomConstants.DEBIT_WALLET;

import com.customer.fivecanale.dagger.ActivityScoped;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module
public class EcomWalletModule {

  @Provides
  @Named(ALL_WALLET)
  @ActivityScoped
  EcomWalletFragment provideEcomAllFragment() {
    return new EcomWalletFragment();
  }

  @Provides
  @Named(DEBIT_WALLET)
  @ActivityScoped
  EcomWalletFragment provideEcomDebitFragment() {
    return new EcomWalletFragment();
  }

  @Provides
  @Named(CREDIT_WALLET)
  @ActivityScoped
  EcomWalletFragment provideEcomCreditFragment() {
    return new EcomWalletFragment();
  }

}
