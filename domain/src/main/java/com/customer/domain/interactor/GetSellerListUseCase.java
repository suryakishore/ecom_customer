package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.sellerlist.SellerListData;
import com.customer.domain.repository.GetSellerListRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetSellerListUseCase extends
    UseCase<GetSellerListUseCase.RequestValues, GetSellerListUseCase.ResponseValues> {

  private GetSellerListRepository mRepository;

  @Inject
  public GetSellerListUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetSellerListRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getSellerList(requestValues.mProductId, requestValues.mParentProductId,
        requestValues.mLoginType);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mProductId;
    private String mParentProductId;
    private String mLoginType;

    public RequestValues(String productId, String parentProductId, String loginType) {
      this.mProductId = productId;
      this.mParentProductId = parentProductId;
      this.mLoginType = loginType;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private SellerListData mData;

    public ResponseValues(SellerListData data) {
      this.mData = data;
    }

    public SellerListData getData() {
      return mData;
    }

    public void setData(SellerListData data) {
      this.mData = data;
    }
  }

}
