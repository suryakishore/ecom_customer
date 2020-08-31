package com.customer.data.repository;

import static com.customer.remote.http.RemoteConstants.CHANGE_PASSWORD_TYPE;
import static com.customer.remote.http.RemoteConstants.FORGOT_PASSWORD_TYPE;
import static com.customer.remote.http.RemoteConstants.VALIDATE_PASSWORD_TYPE;

import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.ResetPasswordUseCase;
import com.customer.domain.interactor.ResetPasswordUseCase.ResponseValues;
import com.customer.domain.repository.ResetPasswordRepository;
import com.customer.remote.http.model.request.ResetPasswordRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ResetPasswordRepositoryImpl extends BaseRepository implements ResetPasswordRepository {
  private DataSource dataSource;
  private SuccessMapper resetPasswordMapper = new SuccessMapper();
  private PreferenceManager preference;

  @Inject
  public ResetPasswordRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<ResponseValues> resetPassword(String newPassword, String oldPassword,
      int resetType) {
    ResetPasswordRequest request = null;
    if (resetType == VALIDATE_PASSWORD_TYPE) {
      request = new ResetPasswordRequest(oldPassword, resetType);
    } else if (resetType == FORGOT_PASSWORD_TYPE) {
      request = new ResetPasswordRequest(newPassword, resetType);
    } else if (resetType == CHANGE_PASSWORD_TYPE) {
      request = new ResetPasswordRequest(newPassword, oldPassword, resetType);
    }
    String token = TextUtils.isEmpty(preference.getResetPasswordToken()) ? getAccessToken()
        : preference.getResetPasswordToken();
    return dataSource.api().nodeApiHandler().resetPassword(getHeader(), token, request
    ).flatMap(new Function<CommonModel, SingleSource<? extends ResponseValues>>() {
      @Override
      public SingleSource<? extends ResponseValues> apply(CommonModel details) throws Exception {
        preference.setResetPasswordToken("");
        return Single.just(
            new ResetPasswordUseCase.ResponseValues(resetPasswordMapper.mapper(details)));
      }
    });
  }
}
