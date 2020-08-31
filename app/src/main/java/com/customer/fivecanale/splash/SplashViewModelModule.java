package com.customer.fivecanale.splash;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SplashViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(SplashViewModel.class)
  protected abstract ViewModel splashViewModelProvider(SplashViewModel homeViewModel);

}
