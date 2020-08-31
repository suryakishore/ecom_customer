package com.customer.fivecanale.review;

import static com.BuildConfig.BUCKET_NAME;
import static com.customer.fivecanale.review.ReviewProductUiAction.FINISH_ACT;
import static com.customer.fivecanale.review.ReviewProductUiAction.SUBMIT;
import static com.customer.fivecanale.util.EcomConstants.DEFAULT_LAT_LANG;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.POINT_ZERO;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_TYPE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetRatableAttributesUseCase;
import com.customer.domain.interactor.ProductRateAndReviewUseCase;
import com.customer.domain.interactor.UploadImageUseCase;
import com.customer.domain.model.getratable.RatableAttributesData;
import com.customer.domain.model.getratable.UserReviewData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.io.File;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view mData class for the review product
 */
public class ReviewProductViewModel extends ViewModel {
  public ObservableField<String> productImage = new ObservableField<>();
  public ObservableField<String> productPrice = new ObservableField<>();
  public ObservableField<String> productColor = new ObservableField<>();
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<String> productName = new ObservableField<>();
  public ObservableField<String> sellerReview = new ObservableField<>();
  public ObservableField<Boolean> rateProductVisible = new ObservableField<>(TRUE);
  public ObservableField<Boolean> btnEnabled = new ObservableField<>(FALSE);
  boolean attributeId;
  private MutableLiveData<ArrayList<RatableAttributesData>> mAttributesList =
      new MutableLiveData<>();
  private MutableLiveData<ArrayList<RatableAttributesData>> mSellerAttributesList =
      new MutableLiveData<>();
  private MutableLiveData<UserReviewData> mUserReviewData =
      new MutableLiveData<>();
  private MutableLiveData<ReviewProductUiAction> mLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mShopNowLiveData = new MutableLiveData<>();
  private GetRatableAttributesUseCase mGetRatableAttributesUseCase;
  private ProductRateAndReviewUseCase mProductRateAndReviewUseCase;
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private UploadImageUseCase mUploadImageUseCase;
  private UseCaseHandler mHandler;
  private String mDeviceIpAddress = "";
  private ArrayList<String> mUploadedImg = new ArrayList<>();
  private ArrayList<File> mFiles = new ArrayList<>();
  private ArrayList<String> mReviewImagesList = new ArrayList<>();
  private String mReviewDesc, mReviewTitle, mSellerDesc = "";
  private String mParentProductId;

  /**
   * constructor class for review product
   */
  @Inject
  ReviewProductViewModel(GetRatableAttributesUseCase attributesUseCase,
      ProductRateAndReviewUseCase productRateAndReviewUseCase,
      UploadImageUseCase uploadImageUseCase, UseCaseHandler handler) {
    this.mGetRatableAttributesUseCase = attributesUseCase;
    this.mProductRateAndReviewUseCase = productRateAndReviewUseCase;
    this.mUploadImageUseCase = uploadImageUseCase;
    this.mHandler = handler;
  }

  /**
   * for getting device details
   *
   * @param deviceIpAddress [IP of device]
   */
  void getDeviceDetails(String deviceIpAddress) {
    this.mDeviceIpAddress = deviceIpAddress;
  }

  /**
   * listens when cross clicked
   */
  public void onCrossIconClicked() {
    mLiveData.postValue(FINISH_ACT);
  }

  /**
   * listens when shopNow clicked
   */
  public void onShopNowClicked() {
    mLiveData.postValue(FINISH_ACT);
  }

  /**
   * call the ratable attributes api
   *
   * @param parentProductId parent product id
   */
  void callRatingAttributesApi(String parentProductId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetRatableAttributesUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetRatableAttributesUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetRatableAttributesUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              if (responseValues.getData().getReviewData() != null
                  && responseValues.getData().getReviewData().size() > ZERO) {
                if (responseValues.getData().getReviewData().get(ZERO).getOrder() != null
                    && !responseValues.getData().getReviewData().get(ZERO).getOrder()) {
                  mShopNowLiveData.postValue(TRUE);
                  return;
                }
                mSellerAttributesList.setValue(
                    responseValues.getData().getSellerData().getAttribute());
                mAttributesList.setValue(responseValues.getData().getReviewData().get(
                    ZERO).getUserReview().getAttribute());
                mUserReviewData.setValue(
                    responseValues.getData().getReviewData().get(ZERO).getUserReview());
                sellerReview.set(responseValues.getData().getSellerData().getSellerReview());
              } else {
                rateProductVisible.set(FALSE);
              }
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("Pdp Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetRatableAttributesUseCase,
        new GetRatableAttributesUseCase.RequestValues(parentProductId),
        disposableSingleObserver);
  }

  /**
   * call the ratable attributes api
   *
   * @param parentProductId product id
   */
  void callRatingsOrReviewsApi(int ratingType, int reviewType, String parentProductId,
      String attributeId, String reviewTitle, String reviewDescription, double rating,
      ArrayList<String> images) {
    DisposableSingleObserver<ProductRateAndReviewUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ProductRateAndReviewUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ProductRateAndReviewUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("Rating succ");
            progressVisible.set(FALSE);
            if (ratingType == REVIEW_TYPE) {
              mLiveData.postValue(FINISH_ACT);
              btnEnabled.set(TRUE);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            btnEnabled.set(TRUE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("Rating Fail" + e.getMessage());
          }
        };
    mHandler.execute(mProductRateAndReviewUseCase,
        new ProductRateAndReviewUseCase.RequestValues(ratingType, reviewType, parentProductId,
            attributeId,
            reviewTitle,
            reviewDescription, rating, images, "", "", mDeviceIpAddress, DEFAULT_LAT_LANG,
            DEFAULT_LAT_LANG, mSellerDesc),
        disposableSingleObserver);
  }

  /**
   * we will get array of file objects to upload the images to amazon server
   *
   * @param fileArrayList   arrayList of files
   * @param parentProductId parent product id
   */
  void reviewImgFiles(ArrayList<String> reviewImagesList, ArrayList<File> fileArrayList,
      String parentProductId) {
    this.mFiles.addAll(fileArrayList);
    this.mReviewImagesList.addAll(reviewImagesList);
    this.mParentProductId = parentProductId;
    progressVisible.set(TRUE);
    btnEnabled.set(FALSE);
    if (mFiles != null && mFiles.size() > ZERO) {
      uploadToAmazon(mFiles.get(ZERO), ZERO);
    } else {
      callRatingsOrReviewsApi(REVIEW_TYPE, 0, mParentProductId, "", mReviewTitle, mReviewDesc,
          POINT_ZERO,
          mReviewImagesList);
    }
  }

  /**
   * For uploading the Image file on the amazon server.
   * The value returned is as void.
   * <pre>
   * Used for uploading the Image file on the amazon server.
   * </pre>
   */
  private void uploadToAmazon(File file, int pos) {
    DisposableSingleObserver<UploadImageUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UploadImageUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UploadImageUseCase.ResponseValues responseValues) {
            EcomUtil.printLog(
                "in amazon upload s " + responseValues.getData().getImageUrl() + "file" + file);
            mUploadedImg.add(responseValues.getData().getImageUrl());
            if (mReviewImagesList != null && mReviewImagesList.contains(file.toString())) {
              mReviewImagesList.remove(file.toString());
            }
            int uploadPos = pos + ONE;
            if (uploadPos < mFiles.size()) {
              uploadToAmazon(mFiles.get(uploadPos), uploadPos);
            } else {
              mReviewImagesList.addAll(mUploadedImg);
              callRatingsOrReviewsApi(REVIEW_TYPE, 0, mParentProductId, "", mReviewTitle,
                  mReviewDesc,
                  POINT_ZERO,
                  mReviewImagesList);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("ProfileDet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mUploadImageUseCase,
        new UploadImageUseCase.RequestValues(BUCKET_NAME, file,
            String.format("%d", System.currentTimeMillis())),
        disposableSingleObserver);
  }

  /**
   * handle text changes of review description
   */
  public void onReviewDes(CharSequence s, int start, int before, int count) {
    mReviewDesc = s.toString();
    btnEnabled.set(
        attributeId && mReviewDesc.length() > ZERO && mReviewTitle != null
            && mReviewTitle.length() > ZERO);
  }

  /**
   * handle text changes of about seller
   */
  public void aboutSeller(CharSequence s, int start, int before, int count) {
    mSellerDesc = s.toString();
  }

  /**
   * handle text changes of review title
   */
  public void onReviewTitle(CharSequence s, int start, int before, int count) {
    mReviewTitle = s.toString();
    btnEnabled.set(
        attributeId && mReviewDesc != null && mReviewDesc.length() > ZERO
            && mReviewTitle.length() > ZERO);
  }

  /**
   * notify activity when a view is  clicked
   */
  public MutableLiveData<ReviewProductUiAction> onClick() {
    return mLiveData;
  }

  /**
   * notify when onError comes
   */
  MutableLiveData<String> onError() {
    return mValidateOnErrorLiveData;
  }

  /**
   * <p>this method is used listen when login button clicked.</p>
   */
  public void onSubmitButtonClicked() {
    mLiveData.postValue(SUBMIT);
  }

  /**
   * notify activity when the product is not ratable
   */
  MutableLiveData<Boolean> onShopNow() {
    return mShopNowLiveData;
  }

  /**
   * notify  attributes mData
   */
  MutableLiveData<ArrayList<RatableAttributesData>> getAttributesList() {
    return mAttributesList;
  }

  /**
   * notify  attributes mData
   */
  MutableLiveData<ArrayList<RatableAttributesData>> getSellerAttributesList() {
    return mSellerAttributesList;
  }

  /**
   * notify  ratings mData
   */
  MutableLiveData<UserReviewData> getUserReviewData() {
    return mUserReviewData;
  }
}