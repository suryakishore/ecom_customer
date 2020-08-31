package com.customer.fivecanale.uiutil.barcodescanning.bottomsheet;

import com.customer.fivecanale.dagger.FragmentScoped;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductNotFoundBtmSheetModule {
  @FragmentScoped
  @ContributesAndroidInjector
  abstract ProductNotFoundBottomSheet productNotFoundBottomSheet();
}
