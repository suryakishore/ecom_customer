package com.customer.fivecanale.injection.module;

import com.customer.fivecanale.dagger.FragmentScoped;
import com.customer.fivecanale.pdp.attributebottomsheet.AttributesBottomSheetFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AttributesBottomSheetDaggerModule {

  @FragmentScoped
  @ContributesAndroidInjector
  abstract AttributesBottomSheetFragment bindBottomSheet();


}