package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.changelang.ChangeLanViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ChangeLanViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(ChangeLanViewModel.class)
  protected abstract ViewModel homeViewModel(ChangeLanViewModel catViewMoreViewModel);

}
