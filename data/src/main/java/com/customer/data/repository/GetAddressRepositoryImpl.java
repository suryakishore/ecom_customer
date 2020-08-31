package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.GetAddressMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.GetAddressUseCase;
import com.customer.domain.repository.GetAddressRepository;
import com.customer.remote.http.model.response.getaddress.AddressDetails;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetAddressRepositoryImpl extends BaseRepository implements GetAddressRepository {
  private DataSource dataSource;
  private GetAddressMapper mapper = new GetAddressMapper();
  private PreferenceManager preference;
  private DatabaseManager mDatabaseManager;

  @Inject
  public GetAddressRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<GetAddressUseCase.ResponseValues> getFilteredProductList() {
    return dataSource.api().nodeApiHandler().getAddress(getHeader(),
        preference.getUserId()).flatMap(new Function<AddressDetails,
        SingleSource<? extends GetAddressUseCase.ResponseValues>>() {
          @Override
      public SingleSource<? extends GetAddressUseCase.ResponseValues> apply(
              AddressDetails details) throws Exception {
            mapper.insertToDatabase(details, mDatabaseManager);
            return Single.just(new GetAddressUseCase.ResponseValues(mapper.mapper(details)));
          }
        });
  }
}
