package com.customer.fivecanale.allreviews;

import static com.customer.fivecanale.util.EcomConstants.ALL_PREVIEW_CODE;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PEN_COUNT;
import static com.customer.fivecanale.util.EcomConstants.POSITION;
import static com.customer.fivecanale.util.EcomConstants.RATING;
import static com.customer.fivecanale.util.EcomConstants.RATING_DATA;
import static com.customer.fivecanale.util.EcomConstants.TOTALRATING_AND_REVIEWS;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.USER_REVIEW_DATA;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.pdp.Rating;
import com.customer.domain.model.pdp.UserReviewData;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.pdp.ReviewsMenuClick;
import com.customer.fivecanale.pdp.adapters.RatingDistributionsAdapter;
import com.customer.fivecanale.pdp.adapters.ReviewsAdapter;
import com.customer.fivecanale.preview.PreviewImageActivity;
import com.customer.fivecanale.uiutil.AnimUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.ActivityAllReviewsBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;

/**
 * This activity will shows the all user reviews.
 */
public class AllReviewsActivity extends DaggerAppCompatActivity implements ReviewsMenuClick,
    ReviewImgClick, PopupMenu.OnMenuItemClickListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  UserInfoHandler mUserInfoHandler;
  private ActivityAllReviewsBinding mBinding;
  private AllReviewsViewModel mAllReviewsViewModel;
  private RatingDistributionsAdapter mRatingDistributionsAdapter;
  private ArrayList<Rating> mUserRatingData = new ArrayList<>();
  private ReviewsAdapter mReviewsAdapter;
  private ArrayList<UserReviewData> mReviewDataArrayList = new ArrayList<>();
  private LinearLayoutManager mLinearLayoutManager;
  private String mParentProductId;
  private String mReviewId;
  private int mPosition;
  private int mReviewPos;
  private int mPenCount;
  private UserReviewData mUserReviewData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    initializeRatingDistributionAdapter();
    initializeReviewsAdapter();
    subscribeCrossIconClicked();
    subscribeLikeOrDisLike();
    subscribeGetReviewsData();
    subscribeOnError();
    getIntentData();
  }

  /**
   * subscribe for get reviews mData
   */
  private void subscribeGetReviewsData() {
    mAllReviewsViewModel.getUserReviewDataMutableLiveData().observe(this,
        userReviewData -> {
          mReviewDataArrayList.addAll(userReviewData);
          mReviewsAdapter.notifyDataSetChanged();
        });
  }

  /**
   * get the intent mData from pdp
   */
  private void getIntentData() {
    Intent intent = getIntent();
    if (intent != null) {
      ArrayList<Rating> ratingData = intent.getParcelableArrayListExtra(
          RATING_DATA);
      mParentProductId = intent.getStringExtra(PARENT_PRODUCT_ID);
      mPenCount = intent.getIntExtra(PEN_COUNT, ZERO);
      mUserRatingData.addAll(ratingData);
      mRatingDistributionsAdapter.notifyDataSetChanged();
      String rating = intent.getStringExtra(RATING);
      String totalRatingAndReviews = intent.getStringExtra(TOTALRATING_AND_REVIEWS);
      mBinding.tvAllRviewsRating.setText(rating);
      mBinding.tvAllReviewDetails.setText(totalRatingAndReviews);
      mAllReviewsViewModel.callGetReviewsApi(mParentProductId,
          String.format("%d", ZERO), String.format("%d", LIMIT));
    }
    mBinding.rvAllReviews.addOnScrollListener(new MyScrollListener(mLinearLayoutManager) {
      @Override
      protected void loadMoreItems() {
        EcomUtil.printLog(
            "itemLoadingVisible" + mReviewDataArrayList.size() + "mPenCount" + mPenCount + "Limit"
                + (mReviewDataArrayList.size() + LIMIT));
        mAllReviewsViewModel.itemLoadingVisible.set(TRUE);
        mAllReviewsViewModel.itemLoadingVisible.set(mReviewDataArrayList.size() < mPenCount);
        if (mReviewDataArrayList.size() < mPenCount) {
          mAllReviewsViewModel.callGetReviewsApi(mParentProductId,
              String.format("%d", mReviewDataArrayList.size()),
              String.format("%d", mReviewDataArrayList.size() + LIMIT));
        }
      }

      @Override
      public boolean isLastPage() {
        return mAllReviewsViewModel.isLastItem;
      }

      @Override
      public boolean isLoading() {
        return mAllReviewsViewModel.isLoading;
      }
    });
  }

  /*
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_all_reviews);
  }

  /**
   * This method is used initialize the viewModel class for this activity.
   */
  private void initializeViewModel() {
    mAllReviewsViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(AllReviewsViewModel.class);
    mBinding.setViewModel(mAllReviewsViewModel);//used to connect mBinding to viewModel
  }

  /**
   * sets the adapter class rating mData
   */
  private void initializeRatingDistributionAdapter() {
    mRatingDistributionsAdapter = new RatingDistributionsAdapter(mUserRatingData);
    mBinding.rvAllReviewsRatingDistribution.setAdapter(mRatingDistributionsAdapter);
  }

  /**
   * sets the adapter class for review mData
   */
  private void initializeReviewsAdapter() {
    mLinearLayoutManager = new LinearLayoutManager(this);
    mReviewsAdapter = new ReviewsAdapter(mReviewDataArrayList, this, this, FALSE);
    mBinding.rvAllReviews.setLayoutManager(mLinearLayoutManager);
    mBinding.rvAllReviews.setAdapter(mReviewsAdapter);
  }

  /**
   * subscribing for cross icon click
   */
  private void subscribeCrossIconClicked() {
    mAllReviewsViewModel.onCrossIcon().observe(this, aBoolean -> {
      if (aBoolean) {
        onBackPressed();
      }
    });
  }

  /**
   * subscribes for like or unlike live mData
   */
  private void subscribeLikeOrDisLike() {
    mAllReviewsViewModel.getLikeLiveData().observe(this, likeOrDisLike -> {
      UserReviewData userReviewData = mReviewDataArrayList.get(mPosition);
      if (likeOrDisLike) {
        userReviewData.setLikes(userReviewData.getLikes() + ONE);
        if (userReviewData.getDisLikeSel()) {
          userReviewData.setDisLikes(userReviewData.getDisLikes() - ONE);
          userReviewData.setDisLikeSel(FALSE);
        }
        userReviewData.setLikeSel(TRUE);
      } else {
        userReviewData.setDisLikes(userReviewData.getDisLikes() + ONE);
        if (userReviewData.getLikeSel()) {
          userReviewData.setLikes(userReviewData.getLikes() - ONE);
          userReviewData.setLikeSel(FALSE);
        }
        userReviewData.setDisLikeSel(TRUE);
      }
      mReviewsAdapter.notifyItemChanged(mPosition, userReviewData);
    });
  }

  /**
   * listens click on menu icon on reviews adapter.
   *
   * @param likeOrMenu    defines like or menu icon
   * @param pos           position of the review item
   * @param optionMenu    option menu image view reference
   * @param likeOrDislike whether you are liking or disliking
   */
  @Override
  public void onMenuClick(int likeOrMenu, int pos, AppCompatImageView optionMenu,
      boolean likeOrDislike) {
    if (!mUserInfoHandler.isUserLoggedIn()) {
      startBoardingAct();
      return;
    }
    UserReviewData userReviewData = mReviewDataArrayList.get(pos);
    this.mReviewId = userReviewData.getReviewId();
    this.mPosition = pos;
    switch (likeOrMenu) {
      case ONE:
        PopupMenu popup = new PopupMenu(this, optionMenu, Gravity.TOP);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.item_reviews_menu);
        popup.show();
        break;
      case TWO:
        mAllReviewsViewModel.callLikeOrDislikeApi(mReviewId, likeOrDislike);
        break;
    }
  }

  /**
   * open the login activity screen
   */
  private void startBoardingAct() {
    Intent intent = new Intent(this, EcomLoginActivity.class);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(this));
  }

  /**
   * listens when we click on review image
   *
   * @param pos       position of the review image
   * @param reviewPos review position of the image
   */
  @Override
  public void onImgClick(int pos, int reviewPos) {
    startPreviewImgActivity(pos, reviewPos);
  }

  /**
   * start the review image activity
   *
   * @param pos       position of the review
   * @param reviewPos in that review position position of the review image.
   */
  private void startPreviewImgActivity(int pos, int reviewPos) {
    mUserReviewData = mReviewDataArrayList.get(reviewPos);
    this.mReviewPos = reviewPos;
    Intent intent = new Intent(this, PreviewImageActivity.class);
    intent.putExtra(POSITION, pos);
    intent.putExtra(USER_REVIEW_DATA, mUserReviewData);
    startActivityForResult(intent, ALL_PREVIEW_CODE);
  }

  /**
   * listener for menu item click for review abort
   *
   * @param item menu item references
   * @return returns boolean
   */
  @Override
  public boolean onMenuItemClick(MenuItem item) {
    if (item.getItemId() == R.id.flagAsAbusive) {
      if (mUserInfoHandler.isUserLoggedIn()) {
        mAllReviewsViewModel.callReportReviewApi(mReviewId);
      } else {
        startBoardingAct();
      }
      return TRUE;
    }
    return FALSE;
  }

  /**
   * This method is to register for the mError message.
   */
  private void subscribeOnError() {
    mAllReviewsViewModel.getError().observe(this, this::showError);
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mBinding.clAllReviews, error, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra(USER_REVIEW_DATA, mReviewDataArrayList);
    setResult(RESULT_OK, intent);
    super.onBackPressed();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ALL_PREVIEW_CODE) {
      if (data != null && resultCode == RESULT_OK) {
        mUserReviewData = data.getParcelableExtra(USER_REVIEW_DATA);
        mReviewDataArrayList.remove(mReviewPos);
        mReviewDataArrayList.add(mReviewPos, mUserReviewData);
        mReviewsAdapter.notifyItemChanged(mReviewPos);
      }
    }
  }
}