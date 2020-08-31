package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.payment.PaymentMethodViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class PaymentMethodModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(PaymentMethodViewModel.class)
  protected abstract ViewModel reviewViewModel(PaymentMethodViewModel paymentMethodModel);
}
