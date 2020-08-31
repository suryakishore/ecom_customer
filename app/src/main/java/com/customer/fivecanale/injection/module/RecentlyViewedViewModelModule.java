package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.recentlyviewed.RecentlyViewedViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RecentlyViewedViewModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(RecentlyViewedViewModel.class)
  protected abstract ViewModel recentlyViewed(RecentlyViewedViewModel profileViewModel);
}
