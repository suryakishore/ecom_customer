package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.addmoney.AddMoneyViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WalletAddMoneyModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(AddMoneyViewModel.class)
  protected abstract ViewModel walletAddMoney(
      AddMoneyViewModel updateProfileViewModel);
}

