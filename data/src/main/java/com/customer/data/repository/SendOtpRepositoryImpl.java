package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.EMAIL_LOGIN;
import static com.customer.data.utils.DataConstants.MOBILE_LOGIN;

import com.customer.data.DataSource;
import com.customer.data.mapper.ForgotPasswordMapper;
import com.customer.domain.interactor.SendOtpUseCase;
import com.customer.domain.interactor.SendOtpUseCase.ResponseValues;
import com.customer.domain.repository.SendOtpRepository;
import com.customer.remote.http.model.request.SendOtpRequest;
import com.customer.remote.http.model.response.ForgotPasswordDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class SendOtpRepositoryImpl extends BaseRepository implements SendOtpRepository {
  private DataSource dataSource;
  private ForgotPasswordMapper mapper = new ForgotPasswordMapper();

  @Inject
  public SendOtpRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ResponseValues> sendOTP(String mobile, String countryCode, String email,
      int verifyType, String triggeredBy, String userName, int type) {
    SendOtpRequest request = null;
    if (verifyType == EMAIL_LOGIN) {
      request = new SendOtpRequest(email, verifyType, triggeredBy, userName);
    } else if (verifyType == MOBILE_LOGIN) {
      request = new SendOtpRequest(mobile, countryCode, verifyType, triggeredBy, userName, type);
    }
    return dataSource.api()
        .nodeApiHandler().sendOtp(getHeader(), request).flatMap(
            new Function<ForgotPasswordDetails, SingleSource<? extends ResponseValues>>() {
              @Override
              public SingleSource<? extends ResponseValues> apply(
                  ForgotPasswordDetails forgotPasswordDetails) throws Exception {
                return Single.just(new SendOtpUseCase.ResponseValues(
                    mapper.map(forgotPasswordDetails)));
              }
            });

  }
}
