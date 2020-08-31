package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.uiutil.barcodescanning.BarCodeViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class BarCodePreviewViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(BarCodeViewModel.class)
  protected abstract ViewModel homeViewModel(BarCodeViewModel catViewMoreViewModel);

}
