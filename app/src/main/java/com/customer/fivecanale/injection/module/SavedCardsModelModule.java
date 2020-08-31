package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.savedcards.SavedCardViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SavedCardsModelModule extends ViewModelFactory {

  @Binds
  @IntoMap
  @ViewModelKey(SavedCardViewModel.class)
  protected abstract ViewModel savedCardsViewModel(SavedCardViewModel savedCardViewModel);
}

