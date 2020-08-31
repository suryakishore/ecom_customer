package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<REQUEST extends UseCase.RequestValues,
    RESPONSE extends UseCase.ResponseValue> {
  public REQUEST mRequestValues = null;
  private ThreadExecutor mThreadExecutor;
  private PostExecutionThread mPostExecutionThread;
  private CompositeDisposable mDisposable = new CompositeDisposable();

  public UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    this.mThreadExecutor = threadExecutor;
    this.mPostExecutionThread = postExecutionThread;
  }

  protected abstract Single<RESPONSE> buildUseCaseObservable(
      REQUEST requestValues);

  public void execute(DisposableSingleObserver<RESPONSE> singleObserver) {
    Single<RESPONSE> single = this.buildUseCaseObservable(mRequestValues).subscribeOn(
        Schedulers.from(mThreadExecutor)).observeOn(mPostExecutionThread.getScheduler());
    addDisposable(single.subscribeWith(singleObserver));
  }

  public void dispose() {
    if (!mDisposable.isDisposed()) {
      mDisposable.dispose();
    }
  }

  private void addDisposable(Disposable disposable) {
    mDisposable.add(disposable);
  }

  //Data passed to a request.
  public interface RequestValues {
  }

  //Data received from a request.
  public interface ResponseValue {
  }
}