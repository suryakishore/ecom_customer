package com.customer.remote.http;

import io.reactivex.Observable;

public interface LogoutRepo {
  Observable<Boolean> logoutObservable();
}
