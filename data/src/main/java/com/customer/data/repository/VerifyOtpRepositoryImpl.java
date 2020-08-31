package com.customer.data.repository;

import android.text.TextUtils;
import android.util.Log;
import com.customer.data.DataSource;
import com.customer.data.mapper.SignInMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.data.repository.observable.WalletAmtObservable;
import com.customer.domain.interactor.VerifyOTPUseCase;
import com.customer.domain.interactor.VerifyOTPUseCase.ResponseValues;
import com.customer.domain.model.UserData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.repository.VerifyOtpRepository;
import com.customer.remote.http.model.request.VerifyOTPRequest;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class VerifyOtpRepositoryImpl extends BaseRepository implements VerifyOtpRepository {
  private DataSource dataSource;
  private SignInMapper signInMapper = new SignInMapper();
  private PreferenceManager preference;

  @Inject
  public VerifyOtpRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<VerifyOTPUseCase.ResponseValues> verifyOtp(String otpId, String otpCode,
      int verifyType) {
    return dataSource.api()
        .nodeApiHandler().verifyOTP(getHeader(),
            new VerifyOTPRequest(otpId, otpCode, verifyType)).flatMap(
            new Function<SignInDetailsData, SingleSource<? extends ResponseValues>>() {
              @Override
              public SingleSource<? extends ResponseValues> apply(
                  SignInDetailsData signInDetailsData) throws Exception {
                if (!TextUtils.isEmpty(signInDetailsData.getAccessToken())) {
                  preference.setResetPasswordToken(signInDetailsData.getAccessToken());
                }
                if (!TextUtils.isEmpty(signInDetailsData.getUserId())) {
                  preference.setIsLoggedIn(true);
                  String profilePick = null;
                  if (signInDetailsData.getProfilePic() != null) {
                    profilePick = signInDetailsData.getProfilePic();
                  }
                  if (signInDetailsData.getCurrencyCode() != null) {
                    preference.setCurrencyCode(signInDetailsData.getCurrencyCode());
                  }
                  if (signInDetailsData.getCurrencySymbol() != null) {
                    preference.setCurrencySymbol(signInDetailsData.getCurrencySymbol());
                  }
                  if (signInDetailsData.getMqttTopic() != null) {
                    preference.setMqttTopic(signInDetailsData.getMqttTopic());
                  }
                  preference.setToken(signInDetailsData.getUserId(),
                      signInDetailsData.getToken().getAccessToken());
                  preference.setRefreshToken(
                      signInDetailsData.getToken().getRefreshToken());
                  UserData data = new UserData(signInDetailsData.getName(),
                      signInDetailsData.getEmail(),
                      signInDetailsData.getMobile(),
                      signInDetailsData.getCountryCode(), profilePick,
                      signInDetailsData.getCity(),
                      signInDetailsData.getRegion(),
                      signInDetailsData.getCountry());
                  preference.setUserDetails(data);
                  preference.setReferalCode(signInDetailsData.getReferralCode());
                  preference.setUserId(signInDetailsData.getUserId());
                  WalletAmtObservable.getInstance().emit(true);
                  UserDataObservable.getInstance().postData(data);
                  CartDataObservable.getInstance().postData(new CartData(10, "", 0));
                }
                return Single.just(
                    new VerifyOTPUseCase.ResponseValues(
                        signInMapper.map(signInDetailsData)));
              }
            });
  }
}
