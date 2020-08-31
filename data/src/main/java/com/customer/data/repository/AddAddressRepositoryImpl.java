package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.repository.AddAddressRepository;
import com.customer.remote.http.model.request.AddAddressRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class AddAddressRepositoryImpl extends BaseRepository implements AddAddressRepository {

  private DataSource dataSource;
  private SuccessMapper mapper = new SuccessMapper();
  private PreferenceManager preference;
  private DatabaseManager mDatabaseManager;

  @Inject
  public AddAddressRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<AddAddressUseCase.ResponseValues> addAddress(String name,
      String mobileNumber, String mobileNumberCode, String locality, String addLine1,
      String addLine2, String landmark, String city,
      String country, String placeId, String pincode, String latitude, String longitude,
      String taggedAs, String state, int tagged) {

    final boolean[] isDefault = new boolean[1];
    Thread t = new Thread() {
      public void run() {
        isDefault[0] = mDatabaseManager.address().getRowCount() <= 0;
      }
    };
    t.start();

    return dataSource.api()
        .nodeApiHandler().addAddress(getHeader(),
            new AddAddressRequest(preference.getUserId(), name, mobileNumber, mobileNumberCode,
                locality, addLine1, addLine2, landmark, city, country, placeId, pincode, latitude,
                longitude, taggedAs, state, tagged, isDefault[0])).flatMap(
            new Function<CommonModel, SingleSource<? extends AddAddressUseCase.ResponseValues>>() {
                    @Override
                    public SingleSource<? extends AddAddressUseCase.ResponseValues> apply(
                        CommonModel addAddressSuccess) throws Exception {
                      return Single.just(
                          new AddAddressUseCase.ResponseValues(mapper.mapper(addAddressSuccess)));
                    }
                  });
  }
}
