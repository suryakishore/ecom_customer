package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.trending.TrendingProductsViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class TrendingProductsViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(TrendingProductsViewModel.class)
  protected abstract ViewModel homeViewModel(TrendingProductsViewModel productListViewModel);

}
