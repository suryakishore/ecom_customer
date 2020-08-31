package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.addresslist.SavedAddressListViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SavedAddressListViewModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(SavedAddressListViewModel.class)
  protected abstract ViewModel homeViewModel(SavedAddressListViewModel productListViewModel);
}
