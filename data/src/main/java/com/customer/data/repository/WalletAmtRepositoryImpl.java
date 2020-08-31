package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.WalletAmtMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.WalletAmtUseCase;
import com.customer.domain.repository.WalletAmtRepository;
import com.customer.remote.http.model.response.wallet.WalletDataDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class WalletAmtRepositoryImpl extends BaseRepository implements WalletAmtRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private WalletAmtMapper mMapper = new WalletAmtMapper();

  @Inject
  public WalletAmtRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<WalletAmtUseCase.ResponseValues> walletAmt(String userType) {
    return mDataSource.api().nodeApiHandler().getWalletAmt(getHeader(), preference.getUserId(),
        userType).flatMap(
        new Function<WalletDataDetails, SingleSource<?
            extends WalletAmtUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends WalletAmtUseCase.ResponseValues> apply(
                WalletDataDetails details) {
              return Single.just(
                new WalletAmtUseCase.ResponseValues(mMapper.mapper(details, preference)));
            }
          });
  }
}
