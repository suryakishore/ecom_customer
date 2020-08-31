package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.historyproductdetail.HistoryProductDetailViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HistoryProductDetailViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(HistoryProductDetailViewModel.class)
  protected abstract ViewModel historyViewModel(HistoryProductDetailViewModel catViewMoreViewModel);
}
