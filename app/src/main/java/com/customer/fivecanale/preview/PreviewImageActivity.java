package com.customer.fivecanale.preview;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.POSITION;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.USER_REVIEW_DATA;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.pdp.UserReviewData;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.uiutil.AnimUtil;
import com.databinding.ActivityPreviewImageBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/**
 * preview image for the review images
 */
public class PreviewImageActivity extends DaggerAppCompatActivity implements
    PopupMenu.OnMenuItemClickListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  UserInfoHandler mUserInfoHandler;
  private ActivityPreviewImageBinding mBinding;
  private PreviewImageViewModel mPreviewImageViewModel;
  private UserReviewData mUserReviewData;
  private String mReviewId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    getIntentData();
    subscribeOnClicks();
    subscribeLikeOrDisLike();
    subscribeOnError();
  }

  /**
   * subscribes to on click has happened.
   */
  private void subscribeOnClicks() {
    mPreviewImageViewModel.getClicks().observe(this, previewImageOnClick -> {
      switch (previewImageOnClick) {
        case BACK:
          onBackPressed();
          break;
        case OPTION_MENU:
          showMenu();
          break;
        case START_BOARDING_ACT:
          startBoardingAct();
          break;
      }
    });
  }

  /**
   * show the menu option.
   */
  private void showMenu() {
    PopupMenu popup = new PopupMenu(this, mBinding.ivPreviewImgOptions, Gravity.TOP);
    popup.setOnMenuItemClickListener(this);
    popup.inflate(R.menu.item_reviews_menu);
    popup.show();
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_preview_image);
  }

  /**
   * this method is to register for the mError message.
   */
  private void subscribeOnError() {
    mPreviewImageViewModel.getError().observe(this, this::showError);
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mBinding.clPreviewImg, error, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mPreviewImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        PreviewImageViewModel.class);
    mBinding.setViewModel(mPreviewImageViewModel);
  }

  /**
   * get the intent mData
   */
  private void getIntentData() {
    int pos = getIntent().getIntExtra(POSITION, ZERO);
    mUserReviewData = (UserReviewData) getIntent().getParcelableExtra(
        USER_REVIEW_DATA);
    if (mUserReviewData != null) {
      PreviewPagerAdapter imageAdapter = new PreviewPagerAdapter(mUserReviewData.getImages());
      mBinding.vpPreviewImages.setAdapter(imageAdapter);
      mBinding.vpPreviewImages.setCurrentItem(pos);
      mReviewId = mUserReviewData.getReviewId();
      mPreviewImageViewModel.reviewId = mReviewId;
      if (mUserReviewData.getRating() != null) {
        mBinding.tvProductRating.setText(mUserReviewData.getRating());
      }
      if (mUserReviewData.getReviewTitle() != null) {
        mBinding.tvPreViewImgTitle.setText(mUserReviewData.getReviewTitle());
      }
      if (mUserReviewData.getReviewDesc() != null) {
        mBinding.tvPreViewImgDesc.setText(mUserReviewData.getReviewDesc());
      }
      setLikesData(mUserReviewData);
      mBinding.tvPreviewImgPersonDet.setText(mUserReviewData.getName());
      mBinding.tvPreviewImgDate.setText(mUserReviewData.getTimestamp());
    }
  }

  /**
   * subscribes for like or unlike live mData
   */
  private void subscribeLikeOrDisLike() {
    mPreviewImageViewModel.getLikeLiveData().observe(this, likeOrDisLike -> {
      UserReviewData userReviewData = mUserReviewData;
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
      setLikesData(userReviewData);
    });
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra(USER_REVIEW_DATA, mUserReviewData);
    setResult(RESULT_OK, intent);
    super.onBackPressed();
  }

  /**
   * used to sets likes mData whether to like or dislike
   */
  private void setLikesData(UserReviewData userReviewData) {
    mBinding.tvPreviewImgLikeCount.setCompoundDrawablesWithIntrinsicBounds(
        userReviewData.getLikeSel() ? R.drawable.pdp_like_sel : R.drawable.pdp_like, ZERO, ZERO,
        ZERO);
    mBinding.tvPreviewImgDislikeCount.setCompoundDrawablesWithIntrinsicBounds(
        userReviewData.getDisLikeSel() ? R.drawable.pdp_dislike_sel : R.drawable.pdp_dislike,
        ZERO,
        ZERO,
        ZERO);
    mBinding.tvPreviewImgLikeCount.setText(String.format("%d", userReviewData.getLikes()));
    mBinding.tvPreviewImgDislikeCount.setText(String.format("%d", userReviewData.getDisLikes()));
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
        mPreviewImageViewModel.callReportReviewApi(mReviewId);
      } else {
        startBoardingAct();
      }
    }
    return item.getItemId() == R.id.flagAsAbusive;
  }

  /**
   * open the login activity screen
   */
  private void startBoardingAct() {
    Intent intent = new Intent(this, EcomLoginActivity.class);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(this);
  }
}