package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.review.ReviewProductViewModel;
import com.customer.fivecanale.search.CategoryViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CategoryModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(CategoryViewModel.class)
  protected abstract ViewModel reviewViewModel(CategoryViewModel reviewProductViewModel);
}
