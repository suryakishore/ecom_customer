package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.ProfileDetMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.GetProfileDetailsUseCase;
import com.customer.domain.repository.GetProfileDetailsRepository;
import com.customer.remote.http.model.response.profile.ProfileDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetProfileDetailsRepositoryImpl extends BaseRepository implements
    GetProfileDetailsRepository {

  private DataSource dataSource;
  private ProfileDetMapper mMapper = new ProfileDetMapper();
  private PreferenceManager preference;

  @Inject
  public GetProfileDetailsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<GetProfileDetailsUseCase.ResponseValues> getProfileData() {
    return dataSource.api().nodeApiHandler().getProfileDetails(getHeader()).flatMap(
        new Function<ProfileDetails, SingleSource<?
            extends GetProfileDetailsUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetProfileDetailsUseCase.ResponseValues> apply(
              ProfileDetails details) throws Exception {
            return Single.just(
                new GetProfileDetailsUseCase.ResponseValues(mMapper.mapper(details, preference)));
          }
        });
  }
}
