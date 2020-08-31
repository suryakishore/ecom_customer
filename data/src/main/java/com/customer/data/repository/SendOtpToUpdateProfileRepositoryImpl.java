package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.EMAIL_LOGIN;
import static com.customer.data.utils.DataConstants.MOBILE_LOGIN;

import com.customer.data.DataSource;
import com.customer.data.mapper.ForgotPasswordMapper;
import com.customer.domain.interactor.SendOtpToUpdateProfileUseCase;
import com.customer.domain.repository.SendOtpToUpdateProfileRepository;
import com.customer.remote.http.model.request.GetProfileOtpRequest;
import com.customer.remote.http.model.response.ForgotPasswordDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class SendOtpToUpdateProfileRepositoryImpl extends BaseRepository implements
    SendOtpToUpdateProfileRepository {
  private DataSource mDataSource;
  private ForgotPasswordMapper mMapper = new ForgotPasswordMapper();

  @Inject
  public SendOtpToUpdateProfileRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<SendOtpToUpdateProfileUseCase.ResponseValues> getOtpToUpdateProfile(String mobNum,
      String countryCode, String mail, int type) {
    GetProfileOtpRequest request = null;
    if (type == EMAIL_LOGIN) {
      request = new GetProfileOtpRequest(getUserId(), mail, type);
    } else if (type == MOBILE_LOGIN) {
      request = new GetProfileOtpRequest(getUserId(), mobNum, countryCode, type);
    }
    return mDataSource.api().nodeApiHandler().getOtpForUpdateProfile(getHeader(), request).flatMap(
        new Function<ForgotPasswordDetails, SingleSource<?
            extends SendOtpToUpdateProfileUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends SendOtpToUpdateProfileUseCase.ResponseValues> apply(
              ForgotPasswordDetails forgotPasswordDetails) throws Exception {
            return Single.just(
                new SendOtpToUpdateProfileUseCase.ResponseValues(
                    mMapper.map(forgotPasswordDetails)));
          }
        });
  }
}
