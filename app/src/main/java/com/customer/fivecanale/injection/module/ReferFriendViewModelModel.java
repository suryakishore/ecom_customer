package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.referfriend.ReferFriendViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ReferFriendViewModelModel extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(ReferFriendViewModel.class)
  protected abstract ViewModel homeViewModel(ReferFriendViewModel productListViewModel);
}
