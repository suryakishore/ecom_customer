package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.WalletAmtMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.WalletAddMoneyUseCase;
import com.customer.domain.repository.WalletAddMoneyRepository;
import com.customer.remote.http.model.request.AddMoneyRequest;
import com.customer.remote.http.model.response.wallet.WalletDataDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class WalletAddMoneyRepositoryImpl extends BaseRepository implements
    WalletAddMoneyRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private WalletAmtMapper mMapper = new WalletAmtMapper();

  @Inject
  public WalletAddMoneyRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<WalletAddMoneyUseCase.ResponseValues> walletAddMoney(String cardId, String currency,
      String amount) {
    return mDataSource.api().nodeApiHandler().getWalletAddMoney(getHeader(),
        new AddMoneyRequest(cardId, currency, amount)
    ).flatMap(
        new Function<WalletDataDetails, SingleSource<?
            extends WalletAddMoneyUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends WalletAddMoneyUseCase.ResponseValues> apply(
              WalletDataDetails details) {
            return Single.just(
                new WalletAddMoneyUseCase.ResponseValues(mMapper.mapper(details,preference)));
          }
        });
  }
}
