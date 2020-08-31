package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.cancelorder.CancelOrderOrProductViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
@Module
public abstract class CancelOrderViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(CancelOrderOrProductViewModel.class)
  protected abstract ViewModel historyViewModel(CancelOrderOrProductViewModel catViewMoreViewModel);
}
