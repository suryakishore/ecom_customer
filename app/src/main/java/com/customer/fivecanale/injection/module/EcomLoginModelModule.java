package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.boarding.login.EcomLoginViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomLoginModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(EcomLoginViewModel.class)
  protected abstract ViewModel homeViewModel(EcomLoginViewModel ecomLoginViewModel);
}
