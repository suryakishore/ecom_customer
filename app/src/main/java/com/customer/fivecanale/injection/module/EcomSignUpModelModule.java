package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.boarding.register.EcomSignUpViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomSignUpModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(EcomSignUpViewModel.class)
  protected abstract ViewModel homeViewModel(EcomSignUpViewModel ecomSignUpViewModel);
}
