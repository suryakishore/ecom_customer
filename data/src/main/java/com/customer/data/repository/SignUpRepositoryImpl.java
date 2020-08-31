package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SignUpMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.signup.SignUpUseCase;
import com.customer.domain.repository.SignUpRepository;
import com.customer.remote.http.model.request.signUp.SignUpRequest;
import com.customer.remote.http.model.response.signUp.SignUpDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class SignUpRepositoryImpl extends BaseRepository implements SignUpRepository {
  private DataSource dataSource;
  private SignUpMapper signUpMapper = new SignUpMapper();
  private PreferenceManager preference;

  @Inject
  public SignUpRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<SignUpUseCase.ResponseValues> signUp(String deviceId, final String mobile,
      String countryCode, String email, String password, int deviceType, int customerType,
      int signUpType,
      int userType, int termsAndCond, String ipAddress, final String firstName,
      final String lastName,
      String profilePicture, String deviceModel, String deviceMake, String deviceTime,
      String browserName, String deviceOsVersion, String latitude, String longitude,
      String appVersion) {
    return dataSource.api()
        .nodeApiHandler()
        .signUp(getHeader(),
            new SignUpRequest(deviceId, mobile, countryCode, email, password, deviceType,
                signUpType, userType, customerType, termsAndCond, ipAddress, firstName, lastName,
                profilePicture, deviceModel, deviceMake, deviceTime, browserName, deviceOsVersion,
                String.valueOf(preference.getLatLong().getLat()),
                String.valueOf(preference.getLatLong().getLongitude()),
                appVersion, preference.getCity(), preference.getCountry(),
                preference.getState()))
        .flatMap(
            new Function<SignUpDetails, SingleSource<? extends SignUpUseCase.ResponseValues>>() {
              @Override
              public SingleSource<? extends SignUpUseCase.ResponseValues> apply(
                  SignUpDetails signUpDetails) throws Exception {
                return Single.just(
                    new SignUpUseCase.ResponseValues(signUpMapper.map(signUpDetails, preference)));
              }
            });
  }
}
