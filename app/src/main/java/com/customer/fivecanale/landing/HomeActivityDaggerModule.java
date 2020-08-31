package com.customer.fivecanale.landing;

import android.app.Activity;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.dagger.FragmentScoped;
import com.customer.fivecanale.landing.cartscreen.EcomCartFragment;
import com.customer.fivecanale.landing.historyscreen.HistoryScreenFragment;
import com.customer.fivecanale.landing.homescreen.HomeFragment;
import com.customer.fivecanale.landing.profile.EcomProfileFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module /*(includes = { ViewModelModule.class, HomeViewModule.class})*/
public abstract class HomeActivityDaggerModule {
  @ActivityScoped
  @Binds
  abstract Activity getActivity(HomeActivity homeActivity);

  @FragmentScoped
  @ContributesAndroidInjector
  abstract EcomCartFragment getCartScreenFragment();

  @FragmentScoped
  @ContributesAndroidInjector
  abstract HistoryScreenFragment getHistoryScreenFragment();

  @FragmentScoped
  @ContributesAndroidInjector
  abstract EcomProfileFragment getEcomProfileFragment();

  @FragmentScoped
  @ContributesAndroidInjector
  abstract HomeFragment getHomeScreenFragment();
}
