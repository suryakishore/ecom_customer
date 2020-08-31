package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateProfileViewModel;
import com.customer.fivecanale.wallet.WalletModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WalletModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(WalletModel.class)
  protected abstract ViewModel productFilterViewModel(
      WalletModel updateProfileViewModel);
}

