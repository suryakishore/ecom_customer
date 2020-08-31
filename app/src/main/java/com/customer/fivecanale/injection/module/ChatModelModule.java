package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.mqtt_chat.ChatViewModel;
import com.customer.fivecanale.search.CategoryViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ChatModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(ChatViewModel.class)
  protected abstract ViewModel reviewViewModel(ChatViewModel reviewProductViewModel);
}
