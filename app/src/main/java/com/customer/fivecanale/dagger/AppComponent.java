package com.customer.fivecanale.dagger;

import android.app.Application;
import com.customer.delivX.dagger.ApplicationScoped;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.injection.CleanArchAppModule;
import com.customer.fivecanale.injection.handler.PermissionHandlerModule;
import com.customer.fivecanale.injection.handler.UserInfoModule;
import com.customer.fivecanale.injection.usecasemodule.AppConfigUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GenerateTokenUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.LogoutUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.VersionUseCaseModule;
import com.customer.fivecanale.splash.SplashUseCaseModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

/**
 * <h1>AppComponent</h1>
 *
 * Interface is used to provide the app component
 */
@ApplicationScoped
@Singleton
@Component(
    modules = {
        AppModule.class,
        UtilityModule.class,
        ActivityBindingModule.class,
        CleanArchAppModule.class,
        AndroidSupportInjectionModule.class,
        UserInfoModule.class,
        PermissionHandlerModule.class,
        VerifyOtpModule.class,
        PdpModule.class,
        SendOtpModule.class,
        VersionUseCaseModule.class,
        AppConfigUseCaseModule.class,
        GenerateTokenUseCaseModule.class,
        LogoutUseCaseModule.class,
        SplashUseCaseModule.class
    })
public interface AppComponent extends AndroidInjector<DaggerApplication> {
  void inject(ApplicationManager application);

  @Override
  void inject(DaggerApplication instance);

  // Gives us syntactic sugar. we can then do
  // DaggerAppComponent.builder().application(this).build().inject(this);
  // never having to instantiate any modules or say which module we are passing the application to.
  // Application will just be provided into our app graph now.
  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }
}
