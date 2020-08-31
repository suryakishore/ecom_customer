package com.customer.fivecanale.splash;

import static com.customer.fivecanale.util.EcomConstants.BROWSER_NAME;
import static com.customer.fivecanale.util.EcomConstants.BROWSER_VERSION;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAN;
import static com.customer.fivecanale.util.EcomConstants.CURRENT_LAT;
import static com.customer.fivecanale.util.EcomConstants.DEVICE_TYPE;

import android.os.Build;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase.ResponseValues;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

public class SplashViewModel extends ViewModel {
  private GuestApiUseCase guestApiUseCase;
  private UseCaseHandler handler;
  private MutableLiveData<Boolean> validateLoginLiveData = new MutableLiveData<Boolean>();
  private String mDeviceId = "";
  private String mDeviceIpAddress = "";

  @Inject
  public SplashViewModel(GuestApiUseCase guestApiUseCase,
      UseCaseHandler handler) {
    this.guestApiUseCase = guestApiUseCase;
    this.handler = handler;
  }

  /**
   * for getting device details
   *
   * @param deviceId [Id of device]
   */
  void getDeviceDetails(String deviceId, String deviceIpAddress) {
    this.mDeviceId = deviceId;
    this.mDeviceIpAddress = deviceIpAddress;
  }

  void callGuestSignAPI(String ipAddress) {
    DisposableSingleObserver<ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ResponseValues>() {
          @Override
          public void onSuccess(ResponseValues responseValues) {
            validateLoginLiveData.postValue(true);
            EcomUtil.printLog("HomeAPi Success");
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("HomeAPi Fail" + e.getMessage());
          }
        };
    handler.execute(guestApiUseCase, new GuestApiUseCase.RequestValues(mDeviceId,
        String.valueOf(Build.VERSION.SDK_INT), DEVICE_TYPE, Build.MANUFACTURER, Build.MODEL,
        String.valueOf(Build.VERSION.SDK_INT), ipAddress, BROWSER_NAME, BROWSER_VERSION,
        String.valueOf(System.currentTimeMillis()), String.valueOf(CURRENT_LAT),
        String.valueOf(CURRENT_LAN)), disposableSingleObserver);
  }

  /**
   * notify activity when login clicked
   */
  public MutableLiveData<Boolean> onLogin() {
    return validateLoginLiveData;
  }
}
