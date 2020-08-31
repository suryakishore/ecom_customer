package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.manageaddress.AddAddressViewModel;
import com.customer.fivecanale.webview.WebViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WebViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(WebViewModel.class)
  protected abstract ViewModel homeViewModel(WebViewModel webViewModel);
}
