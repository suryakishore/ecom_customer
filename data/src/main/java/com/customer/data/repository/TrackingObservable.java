package com.customer.data.repository;

import com.customer.domain.model.getcart.TrackingData;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class TrackingObservable extends Observable<TrackingData> {
  private static TrackingObservable mTrackingObservable;
  private Observer<? super TrackingData> observer;

  public static TrackingObservable getInstance() {
    if (mTrackingObservable == null) {
      mTrackingObservable = new TrackingObservable();
      return mTrackingObservable;
    } else {
      return mTrackingObservable;
    }
  }

  @Override
  protected void subscribeActual(Observer<? super TrackingData> observer) {
    this.observer = observer;
  }

  public void emit(TrackingData productOrderId) {
    if (observer != null) {
      observer.onNext(productOrderId);
    }
  }
}
