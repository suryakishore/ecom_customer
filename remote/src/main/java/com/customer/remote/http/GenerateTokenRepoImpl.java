package com.customer.remote.http;

import com.customer.remote.http.core.GenerateTokenObservable;
import io.reactivex.Observable;
import okhttp3.Request;

public class GenerateTokenRepoImpl implements GenerateTokenRepo {
  @Override
  public Observable<Boolean> generateTokenObservable() {
    return GenerateTokenObservable.getInstance();
  }
}
