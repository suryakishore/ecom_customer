package com.customer.data.repository;

import android.content.Context;
import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.domain.interactor.UpdateProfileUseCase;
import com.customer.domain.model.UserData;
import com.customer.domain.repository.UpdateProfileRepository;
import com.customer.remote.http.model.request.UpdateProfileRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class UpdateProfileRepositoryImpl extends BaseRepository implements UpdateProfileRepository {
  private DataSource dataSource;
  private SuccessMapper mapper = new SuccessMapper();
  private PreferenceManager preference;

  @Inject
  public UpdateProfileRepositoryImpl(DataSource dataSource, Context context) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<UpdateProfileUseCase.ResponseValues> updateProfile(final String firstName,
      String lastName, final String countryCode, final String mobNum, final String email) {
    UserData data = preference.getUserDetails();
    final String name, mail, phNum;
    name = TextUtils.isEmpty(firstName) ? data.getName() : firstName;
    mail = TextUtils.isEmpty(email) ? data.getMail() : email;
    phNum = TextUtils.isEmpty(mobNum) ? data.getPhoneNumber() : mobNum;
    return dataSource.api().nodeApiHandler().updateProfile(getHeader(),
        new UpdateProfileRequest(name, lastName, countryCode, phNum, mail)).flatMap(
        new Function<CommonModel, SingleSource<?
            extends UpdateProfileUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends UpdateProfileUseCase.ResponseValues> apply(
                CommonModel updateProfileDetails) throws Exception {
              UserData data = new UserData(name, mail, phNum, countryCode,
                  preference.getUserDetails().getProfilePic(), "",
                  "", "");
              preference.setUserDetails(data);
              UserDataObservable.getInstance().postData(data);
              return Single.just(
                  new UpdateProfileUseCase.ResponseValues(
                      mapper.mapper(updateProfileDetails)));
            }
          });
  }

  @Override
  public Single<UpdateProfileUseCase.ResponseValues> updateProfilePic(final String profilePick) {
    return dataSource.api().nodeApiHandler().updateProfile(getHeader(),
        new UpdateProfileRequest(profilePick)).flatMap(
        new Function<CommonModel,
            SingleSource<? extends UpdateProfileUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends UpdateProfileUseCase.ResponseValues> apply(
                CommonModel model) throws Exception {
              UserData userData = preference.getUserDetails();
              userData.setProfilePic(profilePick);
              preference.setUserDetails(userData);
              UserDataObservable.getInstance().postData(userData);
              return Single.just(
                  new UpdateProfileUseCase.ResponseValues(
                      mapper.mapper(model)));
            }
          });
  }
}
