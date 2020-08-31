package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.historyfilter.HistoryOrderFilterViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
@Module
public abstract class HistoryOrderFilterModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(HistoryOrderFilterViewModel.class)
  protected abstract ViewModel productFilterViewModel(
      HistoryOrderFilterViewModel historyOrderFilterViewModel);
}
