package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.WalletEstimateMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.TransactionEstimateUseCase;
import com.customer.domain.repository.TransactionEstimateRepository;
import com.customer.remote.http.model.response.walletEstimate.EstimateItemDetails;
import com.customer.remote.http.model.response.walletEstimate.WalletEstimateListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class TransactionEstimateRepositoryImpl extends BaseRepository implements
    TransactionEstimateRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private WalletEstimateMapper mMapper = new WalletEstimateMapper();

  @Inject
  public TransactionEstimateRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<TransactionEstimateUseCase.ResponseValues> walletTransactions(String fromCurrency,
      String toCurrency, float amount) {
    return mDataSource.api().nodeApiHandler().walletTransactions(getHeader(), fromCurrency,
        toCurrency, amount).flatMap(
        new Function<EstimateItemDetails, SingleSource<?
            extends TransactionEstimateUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends TransactionEstimateUseCase.ResponseValues> apply(
                EstimateItemDetails details) {
              return Single.just(
                new TransactionEstimateUseCase.ResponseValues(mMapper.mapper(details)));
            }
          });
  }
}
