package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.allreviews.AllReviewsViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomAllReviewsModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(AllReviewsViewModel.class)
  protected abstract ViewModel homeViewModel(AllReviewsViewModel ecomLoginViewModel);
}
