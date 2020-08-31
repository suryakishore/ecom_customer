package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.WalletTransactionsMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.WalletTransactionsUseCase;
import com.customer.domain.repository.WalletTransactionsRepository;
import com.customer.remote.http.model.response.wallet.WalletTransactionsListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class WalletTransactionsRepositoryImpl extends BaseRepository implements
    WalletTransactionsRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private WalletTransactionsMapper mMapper = new WalletTransactionsMapper();

  @Inject
  public WalletTransactionsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<WalletTransactionsUseCase.ResponseValues> walletTransactions(String walletId,
      int fetchSize, String pageState) {
    return mDataSource.api().nodeApiHandler().getWalletTransactions(getHeader(), walletId,
        fetchSize, pageState).flatMap(
        new Function<WalletTransactionsListDetails, SingleSource<?
            extends WalletTransactionsUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends WalletTransactionsUseCase.ResponseValues> apply(
                WalletTransactionsListDetails details) {
              return Single.just(
                new WalletTransactionsUseCase.ResponseValues(
                    mMapper.mapper(details)));
            }
          });
  }
}
