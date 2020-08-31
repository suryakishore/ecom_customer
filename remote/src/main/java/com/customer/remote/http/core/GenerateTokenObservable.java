package com.customer.remote.http.core;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.Request;

public class GenerateTokenObservable extends Observable<Boolean> {
  private static GenerateTokenObservable sGenerateTokenObservable;
  private Observer<? super Boolean> observer;

  public static GenerateTokenObservable getInstance() {
    if (sGenerateTokenObservable == null) {
      sGenerateTokenObservable = new GenerateTokenObservable();
      return sGenerateTokenObservable;
    } else {
      return sGenerateTokenObservable;
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
