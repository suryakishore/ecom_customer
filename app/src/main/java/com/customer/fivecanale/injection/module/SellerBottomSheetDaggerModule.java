package com.customer.fivecanale.injection.module;

import com.customer.fivecanale.dagger.FragmentScoped;
import com.customer.fivecanale.pdp.attributebottomsheet.AttributesBottomSheetFragment;
import com.customer.fivecanale.pdp.sellerbottomsheet.SellerBottomSheetFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SellerBottomSheetDaggerModule {
  @FragmentScoped
  @ContributesAndroidInjector
  abstract SellerBottomSheetFragment bindBottomSheet();
}
