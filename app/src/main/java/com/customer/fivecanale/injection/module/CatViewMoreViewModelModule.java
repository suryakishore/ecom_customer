package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.category.CatViewMoreViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CatViewMoreViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(CatViewMoreViewModel.class)
  protected abstract ViewModel homeViewModel(CatViewMoreViewModel catViewMoreViewModel);

}
