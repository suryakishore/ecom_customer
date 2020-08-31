package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.profile.profiledetails.ProfileDetailsViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProfileDetailsViewModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(ProfileDetailsViewModel.class)
  protected abstract ViewModel productFilterViewModel(
      ProfileDetailsViewModel productFilterViewModel);
}
