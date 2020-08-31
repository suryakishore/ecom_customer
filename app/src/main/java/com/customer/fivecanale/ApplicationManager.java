package com.customer.fivecanale;

import static com.customer.fivecanale.util.EcomConstants.ANDROID_CUSTOMER;
import static com.customer.fivecanale.util.EcomConstants.BROWSER_NAME;
import static com.customer.fivecanale.util.EcomConstants.BROWSER_VERSION;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAN;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAT;
import static com.customer.fivecanale.util.EcomConstants.DEVICE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PLAY_STORE_URL;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.VERSION_UPDATE_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import com.BuildConfig;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.AppConfigUseCase;
import com.customer.domain.interactor.GenerateTokenUseCase;
import com.customer.domain.interactor.VersionUseCase;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.dagger.AppComponent;
import com.customer.fivecanale.dagger.DaggerAppComponent;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.network.MQTTManager;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtilBuilder;
import com.customer.fivecanale.util.EcomUtil;
import com.facebook.FacebookSdk;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import okhttp3.OkHttpClient;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class ApplicationManager extends DaggerApplication implements
    Application.ActivityLifecycleCallbacks, CustomDialogUtilBuilder.VersionClickListener {
  public static final long CHECK_DELAY = 500;
  public static final String TAG = "ApplicationManager";
  public static boolean foreground = false, paused = true;
  static ApplicationManager applicationManager;

  static {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  public String mVersion = "";
  OkHttpClient client = new OkHttpClient();
  @Inject
  MQTTManager mqttManager;
  @Inject
  UseCaseHandler useCaseHandler;
  @Inject
  VersionUseCase versionUseCase;
  @Inject
  AppConfigUseCase appConfigUseCase;
  @Inject
  GenerateTokenUseCase generateTokenUseCase;
  @Inject
  GuestApiUseCase guestApiUseCase;
  @Inject
  UserInfoHandler userInfoHandler;
  boolean alreadyAttemptingToConnectMqtt = false;
  private boolean mAppForeground = false;
  private boolean mApplicationKilled = false;
  private Runnable check;
  private Handler handler = new Handler();
  private boolean bound = false;
  private long mExpireTime;

  public static ApplicationManager getInstance() {
    return applicationManager;
  }

  public boolean isApplicationKilled() {
    return mApplicationKilled;
  }

  public void setApplicationKilled(boolean applicationKilled) {
    this.mApplicationKilled = applicationKilled;
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
    appComponent.inject(this);
    return appComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    applicationManager = this;
    FacebookSdk.sdkInitialize(this);
    registerActivityLifecycleCallbacks(this);
    connectMqTT();
    onGenerateTokenUpdate();
    onTokenUpdate();
    Log.i(TAG, "appConfigUseCase" + versionUseCase);
    try {
      PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
      mVersion = info.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * this method is used to connect to the mqtt whenever user has open the app every time..
   */
  public void connectMqTT() {
    if (userInfoHandler.isUserLoggedIn()) {
      String clientId = String.format("%s_%s", getString(R.string.app_name),
          EcomUtil.getDeviceId(this));
      EcomUtil.printLog("mqtt client " + clientId);
      mqttManager.createMQttConnection(clientId, userInfoHandler.getMqttTopic(),userInfoHandler.userId());
    }
  }

  /** This method is using for subscribing to User generate token data */
  void onGenerateTokenUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        userInfoHandler
            .getGenerateTokenObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(request -> {
              if (request != null) {
                EcomUtil.printLog("exe" + "regenerate" + request);
                callGenerateToken();
              }
            }));
  }

  /** This method is using for subscribing to User logout data */
  void onTokenUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        userInfoHandler
            .getLogoutObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(request -> {
              if (request != null) {
                EcomUtil.printLog("exe" + "logout" + request);
                userInfoHandler.clearData();
                callGuestSignAPI();
              }
            }));
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  /**
   * <h2>addAccount</h2>
   * This method is used to set the auth token by creating the account manager with the account
   *
   * @param emailID email ID to be added
   */
  public void setAuthToken(String emailID, String password, String authToken) {
  }

  /*
  <h2>getAuthToken</h2>
  This method is used to getProductList the auth token from the created account
  @return  auth token stored
  */
  public String getAuthToken(String emailID) {
    return null;
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {
    if (activity.getClass().getSimpleName().equals("SplashActivity")) {
      setApplicationKilled(false);
      if (!alreadyAttemptingToConnectMqtt) {
      }
    }
  }

  @Override
  public void onActivityStarted(Activity activity) {
  }

  @Override
  public void onActivityResumed(Activity activity) {
    // paused = false;
    boolean wasBackground = !foreground;
    foreground = true;
    if (check != null) {
      handler.removeCallbacks(check);
    }
    handler.postDelayed(check = () -> {
      if (mAppForeground && foreground && paused) {
        paused = false;
        mAppForeground = false;
        callUpdateVersionApi(activity);
        boolean isTimerFinished =
            (System.currentTimeMillis() - userInfoHandler.getSessionTime()) / THOUSAND > mExpireTime
                ? TRUE : FALSE;
        callGetAppConfigApi(activity, isTimerFinished ? ZERO : ONE);
        Log.i(TAG, "splash_foreground  act count" + activity);
      } else {
        Log.i(TAG, "still splash_foreground act count" + foreground);
      }
    }, CHECK_DELAY);
    Log.i(TAG, "onActivityResumed" + activity.getLocalClassName());
  }

  @Override
  public void onActivityPaused(Activity activity) {
    paused = true;
    if (check != null) {
      handler.removeCallbacks(check);
    }
    handler.postDelayed(check = () -> {
      if (foreground && paused) {
        foreground = false;
        mAppForeground = true;
        Log.i(TAG, "splash_background act count");
        userInfoHandler.setSessionTime(System.currentTimeMillis());
      } else {
        Log.i(TAG, "still splash_background act count" + foreground);
      }
    }, CHECK_DELAY);
    Log.i(TAG, "onActivityPaused" + activity.getLocalClassName());
  }

  @Override
  public void onActivityStopped(Activity activity) {
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
  }

  /**
   * call the app config  api
   */
  public void callUpdateVersionApi(Activity activity) {
    Log.i(TAG, "callUpdateVersionApi" + activity.getLocalClassName());
    DisposableSingleObserver<VersionUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<VersionUseCase.ResponseValues>() {
          @Override
          public void onSuccess(VersionUseCase.ResponseValues responseValues) {
            if (responseValues.getData() != null) {
              EcomUtil.printLog("Version Succ" + responseValues.getData().getVersion());
              EcomUtil.printLog("Version Succ" + responseValues.getData().isMandatory());
              if (!mVersion.equals(responseValues.getData().getVersion())) {
                if (responseValues.getData().isMandatory()) {
                  showUpdateDialog(activity, responseValues.getData().isMandatory());
                }
              }
            }
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("Version Fail" + e.getMessage());
          }
        };
    useCaseHandler.execute(versionUseCase,
        new VersionUseCase.RequestValues(ANDROID_CUSTOMER, mVersion),
        disposableSingleObserver);
  }

  /**
   * call the app config  api
   */
  public void callGetAppConfigApi(Activity activity, int backGroundFlag) {
    if (activity instanceof HomeActivity) {
      ((HomeActivity) activity).showProgress(TRUE);
    }
    DisposableSingleObserver<AppConfigUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<AppConfigUseCase.ResponseValues>() {
          @Override
          public void onSuccess(AppConfigUseCase.ResponseValues responseValues) {
            if (activity instanceof HomeActivity) {
              ((HomeActivity) activity).showProgress(FALSE);
              ((HomeActivity) activity).loadFragment();
              if (responseValues.getData().getExpireTime() != null) {
                mExpireTime = Long.parseLong(responseValues.getData().getExpireTime());
              }
              EcomUtil.printLog("exe" + "expireTime" + mExpireTime);
            }
          }

          @Override
          public void onError(Throwable e) {
            if (activity instanceof HomeActivity) {
              ((HomeActivity) activity).showProgress(FALSE);
              ((HomeActivity) activity).loadFragment();
            }
            EcomUtil.printLog("AppConfig Fail" + e.getMessage());
          }
        };
    useCaseHandler.execute(appConfigUseCase,
        new AppConfigUseCase.RequestValues(backGroundFlag),
        disposableSingleObserver);
  }

  /**
   * call generate token  api
   */
  public void callGenerateToken() {
    DisposableSingleObserver<GenerateTokenUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GenerateTokenUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GenerateTokenUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("Generate token Succ" + responseValues);
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("Generate token Fail" + e.getMessage());
          }
        };
    useCaseHandler.execute(generateTokenUseCase,
        new GenerateTokenUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * call guest sign in api.
   */
  void callGuestSignAPI() {
    DisposableSingleObserver<GuestApiUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GuestApiUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GuestApiUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("HomeAPi Success");
            Intent intent = new Intent(ApplicationManager.getInstance(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("HomeAPi Fail" + e.getMessage());
          }
        };
    useCaseHandler.execute(guestApiUseCase,
        new GuestApiUseCase.RequestValues(EcomUtil.getDeviceId(ApplicationManager.getInstance()),
            String.valueOf(Build.VERSION.SDK_INT), DEVICE_TYPE, Build.MANUFACTURER, Build.MODEL,
            mVersion,
            EcomUtil.getIpAddress(ApplicationManager.getInstance()), BROWSER_NAME, BROWSER_VERSION,
            String.valueOf(System.currentTimeMillis()), String.valueOf(CURRENT_LAT),
            String.valueOf(CURRENT_LAN)), disposableSingleObserver);
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showUpdateDialog(Activity activity, boolean isUpdateMandatory) {
    CustomDialogUtilBuilder.CustomDialogBuilder customDialogUtilBuilder =
        new CustomDialogUtilBuilder.CustomDialogBuilder(activity,
            (CustomDialogUtilBuilder.VersionClickListener) this, VERSION_UPDATE_DIALOG_TYPE);
    customDialogUtilBuilder.setUpDateMandatory(isUpdateMandatory);
    customDialogUtilBuilder.buildCustomDialog();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    System.gc();
  }

  @Override
  public void onVersionChange(boolean versionChange) {
    if (versionChange) {
      Intent intent = new Intent(Intent.ACTION_VIEW,
          Uri.parse(String.format("%s%s", PLAY_STORE_URL, BuildConfig.APPLICATION_ID)));
      startActivity(intent);
    }
  }
}
