package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.EMAIL_LOGIN;
import static com.customer.data.utils.DataConstants.MOBILE_LOGIN;

import com.customer.data.DataSource;
import com.customer.domain.interactor.VerifyMobileOrMailUseCase;
import com.customer.domain.interactor.VerifyMobileOrMailUseCase.ResponseValues;
import com.customer.domain.model.VerifyMobileOrMailData;
import com.customer.domain.repository.VerifyMobileOrMailRepository;
import com.customer.remote.http.model.request.VerifyMobileOrMailRequest;
import com.customer.remote.http.model.response.VerifyMobileOrMailDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class VerifyMobileOrMailRepositoryImpl extends BaseRepository implements
    VerifyMobileOrMailRepository {

  private DataSource dataSource;

  @Inject
  public VerifyMobileOrMailRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ResponseValues> signIn(String countryCode, String mobile, int verifyType,
      String email) {

    Single<VerifyMobileOrMailDetails> detailsSingle = null;

    if (verifyType == EMAIL_LOGIN) {
      detailsSingle = dataSource.api()
          .nodeApiHandler().verifyMobileOrMail(getHeader(),
              new VerifyMobileOrMailRequest(email, verifyType));
    } else if (verifyType == MOBILE_LOGIN) {
      detailsSingle = dataSource.api()
          .nodeApiHandler().verifyMobileOrMail(getHeader(),
              new VerifyMobileOrMailRequest(countryCode, mobile, verifyType));
    }

    assert detailsSingle != null;
    return detailsSingle.flatMap(
        new Function<VerifyMobileOrMailDetails, SingleSource<? extends ResponseValues>>() {
          @Override
          public SingleSource<? extends ResponseValues> apply(
              VerifyMobileOrMailDetails verifyMobileOrMailDetails) throws Exception {
            return Single.just(
                new VerifyMobileOrMailUseCase.ResponseValues(new VerifyMobileOrMailData()));
          }
        });
  }
}
