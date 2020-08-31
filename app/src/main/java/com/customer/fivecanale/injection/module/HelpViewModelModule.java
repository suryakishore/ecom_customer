package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.allbrands.AllBrandsViewModel;
import com.customer.fivecanale.help.HelpViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HelpViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(HelpViewModel.class)
  protected abstract ViewModel homeViewModel(HelpViewModel catViewMoreViewModel);

}
