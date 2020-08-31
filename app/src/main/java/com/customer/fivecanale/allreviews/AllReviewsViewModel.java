package com.customer.fivecanale.allreviews;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetAllReviewsUseCase;
import com.customer.domain.interactor.LikeDisLikeReviewUseCase;
import com.customer.domain.interactor.ReportReviewUseCase;
import com.customer.domain.model.pdp.UserReviewData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view mData class for this all reviews activity
 */
public class AllReviewsViewModel extends ViewModel {
  public ObservableField<Boolean> itemLoadingVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  boolean isLoading, isLastItem;
  private MutableLiveData<Boolean> mBooleanMutableLiveData = new MutableLiveData<>();
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private GetAllReviewsUseCase mGetAllReviewsUseCase;
  private UseCaseHandler mHandler;
  private ReportReviewUseCase mReportReviewUseCase;
  private LikeDisLikeReviewUseCase mLikeDisLikeReviewUseCase;
  private MutableLiveData<Boolean> mLikeLiveData = new MutableLiveData<>();
  private MutableLiveData<ArrayList<UserReviewData>> mUserReviewDataMutableLiveData =
      new MutableLiveData<>();

  /**
   * constructor for this all reviews view mData
   *
   * @param handler                   nodeApiHandler class reference
   * @param getAllReviewsUseCase      all reviews use case reference to get the all reviews
   * @param likeDisLikeReviewUseCase like dislike use case
   * @param reportReviewUseCase      report review use case.
   */
  @Inject
  AllReviewsViewModel(UseCaseHandler handler, GetAllReviewsUseCase getAllReviewsUseCase,
      LikeDisLikeReviewUseCase likeDisLikeReviewUseCase,
      ReportReviewUseCase reportReviewUseCase) {
    this.mGetAllReviewsUseCase = getAllReviewsUseCase;
    this.mLikeDisLikeReviewUseCase = likeDisLikeReviewUseCase;
    this.mReportReviewUseCase = reportReviewUseCase;
    this.mHandler = handler;
  }

  /**
   * call to add to wishList api
   *
   * @param parentProductId product id
   */
  void callGetReviewsApi(String parentProductId, String skip, String limit) {
    EcomUtil.printLog("skip" + skip + "limit" + limit);
    if (Integer.parseInt(skip) <= LIMIT) {
      progressVisible.set(TRUE);
    }
    isLoading = TRUE;
    DisposableSingleObserver<GetAllReviewsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetAllReviewsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetAllReviewsUseCase.ResponseValues responseValues) {
            isLoading = FALSE;
            progressVisible.set(FALSE);
            itemLoadingVisible.set(FALSE);
            if (responseValues.getData().getReview() != null) {
              mUserReviewDataMutableLiveData.postValue(
                  responseValues.getData().getReview().getUserReviews());
            }
            EcomUtil.printLog("allReviews Succ");
          }

          @Override
          public void onError(Throwable e) {
            isLoading = FALSE;
            isLastItem = TRUE;
            progressVisible.set(FALSE);
            itemLoadingVisible.set(FALSE);
            EcomUtil.printLog("allReviews Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetAllReviewsUseCase,
        new GetAllReviewsUseCase.RequestValues(parentProductId, skip, limit),
        disposableSingleObserver);
  }

  /**
   * call the LikeOrDislike api
   *
   * @param reviewId review id
   */
  void callLikeOrDislikeApi(String reviewId, boolean likeOrDislike) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<LikeDisLikeReviewUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<LikeDisLikeReviewUseCase.ResponseValues>() {
          @Override
          public void onSuccess(LikeDisLikeReviewUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("LIke Succ");
            mLikeLiveData.postValue(likeOrDislike);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("Like Fail" + e.getMessage());
          }
        };
    if (likeOrDislike) {
      mHandler.execute(mLikeDisLikeReviewUseCase,
          new LikeDisLikeReviewUseCase.RequestValues(reviewId, likeOrDislike),
          disposableSingleObserver);
    } else {
      mHandler.execute(mLikeDisLikeReviewUseCase,
          new LikeDisLikeReviewUseCase.RequestValues(TRUE, reviewId),
          disposableSingleObserver);
    }
  }

  /**
   * call the product details api
   *
   * @param reviewId review id
   */
  void callReportReviewApi(String reviewId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ReportReviewUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ReportReviewUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ReportReviewUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("REview Succ");
            mValidateOnErrorLiveData.postValue(responseValues.getData().getMessage());
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("REview Fail" + e.getMessage());
          }
        };
    mHandler.execute(mReportReviewUseCase, new ReportReviewUseCase.RequestValues(reviewId),
        disposableSingleObserver);
  }

  public void onCrossIconClicked() {
    mBooleanMutableLiveData.postValue(TRUE);
  }

  /*
   * notify activity when cross icon clicked
   */
  MutableLiveData<Boolean> onCrossIcon() {
    return mBooleanMutableLiveData;
  }

  /*
   * notify activity when UserReview mData comes
   */
  MutableLiveData<ArrayList<UserReviewData>> getUserReviewDataMutableLiveData() {
    return mUserReviewDataMutableLiveData;
  }

  /**
   * returns the like liva mData
   */
  MutableLiveData<Boolean> getLikeLiveData() {
    return mLikeLiveData;
  }

  /**
   * returns the message
   */
  MutableLiveData<String> getError() {
    return mValidateOnErrorLiveData;
  }
}