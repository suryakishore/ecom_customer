package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.tracking.TrackingListData;
import com.customer.domain.model.wallet.WalletData;
import com.customer.domain.model.wallet.WalletListData;
import com.customer.domain.repository.WalletAmtRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class WalletAmtUseCase extends
    UseCase<WalletAmtUseCase.RequestValues, WalletAmtUseCase.ResponseValues> {
  private WalletAmtRepository mRepository;

  @Inject
  public WalletAmtUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, WalletAmtRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.walletAmt(requestValues.userType);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String userType;
    public RequestValues(String userType) {
      this.userType = userType;
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
