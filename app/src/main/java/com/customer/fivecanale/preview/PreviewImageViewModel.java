package com.customer.fivecanale.preview;

import static com.customer.fivecanale.preview.PreviewImageOnClick.BACK;
import static com.customer.fivecanale.preview.PreviewImageOnClick.OPTION_MENU;
import static com.customer.fivecanale.preview.PreviewImageOnClick.START_BOARDING_ACT;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.LikeDisLikeReviewUseCase;
import com.customer.domain.interactor.ReportReviewUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/**
 * view mData class for preview image activity
 */
public class PreviewImageViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public String reviewId = "";
  @Inject
  UserInfoHandler mUserInfoHandler;
  private MutableLiveData<PreviewImageOnClick> mOnClick;
  private UseCaseHandler mHandler;
  private ReportReviewUseCase mReportReviewUseCase;
  private LikeDisLikeReviewUseCase mLikeDisLikeReviewUseCase;
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mLikeLiveData = new MutableLiveData<>();

  /**
   * constructor for this preview view mData
   *
   * @param handler                  nodeApiHandler class for this view mData
   * @param likeDisLikeReviewUseCase like and dislike review case
   * @param reportReviewUseCase      report review use case
   */
  @Inject
  PreviewImageViewModel(UseCaseHandler handler,
      LikeDisLikeReviewUseCase likeDisLikeReviewUseCase,
      ReportReviewUseCase reportReviewUseCase) {
    mOnClick = new MutableLiveData<>();
    this.mLikeDisLikeReviewUseCase = likeDisLikeReviewUseCase;
    this.mReportReviewUseCase = reportReviewUseCase;
    this.mHandler = handler;
  }

  /**
   * listens when we click on back
   */
  public void onBackIconClicked() {
    mOnClick.postValue(BACK);
  }

  /**
   * on like clicked
   */
  public void onLikeClick() {
    if (mUserInfoHandler.isUserLoggedIn()) {
      callLikeOrDislikeApi(reviewId, TRUE);
    } else {
      mOnClick.postValue(START_BOARDING_ACT);
    }
  }

  /**
   * on dislike clicked
   */
  public void onDisLikeClick() {
    if (mUserInfoHandler.isUserLoggedIn()) {
      callLikeOrDislikeApi(reviewId, FALSE);
    } else {
      mOnClick.postValue(START_BOARDING_ACT);
    }
  }

  /**
   * on menu clicked
   */
  public void onMenuClick() {
    mOnClick.postValue(OPTION_MENU);
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
          new LikeDisLikeReviewUseCase.RequestValues(reviewId, TRUE),
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

  /**
   * listen when click happened
   */
  MutableLiveData<PreviewImageOnClick> getClicks() {
    return mOnClick;
  }
}
