package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.HomeModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
@Module
public abstract class HomeViewModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(HomeModel.class)
  protected abstract ViewModel homeViewModel(HomeModel homeModel);
}
