package com.customer.remote.http;

import com.customer.remote.http.core.LogoutObservable;
import io.reactivex.Observable;

public class LogoutRepoImpl implements LogoutRepo {
  @Override
  public Observable<Boolean> logoutObservable() {
    return LogoutObservable.getInstance();
  }
}
