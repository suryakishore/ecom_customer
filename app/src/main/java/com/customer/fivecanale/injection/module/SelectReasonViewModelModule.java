package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.selectreason.SelectReasonModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SelectReasonViewModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(SelectReasonModel.class)
  protected abstract ViewModel historyViewModel(SelectReasonModel catViewMoreViewModel);
}
