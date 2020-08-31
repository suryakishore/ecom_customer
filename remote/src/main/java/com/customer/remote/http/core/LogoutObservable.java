package com.customer.remote.http.core;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class LogoutObservable extends Observable<Boolean> {
  private static LogoutObservable mLogoutObservable;
  private Observer<? super Boolean> observer;

  public static LogoutObservable getInstance() {
    if (mLogoutObservable == null) {
      mLogoutObservable = new LogoutObservable();
      return mLogoutObservable;
    } else {
      return mLogoutObservable;
    }
  }

  @Override
  protected void subscribeActual(Observer<? super Boolean> observer) {
    this.observer = observer;
  }

  public void emit(Boolean request) {
    if (observer != null) {
      observer.onNext(request);
    }
  }
}
