package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.filter.ProductFilterViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProductFilterModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(ProductFilterViewModel.class)
  protected abstract ViewModel productFilterViewModel(
      ProductFilterViewModel productFilterViewModel);
}
