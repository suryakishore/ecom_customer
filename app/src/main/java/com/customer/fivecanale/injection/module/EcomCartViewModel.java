package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.boarding.login.EcomLoginViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.cartscreen.CartViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomCartViewModel  extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(CartViewModel.class)
  protected abstract ViewModel cartViewModel(CartViewModel cartViewModel);
}
