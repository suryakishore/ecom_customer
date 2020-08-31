package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.DeleteAddressUseCase;
import com.customer.domain.repository.DeleteAddressRepository;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class DeleteAddressRepositoryImpl extends BaseRepository implements DeleteAddressRepository {

  private DataSource dataSource;
  private SuccessMapper mapper = new SuccessMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public DeleteAddressRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    this.mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<DeleteAddressUseCase.ResponseValues> deleteAddress(final String addressId) {
    return dataSource.api().nodeApiHandler().deleteAddress(getHeader(), addressId).flatMap(
        new Function<CommonModel, SingleSource<?
            extends DeleteAddressUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends DeleteAddressUseCase.ResponseValues> apply(
              CommonModel deleteAddressSuccess) throws Exception {
            mDatabaseManager.address().deleteByAddressId(addressId);
            return Single.just(
                new DeleteAddressUseCase.ResponseValues(mapper.mapper(deleteAddressSuccess)));
          }
        });
  }
}
