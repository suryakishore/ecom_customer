package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.boarding.forgotpassword.EcomForgotPasswordViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomForgotPasswordModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(EcomForgotPasswordViewModel.class)
  protected abstract ViewModel homeViewModel(EcomForgotPasswordViewModel ecomLoginViewModel);
}
