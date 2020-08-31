package com.customer.fivecanale.injection.module;

import com.customer.fivecanale.dagger.FragmentScoped;
import com.customer.fivecanale.sort.SortBottomSheet;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SortBottomSheetModule {

  @FragmentScoped
  @ContributesAndroidInjector
  abstract SortBottomSheet bindSortBottomSheet();
}
