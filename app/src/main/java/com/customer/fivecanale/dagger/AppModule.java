package com.customer.fivecanale.dagger;

import android.app.Application;
import android.content.Context;
import dagger.Binds;
import dagger.Module;

/**
 * <h1>AppModule</h1>
 * Used to inject the dependency
 *
 * @author 3Embed
 * @since 03-Nov-17
 */

@Module
abstract class AppModule {
  //expose Application as an injectable context
  @Binds
  abstract Context bindContext(Application application);
}
