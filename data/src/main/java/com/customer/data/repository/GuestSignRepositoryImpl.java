package com.customer.data.repository;

import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.mapper.GuestSignMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.guestlogin.GuestApiUseCase;
import com.customer.domain.model.guestsignin.GuestSignInData;
import com.customer.domain.repository.GuestLogInRepository;
import com.customer.remote.http.model.request.GuestLogInReq;
import com.customer.remote.http.model.response.guestSignIn.GuestSignInDetailsData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GuestSignRepositoryImpl extends BaseRepository implements GuestLogInRepository {
  private DataSource dataSource;
  private GuestSignMapper guestSignMapper = new GuestSignMapper();
  private PreferenceManager preference;

  @Inject
  public GuestSignRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<GuestApiUseCase.ResponseValues> guestSignIn(String deviceId, String appVersion,
      int deviceType, String deviceMake, String deviceModel, String deviceOsVersion,
      String ipAddress, String browserName,
      String browserVersion, String deviceTime, String latitude, String longitude) {
    if (TextUtils.isEmpty(preference.getGuestToken())) {
      return dataSource.api()
          .nodeApiHandler()
          .guestSignIn(getHeader(),
              new GuestLogInReq(deviceId, appVersion, deviceType, deviceMake, deviceModel,
                  deviceOsVersion, ipAddress, browserName, browserVersion, deviceTime, latitude,
                  longitude))
          .flatMap(
              new Function<GuestSignInDetailsData, SingleSource<?
                  extends GuestApiUseCase.ResponseValues>>() {
                @Override
                public SingleSource<? extends GuestApiUseCase.ResponseValues> apply(
                    GuestSignInDetailsData guestSignInDetailsData) throws Exception {
                  return Single.just(new GuestApiUseCase.ResponseValues(
                      guestSignMapper.map(guestSignInDetailsData, preference)));
                }
              });
    }
    return Single.just(new GuestApiUseCase.ResponseValues(new GuestSignInData()));
  }
}
