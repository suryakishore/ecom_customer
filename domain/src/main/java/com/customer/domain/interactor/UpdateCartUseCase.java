package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.repository.UpdateCartRepository;

import io.reactivex.Single;
import javax.inject.Inject;

import static com.customer.domain.DomainConstants.MODIFY;

public class UpdateCartUseCase extends
    UseCase<UpdateCartUseCase.RequestValues, UpdateCartUseCase.ResponseValues> {
  private UpdateCartRepository mRepository;

  @Inject
  public UpdateCartUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, UpdateCartRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    if(requestValues.mAction != MODIFY) {
      return mRepository.updateCart(requestValues.mUserType, requestValues.mCentralProductId,
              requestValues.mProductId, requestValues.mUnitId, requestValues.mStoreId,
              requestValues.mStoreTypeId, requestValues.mProductName,
              requestValues.mEstimatedProductPrice, requestValues.mExtraNotes, requestValues.mNewQuantity,
              requestValues.mOldQuantity, requestValues.mAction, requestValues.mCartType,
              requestValues.mUserIp, requestValues.mLatitude, requestValues.mLongitude, requestValues.mOffer);
    } else {
      return mRepository.updateCart(requestValues.mUserType, requestValues.mCentralProductId,
              requestValues.mProductId, requestValues.mUnitId, requestValues.mStoreId,
              requestValues.mStoreTypeId, requestValues.mProductName,
              requestValues.mEstimatedProductPrice, requestValues.mExtraNotes, requestValues.mNewQuantity,
              requestValues.mOldQuantity, requestValues.mAction, requestValues.mCartType,
              requestValues.mUserIp, requestValues.mLatitude, requestValues.mLongitude);
    }
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int mUserType;
    private String mCentralProductId;
    private String mProductId;
    private String mUnitId;
    private String mStoreId;
    private int mStoreTypeId;
    private String mProductName;
    private String mEstimatedProductPrice;
    private String mExtraNotes;
    private int mNewQuantity;
    private int mOldQuantity;
    private int mAction;
    private int mCartType;
    private String mUserIp;
    private double mLatitude;
    private double mLongitude;
    private PdpOfferData mOffer;


    public RequestValues(int userType, String centralProductId, String productId,
        String unitId, String storeId, int storeTypeId, String productName,
        String estimatedProductPrice, String extraNotes, int newQuantity, int oldQuantity,
        int action, int cartType, String userIp, double latitude, double longitude) {
      mUserType = userType;
      mCentralProductId = centralProductId;
      mProductId = productId;
      mUnitId = unitId;
      mStoreId = storeId;
      mStoreTypeId = storeTypeId;
      mProductName = productName;
      mEstimatedProductPrice = estimatedProductPrice;
      mExtraNotes = extraNotes;
      mNewQuantity = newQuantity;
      mOldQuantity = oldQuantity;
      mAction = action;
      mCartType = cartType;
      mUserIp = userIp;
      mLatitude = latitude;
      mLongitude = longitude;
    }

    public RequestValues(int userType, String centralProductId, String productId,
                         String unitId, String storeId, int storeTypeId, String productName,
                         String estimatedProductPrice, String extraNotes, int newQuantity, int oldQuantity,
                         int action, int cartType, String userIp, double latitude, double longitude,
                         PdpOfferData offer) {
      mUserType = userType;
      mCentralProductId = centralProductId;
      mProductId = productId;
      mUnitId = unitId;
      mStoreId = storeId;
      mStoreTypeId = storeTypeId;
      mProductName = productName;
      mEstimatedProductPrice = estimatedProductPrice;
      mExtraNotes = extraNotes;
      mNewQuantity = newQuantity;
      mOldQuantity = oldQuantity;
      mAction = action;
      mCartType = cartType;
      mUserIp = userIp;
      mLatitude = latitude;
      mLongitude = longitude;
      mOffer = offer;
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
