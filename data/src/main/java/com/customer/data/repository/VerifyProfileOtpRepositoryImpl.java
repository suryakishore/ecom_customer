package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.EMAIL_LOGIN;
import static com.customer.data.utils.DataConstants.MOBILE_LOGIN;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.domain.interactor.VerifyProfileOtpUseCase;
import com.customer.domain.model.UserData;
import com.customer.domain.repository.VerifyProfileOtpRepository;
import com.customer.remote.http.model.request.ProfileOtpVerifyRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class VerifyProfileOtpRepositoryImpl extends BaseRepository implements
    VerifyProfileOtpRepository {
  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();
  private PreferenceManager mPreferenceManager;

  @Inject
  public VerifyProfileOtpRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    this.mPreferenceManager = dataSource.preference();
  }

  @Override
  public Single<VerifyProfileOtpUseCase.ResponseValues> verifyProfileOtp(final String countryCode,
      String otpId, String otpCode, final String mobile, final String mail, final int type) {
    ProfileOtpVerifyRequest request = null;
    if (type == EMAIL_LOGIN) {
      request = new ProfileOtpVerifyRequest(getUserId(), otpId, otpCode, mail, type);
    } else if (type == MOBILE_LOGIN) {
      request = new ProfileOtpVerifyRequest(getUserId(), otpId, otpCode, mobile, countryCode, type);
    }
    return mDataSource.api().nodeApiHandler().verifyProfileOtp(getHeader(), request).flatMap(
        new Function<CommonModel, SingleSource<? extends VerifyProfileOtpUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends VerifyProfileOtpUseCase.ResponseValues> apply(
              CommonModel model) throws Exception {
            if (type == MOBILE_LOGIN) {
              UserData data = mPreferenceManager.getUserDetails();
              data.setCountryCode(countryCode);
              data.setPhoneNumber(mobile);
              mPreferenceManager.setUserDetails(data);
              UserDataObservable.getInstance().postData(data);
            }
            return Single.just(new VerifyProfileOtpUseCase.ResponseValues(mMapper.mapper(model)));
          }
        });
  }
}
