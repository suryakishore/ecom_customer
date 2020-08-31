package com.customer.fivecanale.cart;

import android.app.Activity;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.dagger.FragmentScoped;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.landing.cartscreen.EcomCartFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EcomCartDaggerModule {
  @ActivityScoped
  @Binds
  abstract Activity getActivity(EcomCartActivity ecomCartActivity);

  @FragmentScoped
  @ContributesAndroidInjector
  abstract EcomCartFragment getCartScreenFragment();

}
