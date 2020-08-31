package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.domain.interactor.LogoutUseCase;
import com.customer.domain.model.UserData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.repository.LogoutRepository;
import com.customer.remote.http.model.request.LogOutRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;

public class LogoutRepositoryImpl extends BaseRepository implements LogoutRepository {
  private static int NUMBER_OF_THREADS = 4;
  private DataSource dataSource;
  private SuccessMapper mapper = new SuccessMapper();
  private PreferenceManager preference;
  private DatabaseManager mDatabaseManager;

  @Inject
  public LogoutRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    this.preference = dataSource.preference();
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<LogoutUseCase.ResponseValues> logout(String ipAddress, double latitude,
      double longitude) {
    return dataSource.api().nodeApiHandler().logout(getHeader(),
        new LogOutRequest(ipAddress,
            preference.getLatLong() != null ? preference.getLatLong().getLat() : latitude,
            preference.getLatLong() != null ? preference.getLatLong().getLongitude()
                : longitude)).flatMap(
        new Function<CommonModel, SingleSource<? extends LogoutUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends LogoutUseCase.ResponseValues> apply(CommonModel details)
              throws Exception {
            ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);
            databaseWriteExecutor.execute(new Runnable() {
              @Override
              public void run() {
                mDatabaseManager.cart().clearCart();
                mDatabaseManager.address().deleteAllAddresses();
              }
            });
            preference.setUserDetails(null);
            preference.setToken(preference.getUserId(), "");
            preference.setUserId("");
            preference.setWalletAmt("");
            preference.setNotificationCount(0);
            preference.setIsLoggedIn(false);
            UserDataObservable.getInstance().postData(new UserData());
            CartDataObservable.getInstance().postData(new CartData());
            return Single.just(new LogoutUseCase.ResponseValues(mapper.mapper(details)));
          }
        });
  }
}
