package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.profile.ProfileViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProfileViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(ProfileViewModel.class)
  protected abstract ViewModel profileViewModel(ProfileViewModel profileViewModel);
}
