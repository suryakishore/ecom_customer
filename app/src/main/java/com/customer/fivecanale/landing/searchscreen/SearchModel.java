package com.customer.fivecanale.landing.searchscreen;

import com.customer.fivecanale.dagger.FragmentScoped;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchModel {

  @FragmentScoped
  @ContributesAndroidInjector
  abstract SearchScreenFragment getSearchFragment();
}
