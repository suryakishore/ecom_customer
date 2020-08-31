package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.pdp.ProductDataModel;
import com.customer.domain.repository.ProductDetailsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class PdpUseCase extends UseCase<PdpUseCase.RequestValues, PdpUseCase.ResponseValues> {
  private ProductDetailsRepository mRepository;

  @Inject
  public PdpUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      ProductDetailsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getPdp(requestValues.mProductId, requestValues.mParentProductId,
        requestValues.lat, requestValues.longitude);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mProductId;
    private String mParentProductId;
    private double lat;
    private double longitude;

    public RequestValues(String productId, String parentProductId, double lat, double longitude) {
      this.mProductId = productId;
      this.mParentProductId = parentProductId;
      this.lat = lat;
      this.longitude = longitude;
    }

    public String getParentProductId() {
      return mParentProductId;
    }

    public void setParentProductId(String parentProductId) {
      this.mParentProductId = parentProductId;
    }

    public String getProductId() {
      return mProductId;
    }

    public void setProductId(String productId) {
      this.mProductId = productId;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ProductDataModel mData;

    public ResponseValues(ProductDataModel data) {
      this.mData = data;
    }

    public ProductDataModel getData() {
      return mData;
    }

    public void setData(ProductDataModel data) {
      this.mData = data;
    }
  }
}
