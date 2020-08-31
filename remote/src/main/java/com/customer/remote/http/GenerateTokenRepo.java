package com.customer.remote.http;

import io.reactivex.Observable;
import okhttp3.Request;

public interface GenerateTokenRepo {
  Observable<Boolean> generateTokenObservable();
}
