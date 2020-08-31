package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.cartscreen.CartViewModel;
import com.customer.fivecanale.tracking.EcomTrackingViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomTrackViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(EcomTrackingViewModel.class)
  protected abstract ViewModel cartViewModel(EcomTrackingViewModel cartViewModel);
}
