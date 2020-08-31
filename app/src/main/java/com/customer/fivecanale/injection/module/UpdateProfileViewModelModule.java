package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateProfileViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class UpdateProfileViewModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(UpdateProfileViewModel.class)
  protected abstract ViewModel productFilterViewModel(
      UpdateProfileViewModel updateProfileViewModel);
}
