package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.allbrands.AllBrandsViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.promocode.PromoCodesViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class PromoCodesViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(PromoCodesViewModel.class)
  protected abstract ViewModel homeViewModel(PromoCodesViewModel promoCodesViewModel);

}
