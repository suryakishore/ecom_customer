package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.repository.observable.DefaultAddressObservable;
import com.customer.domain.interactor.MakeAddressDefaultUseCase;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.repository.MakeAddressDefaultRepository;
import com.customer.remote.http.model.request.MakeAddressDefaultRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class MakeAddressDefaultRepositoryImpl extends BaseRepository implements
    MakeAddressDefaultRepository {
  private DataSource mDataSource;
  private SuccessMapper mSuccessMapper = new SuccessMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public MakeAddressDefaultRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<MakeAddressDefaultUseCase.ResponseValues> makeAddressDefault(
      final String addressId) {
    return mDataSource.api().nodeApiHandler().makeAddressDefault(getHeader(),
        new MakeAddressDefaultRequest(addressId)).flatMap(
        new Function<CommonModel,
            SingleSource<? extends MakeAddressDefaultUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends MakeAddressDefaultUseCase.ResponseValues> apply(
                CommonModel model) throws Exception {
              mDatabaseManager.address().makeAddressAsNonDefault();
              mDatabaseManager.address().makeAddressDefault(addressId);
              DefaultAddressObservable.getInstance().postData(new AddressListItemData(addressId));
              return Single.just(
                  new MakeAddressDefaultUseCase.ResponseValues(mSuccessMapper.mapper(model)));
            }
          });
  }
}
