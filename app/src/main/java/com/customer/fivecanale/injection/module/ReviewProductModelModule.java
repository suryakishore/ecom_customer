package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.review.ReviewProductViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ReviewProductModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(ReviewProductViewModel.class)
  protected abstract ViewModel reviewViewModel(ReviewProductViewModel reviewProductViewModel);
}
