package com.customer.domain;

import com.customer.domain.interactor.UseCase;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

public class UseCaseHandler {
  private static UseCaseHandler INSTANCE;

  @Inject
  public UseCaseHandler() {
  }

  public static UseCaseHandler getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UseCaseHandler();
      return INSTANCE;
    } else {
      return INSTANCE;
    }
  }

  public <REQUEST extends UseCase.RequestValues, RES extends UseCase.ResponseValue> void execute(
      UseCase<REQUEST, RES> useCase, REQUEST values, DisposableSingleObserver<RES> singleObserver) {
    useCase.mRequestValues = values;
    useCase.execute(singleObserver);
  }
}
