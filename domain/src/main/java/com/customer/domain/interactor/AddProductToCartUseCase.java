package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.repository.AddProductToCartRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class AddProductToCartUseCase extends
    UseCase<AddProductToCartUseCase.RequestValues, AddProductToCartUseCase.ResponseValues> {
  private AddProductToCartRepository mRepository;

  @Inject
  public AddProductToCartUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, AddProductToCartRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.addProductToCart(requestValues.mUserType, requestValues.mCentralProductId,
        requestValues.mProductId, requestValues.mUnitId, requestValues.mStoreId,
        requestValues.mQuantity, requestValues.mCartType,
        requestValues.mNotes, requestValues.mUserIp, requestValues.mUserPostCode,
        requestValues.mLatitude, requestValues.mLongitude,
        requestValues.mCity, requestValues.mStoreTypeId, requestValues.mProductName,
        requestValues.mEstimatedProductPrice,
        requestValues.mExtraNotes, requestValues.mOfferData);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int mUserType;
    private String mCentralProductId;
    private String mProductId;
    private String mUnitId;
    private String mStoreId;
    private int mQuantity;
    private int mCartType;
    private String mNotes;
    private String mUserIp;
    private String mUserPostCode;
    private double mLatitude;
    private double mLongitude;
    private String mCity;
    private int mStoreTypeId;
    private String mProductName;
    private String mEstimatedProductPrice;
    private String mExtraNotes;
    private PdpOfferData mOfferData;

    public RequestValues(int userType, String centralProductId, String productId,
        String unitId, String storeId, int quantity, int cartType, String notes,
        String userIp, String userPostCode, double latitude, double longitude, String city,
        int storeTypeId, String productName, String estimatedProductPrice, String extraNotes,
        PdpOfferData offerData) {
      this.mUserType = userType;
      this.mCentralProductId = centralProductId;
      this.mProductId = productId;
      this.mUnitId = unitId;
      this.mStoreId = storeId;
      this.mQuantity = quantity;
      this.mCartType = cartType;
      this.mNotes = notes;
      this.mUserIp = userIp;
      this.mUserPostCode = userPostCode;
      this.mLatitude = latitude;
      this.mLongitude = longitude;
      this.mCity = city;
      this.mStoreTypeId = storeTypeId;
      this.mProductName = productName;
      this.mEstimatedProductPrice = estimatedProductPrice;
      this.mExtraNotes = extraNotes;
      this.mOfferData = offerData;
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
  }
}
