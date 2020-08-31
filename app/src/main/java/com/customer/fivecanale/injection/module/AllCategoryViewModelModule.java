package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.allcategory.AllCategoryViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AllCategoryViewModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(AllCategoryViewModel.class)
  protected abstract ViewModel homeViewModel(AllCategoryViewModel allCategoryViewModel);
}
