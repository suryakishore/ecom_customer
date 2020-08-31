package com.customer.data.repository.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class NotificationCountObservable extends Observable<Integer> {
  private static NotificationCountObservable mNotificationCountObservable;
  private Observer<? super Integer> observer;

  public static NotificationCountObservable getInstance() {
    if (mNotificationCountObservable == null) {
      mNotificationCountObservable = new NotificationCountObservable();
      return mNotificationCountObservable;
    } else {
      return mNotificationCountObservable;
    }
  }

  @Override
  protected void subscribeActual(Observer<? super Integer> observer) {
    this.observer = observer;
  }

  public void emit(int notificationCount) {
    if (observer != null) {
      observer.onNext(notificationCount);
    }
  }
}
