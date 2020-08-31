package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.manageaddress.AddAddressViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AddAddressViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(AddAddressViewModel.class)
  protected abstract ViewModel homeViewModel(AddAddressViewModel catViewMoreViewModel);
}
