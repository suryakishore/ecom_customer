package com.customer.data.repository;

import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.mapper.SignInMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.AppConfigUseCase;
import com.customer.domain.repository.AppConfigRepository;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class AppConfigRepositoryImpl extends BaseRepository implements
    AppConfigRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private SignInMapper mMapper = new SignInMapper();

  @Inject
  public AppConfigRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<AppConfigUseCase.ResponseValues> appConfig(int backGroundFlag) {
    return mDataSource.api().nodeApiHandler().getAppConfig(getHeader(), backGroundFlag).flatMap(
        new Function<SignInDetailsData, SingleSource<?
            extends AppConfigUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends AppConfigUseCase.ResponseValues> apply(
              SignInDetailsData details) {
            if (details.getToken() != null
                && !TextUtils.isEmpty(
                details.getToken().getAccessToken())) {
              preference.setRefreshToken(
                  details.getToken().getRefreshToken());
              preference.setToken(preference.getUserId(),details.getToken().getAccessToken());
            }
            return Single.just(
                new AppConfigUseCase.ResponseValues(mMapper.map(details)));
          }
        });
  }
}
