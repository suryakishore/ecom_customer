package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.productlist.ProductListViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProductListViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(ProductListViewModel.class)
  protected abstract ViewModel homeViewModel(ProductListViewModel productListViewModel);
}