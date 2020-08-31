package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.preview.PreviewImageViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class PreviewImageModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(PreviewImageViewModel.class)
  protected abstract ViewModel homeViewModel(PreviewImageViewModel ecomLoginViewModel);
}
