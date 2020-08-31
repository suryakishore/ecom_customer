package com.customer.fivecanale.dagger;

import android.content.Context;
import com.customer.fivecanale.network.MQTTManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class UtilityModule {


  @Provides
  @Singleton
  MQTTManager getMqtt(Context context) {
    return new MQTTManager(context);
  }
}
