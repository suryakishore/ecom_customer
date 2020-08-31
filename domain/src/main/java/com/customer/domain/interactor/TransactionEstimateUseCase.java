package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.walletestimate.WalletEstimateItemData;
import com.customer.domain.repository.TransactionEstimateRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class TransactionEstimateUseCase extends
    UseCase<TransactionEstimateUseCase.RequestValues, TransactionEstimateUseCase.ResponseValues> {
  private TransactionEstimateRepository mRepository;

  @Inject
  public TransactionEstimateUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, TransactionEstimateRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.walletTransactions(requestValues.fromCurrency, requestValues.toCurrency,
        requestValues.amount);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String fromCurrency;
    private String toCurrency;
    private float amount;

    public RequestValues(String fromCurrency, String toCurrency, float amount) {
      this.fromCurrency = fromCurrency;
      this.toCurrency = toCurrency;
      this.amount = amount;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private WalletEstimateItemData mData;

    public ResponseValues(WalletEstimateItemData data) {
      this.mData = data;
    }

    public WalletEstimateItemData getData() {
      return mData;
    }

    public void setData(WalletEstimateItemData data) {
      this.mData = data;
    }
  }
}
