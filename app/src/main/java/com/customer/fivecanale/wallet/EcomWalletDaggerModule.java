package com.customer.fivecanale.wallet;

import android.app.Activity;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.dagger.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EcomWalletDaggerModule {
  @ActivityScoped
  @Binds
  abstract Activity getActivity(EcomWalletActivity ecomWalletActivity);

  @FragmentScoped
  @ContributesAndroidInjector
  abstract EcomWalletFragment ecomWalletFragment();
}
