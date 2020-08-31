package com.customer.data.repository.observable;

import android.annotation.SuppressLint;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observables.ConnectableObservable;

public class HistoryDataObservable {
  private static HistoryDataObservable rxConnectable;
  private static ConnectableObservable<OrderHistProductData> connectableObservable;
  private ObservableEmitter<OrderHistProductData> emitor;

  @SuppressLint("CheckResult")
  public HistoryDataObservable() {
    Observable<OrderHistProductData> observable = Observable.create(new ObservableOnSubscribe<OrderHistProductData>() {
      @Override
      public void subscribe(ObservableEmitter<OrderHistProductData> e) throws Exception {
        emitor = e;
      }
    });
    connectableObservable = observable.publish();
    connectableObservable.share();
    connectableObservable.replay();
    connectableObservable.connect();
  }

  public static HistoryDataObservable getInstance() {
    if (rxConnectable == null) {
      rxConnectable = new HistoryDataObservable();
      return rxConnectable;
    } else {
      return rxConnectable;
    }
  }

  public static ConnectableObservable<OrderHistProductData> getObservable() {
    return connectableObservable;
  }

  public void postData(OrderHistProductData userData) {
    if (emitor != null && userData != null) {
      emitor.onNext(userData);
    }
  }
}
