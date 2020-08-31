package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.EMAIL_LOGIN;
import static com.customer.data.utils.DataConstants.MOBILE_LOGIN;

import com.customer.data.DataSource;
import com.customer.data.mapper.ForgotPasswordMapper;
import com.customer.domain.interactor.ForgotPasswordUseCase;
import com.customer.domain.interactor.ForgotPasswordUseCase.ResponseValues;
import com.customer.domain.repository.ForgotPasswordRepository;
import com.customer.remote.http.model.request.ForgotPasswordRequest;
import com.customer.remote.http.model.response.ForgotPasswordDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ForgotPasswordRepositoryImpl extends BaseRepository implements
    ForgotPasswordRepository {
  private DataSource dataSource;
  private ForgotPasswordMapper mapper = new ForgotPasswordMapper();

  @Inject
  public ForgotPasswordRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ResponseValues> forgotPassword(int verifyType, String email, String countryCode,
      String mobile, String deviceId, String deviceMake, String deviceModel, String deviceType) {

    Single<ForgotPasswordDetails> dataSingle = null;
    if (verifyType == EMAIL_LOGIN) {
      dataSingle = dataSource.api()
          .nodeApiHandler().forgotPassword(getHeader(),
              new ForgotPasswordRequest(verifyType, email, deviceId, deviceMake, deviceModel,
                  deviceType));
    } else if (verifyType == MOBILE_LOGIN) {
      dataSingle = dataSource.api()
          .nodeApiHandler().forgotPassword(getHeader(),
              new ForgotPasswordRequest(verifyType, countryCode, mobile, deviceId, deviceMake,
                  deviceModel, deviceType));
    }
    assert dataSingle != null;
    return dataSingle.flatMap(
        new Function<ForgotPasswordDetails, SingleSource<? extends ResponseValues>>() {
          @Override
          public SingleSource<? extends ResponseValues> apply(
              ForgotPasswordDetails forgotPasswordDetails) throws Exception {
            return Single.just(new ForgotPasswordUseCase.ResponseValues(
                mapper.map(forgotPasswordDetails)));
          }
        });
  }
}
