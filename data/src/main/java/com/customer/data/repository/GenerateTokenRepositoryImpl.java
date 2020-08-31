package com.customer.data.repository;

import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.GenerateTokenUseCase;
import com.customer.domain.repository.GenerateTokenRepository;
import com.customer.remote.http.model.request.GenerateTokenRequest;
import com.customer.remote.http.model.response.generatetoken.GenerateTokenItemDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GenerateTokenRepositoryImpl extends BaseRepository implements GenerateTokenRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;

  @Inject
  public GenerateTokenRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = mDataSource.preference();
  }

  @Override
  public Single<GenerateTokenUseCase.ResponseValues> generateToken() {
    return mDataSource.api().nodeApiHandler().generateToken(getHeader(),
        new GenerateTokenRequest(preference.getRefreshToken(),
            preference.getToken(preference.getUserId()))).flatMap(
        new Function<GenerateTokenItemDetails, SingleSource<?
            extends GenerateTokenUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GenerateTokenUseCase.ResponseValues> apply(
              GenerateTokenItemDetails model)
              throws Exception {
            if (model.getAccessToken() != null
                && !TextUtils.isEmpty(
                model.getAccessToken())) {
              preference.setToken(preference.getUserId(),
                  model.getAccessToken());
            }
            return Single.just(new GenerateTokenUseCase.ResponseValues(null));
          }
        });
  }
}
