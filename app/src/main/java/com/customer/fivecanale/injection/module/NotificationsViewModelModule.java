package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.manageaddress.AddAddressViewModel;
import com.customer.fivecanale.notifications.NotificationsViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NotificationsViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(NotificationsViewModel.class)
  protected abstract ViewModel homeViewModel(NotificationsViewModel catViewMoreViewModel);
}
