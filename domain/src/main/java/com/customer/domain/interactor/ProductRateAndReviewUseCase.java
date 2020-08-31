package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.ProductRateAndReviewRepository;
import io.reactivex.Single;
import java.util.ArrayList;
import javax.inject.Inject;

public class ProductRateAndReviewUseCase extends
    UseCase<ProductRateAndReviewUseCase.RequestValues, ProductRateAndReviewUseCase.ResponseValues> {
  private ProductRateAndReviewRepository mRepository;

  @Inject
  public ProductRateAndReviewUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      ProductRateAndReviewRepository homePageRepository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = homePageRepository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.rateAndReview(requestValues.mType, requestValues.mReviewType,
        requestValues.mProductId, requestValues.mAttributeId, requestValues.mReviewTitle,
        requestValues.mReviewDescription, requestValues.mRating, requestValues.mImages,
        requestValues.mCity, requestValues.mCountry, requestValues.mIpAddress,
        requestValues.mLatitude,
        requestValues.mLongitude, requestValues.mSellerReview);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int mType;
    private int mReviewType;
    private String mProductId;
    private String mAttributeId;
    private String mReviewTitle;
    private String mReviewDescription;
    private String mSellerReview;
    private double mRating;
    private ArrayList<String> mImages;
    private String mCity;
    private String mCountry;
    private String mIpAddress;
    private double mLatitude;
    private double mLongitude;

    public RequestValues(int type, int reviewType, String productId, String attributeId,
        String reviewTitle,
        String reviewDescription, double rating, ArrayList<String> images, String city,
        String country, String ipAddress, double latitude, double longitude, String sellerReview) {
      this.mType = type;
      this.mProductId = productId;
      this.mAttributeId = attributeId;
      this.mReviewTitle = reviewTitle;
      this.mReviewDescription = reviewDescription;
      this.mRating = rating;
      this.mImages = images;
      this.mCity = city;
      this.mCountry = country;
      this.mIpAddress = ipAddress;
      this.mLatitude = latitude;
      this.mLongitude = longitude;
      this.mReviewType = reviewType;
      this.mSellerReview = sellerReview;
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
      mData = data;
    }
  }
}
