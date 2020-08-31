package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.EMAIL_LOGIN;
import static com.customer.data.utils.DataConstants.MOBILE_LOGIN;

import android.content.Context;
import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.mapper.SignInMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.data.repository.observable.WalletAmtObservable;
import com.customer.domain.interactor.SignInUseCase;
import com.customer.domain.model.UserData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.repository.SignInRepository;
import com.customer.remote.http.model.request.SignInRequest;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class SignInRepositoryImpl extends BaseRepository implements SignInRepository {
  @Inject
  Context context;
  private DataSource dataSource;
  private SignInMapper signInMapper = new SignInMapper();
  private PreferenceManager preference;

  @Inject
  public SignInRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<SignInUseCase.ResponseValues> signIn(String mobile, String countryCode,
      String email, String password, int verifyType, int loginType, String deviceId,
      int deviceType, String ipAddress, String googleId, String facebookId,
      String deviceModel, String deviceMake, String browserName, String deviceOsVersion,
      String latitude, String longitude, String appVersion) {
    Single<SignInDetailsData> singleData = null;
    // verifyType 1 ==Email Login
    if (verifyType == EMAIL_LOGIN) {
      singleData =
          dataSource.api().nodeApiHandler().signIn(getHeader(),
              new SignInRequest(email, password, verifyType, loginType, deviceId,
                  deviceType, ipAddress, googleId, facebookId, deviceModel, deviceMake, browserName,
                  deviceOsVersion, String.valueOf(preference.getLatLong().getLat()),
                  String.valueOf(preference.getLatLong().getLongitude()), appVersion,
                  preference.getCity(),
                  preference.getCountry(), preference.getState()));
    } else if (verifyType == MOBILE_LOGIN) {
      singleData = dataSource.api().nodeApiHandler().signIn(getHeader(),
          new SignInRequest(mobile, countryCode, password, verifyType, loginType,
              deviceId, deviceType, ipAddress, deviceModel, deviceMake, browserName,
              deviceOsVersion, String.valueOf(preference.getLatLong().getLat()),
              String.valueOf(preference.getLatLong().getLongitude()), appVersion, preference.getCity(),
              preference.getCountry(), preference.getState()));
    }
    assert singleData != null;
    return singleData.flatMap(
        new Function<SignInDetailsData, SingleSource<?
            extends SignInUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends SignInUseCase.ResponseValues> apply(
              SignInDetailsData signInDetailsData) throws Exception {
            preference.setUserId(signInDetailsData.getUserId());
            if (signInDetailsData.getToken() != null
                && !TextUtils.isEmpty(
                signInDetailsData.getToken().getAccessToken())) {
              preference.setIsLoggedIn(true);
              preference.setRefreshToken(
                  signInDetailsData.getToken().getRefreshToken());
              preference.setToken(signInDetailsData.getUserId(),
                  signInDetailsData.getToken().getAccessToken());
            }
            if (signInDetailsData.getCurrencyCode() != null) {
              preference.setCurrencyCode(signInDetailsData.getCurrencyCode());
            }
            if (signInDetailsData.getMqttTopic() != null) {
              preference.setMqttTopic(signInDetailsData.getMqttTopic());
            }
            if (signInDetailsData.getCurrencySymbol() != null) {
              preference.setCurrencySymbol(signInDetailsData.getCurrencySymbol());
            }
            if (!TextUtils.isEmpty(signInDetailsData.getEmail())) {
              UserData data = new UserData(signInDetailsData.getName(),
                  signInDetailsData.getEmail(), signInDetailsData.getMobile(),
                  signInDetailsData.getCountryCode(), signInDetailsData.getProfilePic(),
                  signInDetailsData.getCity(), signInDetailsData.getRegion(),
                  signInDetailsData.getCountry());
              preference.setUserDetails(data);
              WalletAmtObservable.getInstance().emit(true);
              UserDataObservable.getInstance().postData(data);
              CartDataObservable.getInstance().postData(new CartData(10, "", 0));
            }
            if (!TextUtils.isEmpty(signInDetailsData.getOtpId())) {
              preference.setOtpToken(signInDetailsData.getOtpId());
            }
            return Single.just(
                new SignInUseCase.ResponseValues(
                    signInMapper.map(signInDetailsData)));
          }
        });
  }
}
