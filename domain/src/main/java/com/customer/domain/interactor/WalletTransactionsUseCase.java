package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.wallet.WalletTransactionsData;
import com.customer.domain.repository.WalletTransactionsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class WalletTransactionsUseCase extends
    UseCase<WalletTransactionsUseCase.RequestValues, WalletTransactionsUseCase.ResponseValues> {
  private WalletTransactionsRepository mRepository;

  @Inject
  public WalletTransactionsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, WalletTransactionsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.walletTransactions(requestValues.walletId, requestValues.fetchSize,
        requestValues.pageState);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String walletId;
    private int fetchSize;
    private String pageState;

    public RequestValues(String walletId, int fetchSize, String pageState) {
      this.walletId = walletId;
      this.fetchSize = fetchSize;
      this.pageState = pageState;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private WalletTransactionsData mData;

    public ResponseValues(WalletTransactionsData data) {
      this.mData = data;
    }

    public WalletTransactionsData getData() {
      return mData;
    }

    public void setData(WalletTransactionsData data) {
      this.mData = data;
    }
  }
}
