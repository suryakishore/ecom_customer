package com.customer.data.repository.observable;

import com.customer.domain.model.common.ProductsData;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class WalletAmtObservable extends Observable<Boolean> {
  private static WalletAmtObservable wishListItemEmitter;
  private Observer<? super Boolean> observer;

  public static WalletAmtObservable getInstance() {
    if (wishListItemEmitter == null) {
      wishListItemEmitter = new WalletAmtObservable();
      return wishListItemEmitter;
    } else {
      return wishListItemEmitter;
    }
  }

  @Override
  protected void subscribeActual(Observer<? super Boolean> observer) {
    this.observer = observer;
  }

  public void emit(Boolean wishListProduct) {
    if (observer != null) {
      observer.onNext(wishListProduct);
    }
  }
}
