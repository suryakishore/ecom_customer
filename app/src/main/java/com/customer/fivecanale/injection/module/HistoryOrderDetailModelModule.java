package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.orderdetails.HistoryOrderDetailViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HistoryOrderDetailModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(HistoryOrderDetailViewModel.class)
  protected abstract ViewModel historyViewModel(HistoryOrderDetailViewModel ecomLoginViewModel);
}
