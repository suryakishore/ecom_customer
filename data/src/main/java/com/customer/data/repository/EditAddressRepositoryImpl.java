package com.customer.data.repository;

import android.util.Log;
import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.EditAddressUseCase;
import com.customer.domain.repository.EditAddressRepository;
import com.customer.remote.http.model.request.AddAddressRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import com.data.cache.entities.UserAddress;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;

public class EditAddressRepositoryImpl extends BaseRepository implements EditAddressRepository {

  private DataSource dataSource;
  private SuccessMapper mapper = new SuccessMapper();
  private PreferenceManager preference;
  private DatabaseManager mDatabaseManager;

  @Inject
  public EditAddressRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<EditAddressUseCase.ResponseValues> editAddress(final String name,
      final String mobileNumber, final String mobileNumberCode, final String locality,
      final String addLine1, final String addLine2, final String flatNumber, final String landmark,
      final String city, final String state,
      final String country, final String placeId, final String pincode, final String latitude,
      final String longitude, final String taggedAs, final String addressId, final int tagged,
      final boolean isDefault,final String cityId,final String countryId) {

    return dataSource.api()
        .nodeApiHandler().editAddress(getHeader(),
            new AddAddressRequest(preference.getUserId(), name, mobileNumber, mobileNumberCode,
                locality, addLine1, addLine2, flatNumber, landmark, city, state, country, placeId,
                pincode, latitude, longitude, taggedAs, addressId, isDefault)).flatMap(
            new Function<CommonModel, SingleSource<?
                extends EditAddressUseCase.ResponseValues>>() {
                    @Override
                    public SingleSource<? extends EditAddressUseCase.ResponseValues> apply(
                        CommonModel addAddressSuccess) throws Exception {
                      UserAddress address = new UserAddress(addressId, mobileNumberCode, country, pincode,
                          city, mobileNumber, flatNumber, latitude, "", "", locality,
                          placeId, "", preference.getUserId(), "", name,
                          addLine1, addLine2, state, "", landmark, taggedAs, tagged + "",
                          isDefault ? "1" : "0", longitude,cityId,countryId);
                      mDatabaseManager.address().update(address);
                      List<UserAddress> addresses = mDatabaseManager.address().getAllAddress();
                      if (addresses != null) {
                        for (UserAddress userAddress : addresses) {
                          Log.d("AddressListFromDb", userAddress.getName());
                        }
                      }
                      return Single.just(
                          new EditAddressUseCase.ResponseValues(mapper.mapper(addAddressSuccess)));
                    }
                  });
  }

}
