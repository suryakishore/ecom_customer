package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.wallet.WalletData;
import com.customer.domain.repository.WalletAddMoneyRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class WalletAddMoneyUseCase extends
    UseCase<WalletAddMoneyUseCase.RequestValues, WalletAddMoneyUseCase.ResponseValues> {
  private WalletAddMoneyRepository mRepository;

  @Inject
  public WalletAddMoneyUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, WalletAddMoneyRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.walletAddMoney(requestValues.cardId, requestValues.currency,
        requestValues.amount);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String cardId;
    private String currency;
    private String amount;

    public RequestValues(String cardId, String currency, String amount) {
      this.cardId = cardId;
      this.currency = currency;
      this.amount = amount;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private WalletData mData;

    public ResponseValues(WalletData data) {
      this.mData = data;
    }

    public WalletData getData() {
      return mData;
    }

    public void setData(WalletData data) {
      this.mData = data;
    }
  }
}
