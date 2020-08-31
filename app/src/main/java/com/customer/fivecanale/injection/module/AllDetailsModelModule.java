package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.alldetails.AllDetailsViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AllDetailsModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(AllDetailsViewModel.class)
  protected abstract ViewModel allDetailsViewModel(AllDetailsViewModel allDetailsViewModel);
}
