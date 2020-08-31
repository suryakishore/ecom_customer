package com.customer.fivecanale.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.LocationManagerUtil;
import dagger.android.support.DaggerAppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.inject.Inject;

/**
 * <H>Splash Activity</H>
 *
 * <p>If user is logged in and location is available then allowing to go home else making location
 * button visible .
 *
 * @author 3Embed
 */
public class SplashActivity extends DaggerAppCompatActivity
    implements LocationManagerUtil.FusedLocationListener {
  @Inject
  UserInfoHandler userInfoHandler;
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  private SplashViewModel splashViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //  getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.splash));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.app_color));
    }
    if (LocationManagerUtil.isGpsEnabled(this) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      LocationManagerUtil.getFuseLocation(this, this);
    }
    printHashKey();
    setContentView(R.layout.activity_splash);
    Thread thread = new Thread() {
      @Override
      public void run() {
        userInfoHandler.setIpAddress(EcomUtil.getIpAddress());
        EcomUtil.printLog("exe" + "IpAddress" + userInfoHandler.getIpAddress());
      }
    };
    thread.start();
    initialization();
  }

  /**
   * Initializing variables and injecting activity to dagger,Calling presenter method to check user
   * is logged in or not.
   */
  private void initialization() {
    splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
    splashViewModel.getDeviceDetails(EcomUtil.getDeviceId(this), EcomUtil.getIpAddress(this));
    if (!userInfoHandler.isUserLoggedIn()) {
      Thread thread = new Thread() {
        @Override
        public void run() {
          splashViewModel.callGuestSignAPI(EcomUtil.getIpAddress());
        }
      };
      thread.start();
    } else {
      Handler handler = new Handler();
      handler.postDelayed(
          new Runnable() {
            public void run() {
              startHomePage();
            }
          },
          3000);
    }
    subscribeCreateAccountClicked();
  }

  private void subscribeCreateAccountClicked() {
    splashViewModel
        .onLogin()
        .observe(
            this,
            new Observer<Boolean>() {
              @Override
              public void onChanged(Boolean value) {
                if (value) {
                  startHomePage();
                }
              }
            });
  }

  private void startHomePage() {
    Intent intent = new Intent(this, HomeActivity.class);
    startActivity(intent);
    finish();
  }

  public void printHashKey() {
    try {
      PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(),
          PackageManager.GET_SIGNATURES);
      for (Signature signature : info.signatures) {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(signature.toByteArray());
        String hashKey = new String(Base64.encode(md.digest(), 0));
        Log.d("Facebook", "printHashKey() Hash Key: " + hashKey);
      }
    } catch (NoSuchAlgorithmException e) {
      Log.e("Facebook", "printHashKey()", e);
    } catch (Exception e) {
      Log.e("Facebook", "printHashKey()", e);
    }
  }

  @Override
  public void onSuccess(double latitude, double longitude) {
    userInfoHandler.setLatLong(latitude, longitude);
  }
}
