package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.DeleteWishListProductRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class DeleteWishListProductUseCase extends
    UseCase<DeleteWishListProductUseCase.RequestValues,
        DeleteWishListProductUseCase.ResponseValues> {

  private DeleteWishListProductRepository mRepository;

  @Inject
  public DeleteWishListProductUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      DeleteWishListProductRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.deleteWishListProduct(requestValues.mProductId,
        requestValues.mIpAddress, requestValues.mLatitude, requestValues.mLongitude);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mProductId;
    private String mIpAddress;
    private double mLatitude;
    private double mLongitude;

    public RequestValues(String productId, String ipAddress, double latitude, double longitude) {
      this.mProductId = productId;
      this.mIpAddress = ipAddress;
      this.mLatitude = latitude;
      this.mLongitude = longitude;
    }

    public String getProductId() {
      return mProductId;
    }

    public void setProductId(String productId) {
      this.mProductId = productId;
    }

    public String getIpAddress() {
      return mIpAddress;
    }

    public void setIpAddress(String ipAddress) {
      this.mIpAddress = ipAddress;
    }

    public double getLatitude() {
      return mLatitude;
    }

    public void setLatitude(double latitude) {
      this.mLatitude = latitude;
    }

    public double getLongitude() {
      return mLongitude;
    }

    public void setLongitude(double longitude) {
      this.mLongitude = longitude;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CommonData mData;

    public ResponseValues(CommonData data) {
      this.mData = data;
    }

    public CommonData getData() {
      return mData;
    }

    public void setData(CommonData data) {
      this.mData = data;
    }
  }
}
