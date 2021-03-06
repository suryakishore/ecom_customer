package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.wishlist.WishListViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WishListViewModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(WishListViewModel.class)
  protected abstract ViewModel productFilterViewModel(
      WishListViewModel updateProfileViewModel);

}
