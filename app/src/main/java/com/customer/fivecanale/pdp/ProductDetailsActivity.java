package com.customer.fivecanale.pdp;

import static com.customer.fivecanale.util.EcomConstants.ALL_DETAILS_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.ALL_OFFERS;
import static com.customer.fivecanale.util.EcomConstants.ALL_PREVIEW_CODE;
import static com.customer.fivecanale.util.EcomConstants.ALL_REVIEWS_CODE;
import static com.customer.fivecanale.util.EcomConstants.ATTRIBUTES_BOTTOM_SHEET_TAG;
import static com.customer.fivecanale.util.EcomConstants.ATTRIBUTES_DATA;
import static com.customer.fivecanale.util.EcomConstants.DEFAULT_LAT_LANG;
import static com.customer.fivecanale.util.EcomConstants.ERROR_MSG;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIFTY;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FIVE_HUNDRED;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GET_ATTRIBUTES;
import static com.customer.fivecanale.util.EcomConstants.GET_ATTRIBUTES_RATING;
import static com.customer.fivecanale.util.EcomConstants.GET_DESCRIPTION;
import static com.customer.fivecanale.util.EcomConstants.GET_HILIGHTS;
import static com.customer.fivecanale.util.EcomConstants.GET_OFFER_DATA;
import static com.customer.fivecanale.util.EcomConstants.GET_RATINGS;
import static com.customer.fivecanale.util.EcomConstants.GET_SELLERS;
import static com.customer.fivecanale.util.EcomConstants.GET_VARIENT_DATA;
import static com.customer.fivecanale.util.EcomConstants.ITEM_NAME;
import static com.customer.fivecanale.util.EcomConstants.LIKE_DISLIKE;
import static com.customer.fivecanale.util.EcomConstants.OFFER_PRICE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ONE_FIFTY;
import static com.customer.fivecanale.util.EcomConstants.OUT_OF_STOCK_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PEN_COUNT;
import static com.customer.fivecanale.util.EcomConstants.POINT_EIGHT;
import static com.customer.fivecanale.util.EcomConstants.POINT_SIX;
import static com.customer.fivecanale.util.EcomConstants.POINT_THREE;
import static com.customer.fivecanale.util.EcomConstants.POINT_ZERO;
import static com.customer.fivecanale.util.EcomConstants.POSITION;
import static com.customer.fivecanale.util.EcomConstants.PRICE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_NAME;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_PRICE;
import static com.customer.fivecanale.util.EcomConstants.RATING;
import static com.customer.fivecanale.util.EcomConstants.RATING_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.RATING_DATA;
import static com.customer.fivecanale.util.EcomConstants.REVIEW;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_DATA;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_IMAGES;
import static com.customer.fivecanale.util.EcomConstants.SELLER_BOTTOM_SHEET_TAG;
import static com.customer.fivecanale.util.EcomConstants.SPECIFICATIONS;
import static com.customer.fivecanale.util.EcomConstants.TEXT_PLAIN;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.THREE_FIFTY;
import static com.customer.fivecanale.util.EcomConstants.TITLE;
import static com.customer.fivecanale.util.EcomConstants.TOTALRATING_AND_REVIEWS;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.TWO_FIFTY;
import static com.customer.fivecanale.util.EcomConstants.URL;
import static com.customer.fivecanale.util.EcomConstants.USER_REVIEW_DATA;
import static com.customer.fivecanale.util.EcomConstants.VIEW_PAGER_IMAGES;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.BuildConfig;
import com.R;
import com.customer.domain.interactor.PdpUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.common.ImageData;
import com.customer.domain.model.pdp.AttributeRatingData;
import com.customer.domain.model.pdp.AttributesData;
import com.customer.domain.model.pdp.InnerAttributesData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.model.pdp.Rating;
import com.customer.domain.model.pdp.ReviewParameterData;
import com.customer.domain.model.pdp.SupplierData;
import com.customer.domain.model.pdp.UserReviewData;
import com.customer.domain.model.pdp.VariantsData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.alldetails.AllDetailsActivity;
import com.customer.fivecanale.allreviews.AllReviewsActivity;
import com.customer.fivecanale.allreviews.ReviewImgClick;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.cart.EcomCartActivity;
import com.customer.fivecanale.pdp.adapters.AllOffersAdapter;
import com.customer.fivecanale.pdp.adapters.HighLightsAdapter;
import com.customer.fivecanale.pdp.adapters.PdpVariantsAdapter;
import com.customer.fivecanale.pdp.adapters.ProductRatingOnAdapter;
import com.customer.fivecanale.pdp.adapters.ProductReviewImagesAdapter;
import com.customer.fivecanale.pdp.adapters.ProductsViewPagerAdapter;
import com.customer.fivecanale.pdp.adapters.RatingDistributionsAdapter;
import com.customer.fivecanale.pdp.adapters.ReviewsAdapter;
import com.customer.fivecanale.pdp.adapters.SpecificationsAdapter;
import com.customer.fivecanale.pdp.adapters.ViewMoreSellersAdapter;
import com.customer.fivecanale.pdp.attributebottomsheet.AttributesBottomSheetFragment;
import com.customer.fivecanale.pdp.sellerbottomsheet.SellerBottomSheetFragment;
import com.customer.fivecanale.preview.PreviewImageActivity;
import com.customer.fivecanale.review.ReviewProductActivity;
import com.customer.fivecanale.uiutil.AnimUtil;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtilBuilder;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.LocationManagerUtil;
import com.customer.fivecanale.webview.WebViewAct;
import com.databinding.ActivityProductDetailsBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * <h1>This class is used to show the details of the particular product.</h1>
 */
public class ProductDetailsActivity extends DaggerAppCompatActivity implements
    PdpVariantsClickListener, PopupMenu.OnMenuItemClickListener, ReviewsMenuClick, ReviewImgClick,
    CustomDialogUtilBuilder.DialogOutOfStockNotifyListener, ViewMoreSellersOnClick,
    OfferItemOnClick,
    LocationManagerUtil.FusedLocationListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  AttributesBottomSheetFragment mAttributesBottomSheetFragment;
  @Inject
  SellerBottomSheetFragment mSellerBottomSheetFragment;
  @Inject
  UserInfoHandler mUserInfoHandler;
  private ProductDetailsViewModel mProductDetailsViewModel;
  private ActivityProductDetailsBinding mBinding;
  private ArrayList<ImageData> mMobileImages = new ArrayList<>();
  private ArrayList<String> mReviewImages = new ArrayList<>();
  private ProductsViewPagerAdapter mProductsViewPagerAdapter;
  private HighLightsAdapter mHighLightsAdapter;
  private SpecificationsAdapter mSpecificationsAdapter;
  private ProductRatingOnAdapter mProductRatingOnAdapter;
  private ProductReviewImagesAdapter mProductReviewImagesAdapter;
  private RatingDistributionsAdapter mRatingDistributionsAdapter;
  private ReviewsAdapter mReviewsAdapter;
  private PdpOfferData mOffer;
  private ArrayList<String> mHighLightsList = new ArrayList<>();
  private ArrayList<InnerAttributesData> mSpecialitiesList = new ArrayList<>();
  private ArrayList<AttributesData> mAttributesData = new ArrayList<>();
  private ArrayList<AttributeRatingData> mAttributeRatingData = new ArrayList<>();
  private ArrayList<UserReviewData> mUserReviewDataArrayList = new ArrayList<>();
  private ArrayList<Rating> mUserRatingData = new ArrayList<>();
  private ArrayList<VariantsData> mVariantsData = new ArrayList<>();
  private ArrayList<SupplierData> mSupplierArrayList = new ArrayList<>();
  private ArrayList<PdpOfferData> mAllOffersArrayList = new ArrayList<>();
  private ViewMoreSellersAdapter mViewMoreSellersAdapter;
  private AllOffersAdapter mAllOffersAdapter;
  private int mDotsCount;
  private ImageView[] mDots;
  private String mParentProductId, mProductId, mReviewId;
  private PdpVariantsAdapter mPdpVariantsAdapter;
  private int mPosition;
  private UserReviewData mUserReviewData;
  private int mReviewPos, mAvailableCount, mCartCount;
  private String mUnitId, mStoreId;
  private PdpOfferData mOffersListData;
  private double mLat, mLan;
  private String mDescription = "";
  private int moreIndex = ONE, mLineCount;

  public ProductDetailsActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    initializeViewPager();
    initializeHighLightsAdapter();
    initializeSpecificationsAdapter();
    initializeRatingDistributionAdapter();
    initializeProductReviewImagesAdapter();
    initializeReviewsAdapter();
    initializeRatingOnAdapter();
    initializeVariantsAdapter();
    initializeOffersAdapter();
    initializeViewMoreSellersAdapter();
    subscribeBackButton();
    subscribeVariantData();
    subscribeLiveData();
    subscribeShortLink();
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      mParentProductId = bundle.getString(PARENT_PRODUCT_ID);
      mProductId = bundle.getString(PRODUCT_ID);
      if (!LocationManagerUtil.isGpsEnabled(this)) {
        mProductDetailsViewModel.callProductDetailsApi(mProductId, mParentProductId,
            DEFAULT_LAT_LANG, DEFAULT_LAT_LANG);
      } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
          LocationManagerUtil.getFuseLocation(this, this::onSuccess);
        } else {
          mProductDetailsViewModel.callProductDetailsApi(mProductId, mParentProductId,
              DEFAULT_LAT_LANG, DEFAULT_LAT_LANG);
        }
      }
    }
  }

  /**
   * initialize adapter for view more sellers
   */
  private void initializeViewMoreSellersAdapter() {
    mViewMoreSellersAdapter = new ViewMoreSellersAdapter(mSupplierArrayList, this);
    mBinding.rvViewSellersDetails.setAdapter(mViewMoreSellersAdapter);
  }

  /**
   * initialize adapter for view more sellers
   */
  private void initializeOffersAdapter() {
    mAllOffersAdapter = new AllOffersAdapter(mAllOffersArrayList, this::onOfferItemClick);
    mBinding.rvPdpAllOffers.setAdapter(mAllOffersAdapter);
  }

  /**
   * open the login activity screen
   */
  private void startBoardingAct() {
    Intent intent = new Intent(this, EcomLoginActivity.class);
    startActivity(intent);
    AnimUtil.activityTransitionAnimation(this);
  }

  private void startAllDetailsAct(String title) {
    Intent intent = new Intent(this, AllDetailsActivity.class);
    intent.putExtra(SPECIFICATIONS, title);
    intent.putExtra(ATTRIBUTES_DATA, mAttributesData);
    intent.putExtra(ITEM_NAME, mBinding.tvPdpProductName.getText().toString());
    intent.putExtra(PRODUCT_IMAGE, mProductDetailsViewModel.mPrimaryImage);
    intent.putExtra(PRODUCT_COLOUR, mProductDetailsViewModel.mColor);
    intent.putExtra(PRODUCT_PRICE, mBinding.tvPdpProductPrice.getText().toString());
    intent.putExtra(OFFER_PRICE, mProductDetailsViewModel.mOfferPrice);
    startActivity(intent);
  }

  /**
   * start activity for review product activity
   */
  private void startRateProductAct() {
    Intent intent = new Intent(this, ReviewProductActivity.class);
    intent.putExtra(PRODUCT_IMAGE, mProductDetailsViewModel.mPrimaryImage);
    intent.putExtra(PRODUCT_COLOUR, mProductDetailsViewModel.mColor);
    intent.putExtra(PRODUCT_NAME, mBinding.tvPdpProductName.getText().toString());
    intent.putExtra(PRICE, mBinding.tvPdpProductPrice.getText().toString());
    intent.putExtra(PARENT_PRODUCT_ID, mParentProductId);
    intent.putExtra(PRODUCT_ID, mProductId);
    startActivity(intent);
  }

  /**
   * sets the adapter class for variants mData
   */
  private void initializeVariantsAdapter() {
    mPdpVariantsAdapter = new PdpVariantsAdapter(mVariantsData, this);
    mBinding.rvPdpVariants.setAdapter(mPdpVariantsAdapter);
  }

  /**
   * open the allReviewsActivity
   */
  private void startAllReviewsAct(Integer penCount) {
    Intent intent = new Intent(this, AllReviewsActivity.class);
    intent.putExtra(RATING_DATA, mUserRatingData);
    intent.putExtra(PARENT_PRODUCT_ID, mParentProductId);
    intent.putExtra(RATING, mBinding.tvPdpCurrentRateVal.getText().toString());
    intent.putExtra(TOTALRATING_AND_REVIEWS,
        mBinding.tvPdpRatingsReviewDetails.getText().toString());
    intent.putExtra(PEN_COUNT, penCount);
    startActivityForResult(intent, ALL_REVIEWS_CODE);
  }

  /**
   * start the review image activity
   *
   * @param pos       position of the review
   * @param reviewPos in that review position position of the review image.
   */
  private void startPreviewImgActivity(int pos, int reviewPos) {
    mUserReviewData = mUserReviewDataArrayList.get(reviewPos);
    this.mReviewPos = reviewPos;
    Intent intent = new Intent(this, PreviewImageActivity.class);
    intent.putExtra(POSITION, pos);
    intent.putExtra(USER_REVIEW_DATA, mUserReviewData);
    startActivityForResult(intent, ALL_PREVIEW_CODE);
  }

  /**
   * sets the adapter class for review mData
   */
  private void initializeReviewsAdapter() {
    mReviewsAdapter = new ReviewsAdapter(mUserReviewDataArrayList, this, this, TRUE);
    mBinding.rvPdpProductReviews.setAdapter(mReviewsAdapter);
  }

  /**
   * sets the adapter class rating mData
   */
  private void initializeRatingDistributionAdapter() {
    mRatingDistributionsAdapter = new RatingDistributionsAdapter(mUserRatingData);
    mBinding.rvPdpRatingDistribution.setAdapter(mRatingDistributionsAdapter);
  }

  /**
   * sets the adapter for  user ratings
   */
  private void initializeRatingOnAdapter() {
    mProductRatingOnAdapter = new ProductRatingOnAdapter(mAttributeRatingData);
    mBinding.rvPdpRatingOn.setAdapter(mProductRatingOnAdapter);
  }

  /**
   * sets the adapter class for review images
   */
  private void initializeProductReviewImagesAdapter() {
    mProductReviewImagesAdapter = new ProductReviewImagesAdapter(mReviewImages);
    mBinding.rvPdpProductReviewsImages.setAdapter(mProductReviewImagesAdapter);
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    mBinding.vPPdpProductImages.getLayoutParams().height = displayMetrics.widthPixels;
    mBinding.vPPdpProductImages.requestFocus();
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mProductDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        ProductDetailsViewModel.class);
    mBinding.setViewmodel(mProductDetailsViewModel);
    mProductDetailsViewModel.getDeviceDetails(EcomUtil.getIpAddress(this));
    // EcomUtil.generateDeepLink(getIntent(), this);
    setupNestedScrollView();
    mProductDetailsViewModel.onCartUpdate();
  }

  /**
   * viewpager to show the products
   */
  private void initializeViewPager() {
    mProductsViewPagerAdapter = new ProductsViewPagerAdapter(this, mMobileImages);
    mBinding.vPPdpProductImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {
        EcomUtil.setViewPagerSelectedDot(mBinding.vPPdpProductImages.getContext(), mDots,
            mDotsCount, position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });
  }

  /**
   * this will show the out of stock report dialog
   */
  private void showOutOfStockReportDialog(String userEmail) {
    CustomDialogUtilBuilder.CustomDialogBuilder customDialogUtilBuilder =
        new CustomDialogUtilBuilder.CustomDialogBuilder(this, this, THREE);
    customDialogUtilBuilder.setMailId(userEmail);
    customDialogUtilBuilder.buildCustomDialog();
  }

  /**
   * initialize the highlights adapter.
   */
  private void initializeHighLightsAdapter() {
    mHighLightsAdapter = new HighLightsAdapter(mHighLightsList);
    mBinding.rvPdpHighlights.setAdapter(mHighLightsAdapter);
  }

  /**
   * initializes the specifications adapter.
   */
  private void initializeSpecificationsAdapter() {
    mSpecificationsAdapter = new SpecificationsAdapter(mSpecialitiesList, TRUE);
    mBinding.rvPdpSpecifications.setAdapter(mSpecificationsAdapter);
  }

  /**
   * set the dot count for selected item in the viewpager..
   */
  private void setUiPageViewController() {
    mBinding.llPdpProductImagePosition.removeAllViews();
    mDotsCount = mProductsViewPagerAdapter.getCount();
    mDots = new ImageView[mDotsCount];
    if (mDotsCount > ZERO) {
      EcomUtil.setViewPagerDots(this, mDots, mDotsCount, mBinding.llPdpProductImagePosition);
    }
  }

  /**
   * it shows the mError message
   *
   * @param error mError message.
   */
  public void onError(String error) {
    Snackbar.make(mBinding.clPdp, error, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * subscribe for the back button on click
   */
  private void subscribeBackButton() {
    mProductDetailsViewModel.onClick().observe(this, result -> {
      switch (result) {
        case CROSS_ICON:
          finish();
          break;
        case OPEN_LOGIN:
          startActivity(new Intent(this, EcomLoginActivity.class));
          break;
        case SIZE_CHART:
          startWebViewAct();
          break;
        case SELLER_INFORMATION:
          break;
        case GOTO_CART:
          startGoToCartAct();
          break;
        case RATING_AND_REVIEWS:
          mBinding.nsPdp.smoothScrollTo(ZERO, (int) mBinding.tvPdpRateOption.getY() - FIFTY);
          break;
        case MORE:
          moreIndex += ONE;
          setLinesCount(mDescription, TRUE);
          break;
        case LOGIN:
          startBoardingAct();
          break;
        case OPEN_SHEET:
          onVariantClick(mVariantsData, mUnitId);
          break;
      }
    });
  }

  /*subscribes the live data*/
  public void subscribeLiveData() {
    mProductDetailsViewModel.getLiveData().observe(this, new Observer<Pair<String, Object>>() {
      @Override
      public void onChanged(Pair<String, Object> integerObjectPair) {
        if (integerObjectPair.first != null && integerObjectPair.second != null) {
          switch (integerObjectPair.first) {
            case REVIEW:
              openSellerBottomSheet((ArrayList<ReviewParameterData>) integerObjectPair.second);
              break;
            case REVIEW_CLICKED:
              startAllReviewsAct((Integer) integerObjectPair.second);
              break;
            case ALL_DETAILS_CLICKED:
              startAllDetailsAct((String) integerObjectPair.second);
              break;
            case RATING_CLICKED:
              if ((Boolean) integerObjectPair.second) {
                if (mUserInfoHandler.isUserLoggedIn()) {
                  startRateProductAct();
                } else {
                  startBoardingAct();
                }
              }
              break;
            case LIKE_DISLIKE:
              UserReviewData userReviewData = mUserReviewDataArrayList.get(mPosition);
              if ((boolean) integerObjectPair.second) {
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
              break;
            case ERROR_MSG:
              onError((String) integerObjectPair.second);
              break;
            case VIEW_PAGER_IMAGES:
              mBinding.vPPdpProductImages.setAdapter(null);
              mMobileImages.clear();
              mMobileImages.addAll((ArrayList<ImageData>) integerObjectPair.second);
              mBinding.vPPdpProductImages.setAdapter(mProductsViewPagerAdapter);
              mProductsViewPagerAdapter.notifyDataSetChanged();
              setUiPageViewController();
              break;
            case REVIEW_IMAGES:
              mReviewImages.clear();
              mReviewImages.addAll((ArrayList<String>) integerObjectPair.second);
              mProductReviewImagesAdapter.notifyDataSetChanged();
              break;
            case REVIEW_DATA:
              mUserReviewDataArrayList.clear();
              mUserReviewDataArrayList.addAll((ArrayList<UserReviewData>) integerObjectPair.second);
              mReviewsAdapter.notifyDataSetChanged();
              break;
            case GET_RATINGS:
              mUserRatingData.clear();
              mUserRatingData.addAll((ArrayList<Rating>) integerObjectPair.second);
              mRatingDistributionsAdapter.notifyDataSetChanged();
              break;
            case GET_HILIGHTS:
              mHighLightsList.clear();
              mHighLightsList.addAll((ArrayList<String>) integerObjectPair.second);
              mHighLightsAdapter.notifyDataSetChanged();
              break;
            case GET_ATTRIBUTES:
              mSpecialitiesList.clear();
              mAttributesData.clear();
              mAttributesData.addAll((ArrayList<AttributesData>) integerObjectPair.second);
              if (mAttributesData != null && mAttributesData.size() > ZERO) {
                mSpecialitiesList.addAll(mAttributesData.get(ZERO).getInnerAttributes());
                mSpecificationsAdapter.notifyDataSetChanged();
              }
              break;
            case GET_ATTRIBUTES_RATING:
              mAttributeRatingData.clear();
              mAttributeRatingData.addAll(
                  (ArrayList<AttributeRatingData>) integerObjectPair.second);
              mProductRatingOnAdapter.notifyDataSetChanged();
              break;
            case GET_VARIENT_DATA:
              if (integerObjectPair.second != null) {
                mVariantsData.clear();
                mVariantsData.addAll((ArrayList<VariantsData>) integerObjectPair.second);
                mPdpVariantsAdapter.notifyDataSetChanged();
              }
              break;
            case GET_OFFER_DATA:
              if (integerObjectPair.second != null) {
                mOffer = (PdpOfferData) integerObjectPair.second;
              }
              break;
            case OUT_OF_STOCK_CLICKED:
              showOutOfStockReportDialog(
                  (Boolean) integerObjectPair.second ? mUserInfoHandler.getUserEmail() : "");
              break;
            case GET_SELLERS:
              mSupplierArrayList.clear();
              mSupplierArrayList.addAll((ArrayList<SupplierData>) integerObjectPair.second);
              EcomUtil.printLog("exe" + "mSupplierArrayList" + mSupplierArrayList.size());
              mViewMoreSellersAdapter.notifyDataSetChanged();
              mBinding.rvViewSellersDetails.setVisibility(
                  mSupplierArrayList.size() > ONE ? View.VISIBLE : View.GONE);
              break;
            case GET_DESCRIPTION:
              mDescription = (String) integerObjectPair.second;
              mBinding.tvPdpProductDescription.setText(mDescription);
              EcomUtil.printLog("exe" + "description" + mDescription);
              if (!mDescription.isEmpty()) {
                mBinding.tvPdpProductDescription.post(() -> {
                  // Use lineCount here
                  mLineCount = mBinding.tvPdpProductDescription.getLineCount();
                  EcomUtil.printLog("exe" + "lineCount" + mLineCount);
                  setLinesCount(mDescription, FALSE);
                  if (mLineCount <= FOUR) {
                    mBinding.tvPdpProductDesMore.setVisibility(View.GONE);
                  }
                });
              }
              break;
            case ALL_OFFERS:
              mAllOffersArrayList.clear();
              mAllOffersArrayList.addAll((ArrayList<PdpOfferData>) integerObjectPair.second);
              mAllOffersAdapter.notifyDataSetChanged();
              break;
          }
        }
      }
    });
  }

  /**
   * subscribes to variant data
   */
  private void subscribeVariantData() {
    mProductDetailsViewModel.getVariants().observe(this, integerObjectHashMap -> {
      mUnitId = (String) integerObjectHashMap.get(ONE);
      mStoreId = (String) integerObjectHashMap.get(TWO);
      ArrayList<VariantsData> variantsData = (ArrayList<VariantsData>) integerObjectHashMap.get(
          THREE);
      mOffersListData = (PdpOfferData) integerObjectHashMap.get(FOUR);
      mAvailableCount = (Integer) integerObjectHashMap.get(FIVE);
      // mCartCount=(Integer)integerObjectHashMap.get(SIX);
      mAvailableCount = (Integer) integerObjectHashMap.get(FIVE);
      if (variantsData != null) {
        mVariantsData.clear();
        mVariantsData.addAll(variantsData);
        mPdpVariantsAdapter.notifyDataSetChanged();
      }
    });
  }

  /**
   * send the mData to the viewModel to set the mData.
   *
   * @param responseValues response for the pdp.
   */
  public void getProductData(PdpUseCase.ResponseValues responseValues) {
    mProductDetailsViewModel.parsePdpResponseValues(responseValues);
  }

  /**
   * this method is used to show the add to cart button status
   *
   * @param isAddToCart used to show the addTocart button status
   */
  public void addToCartStatus(boolean isAddToCart) {
    mProductDetailsViewModel.addToCartTxt.set(
        isAddToCart ? ApplicationManager.getInstance().getString(R.string.pdpAddGoCart)
            : ApplicationManager.getInstance().getString(R.string.pdpAddToCart));
  }

  /**
   * this method is used to set the total cart count from bottom sheet fragment
   */
  public void setCartCount() {
    mProductDetailsViewModel.setCartStatus();
  }

  @Override
  public void onVariantClick(ArrayList<VariantsData> variantsData, String unitId) {
    if (variantsData != null && variantsData.size() > ONE) {
      if (!mAttributesBottomSheetFragment.isAdded()) {
        mAttributesBottomSheetFragment.setItemData(mBinding.tvPdpProductPrice.getText().toString(),
            mProductDetailsViewModel.mOfferPrice,
            mBinding.tvPdpProductName.getText().toString(),
            variantsData, mProductId, mParentProductId, unitId, mStoreId, mAvailableCount,
            mProductDetailsViewModel.productOutOfStock.get(), mCartCount, mOffersListData, mOffer);
        mAttributesBottomSheetFragment.show(getSupportFragmentManager(),
            ATTRIBUTES_BOTTOM_SHEET_TAG);
      }
    }
  }

  /**
   * this method  will open the seller bottom sheet
   */
  private void openSellerBottomSheet(ArrayList<ReviewParameterData> reviewParameterData) {
    if (!mSellerBottomSheetFragment.isAdded()) {
      mSellerBottomSheetFragment.setItemData(mBinding.tvPdpSellerName.getText().toString(),
          mProductDetailsViewModel.getSellerSince(),
          reviewParameterData);
      mSellerBottomSheetFragment.show(getSupportFragmentManager(),
          SELLER_BOTTOM_SHEET_TAG);
    }
  }

  /**
   * subscribe to short link
   */
  private void subscribeShortLink() {
    mProductDetailsViewModel.getShortLink().observe(this, shortLink -> {
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType(TEXT_PLAIN);
      intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
      intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
      startActivity(intent);
    });
  }

  /**
   * this method  will open the seller bottom sheet
   */
  private void openTermsAndConBottomSheet(String termsAndConditions) {
    if (!mSellerBottomSheetFragment.isAdded()) {
      mSellerBottomSheetFragment.setTermsAndConditions(termsAndConditions);
      mSellerBottomSheetFragment.show(getSupportFragmentManager(),
          SELLER_BOTTOM_SHEET_TAG);
    }
  }

  /**
   * start the web view activity
   */
  private void startWebViewAct() {
    Intent intent = new Intent(this, WebViewAct.class);
    intent.putExtra(URL, BuildConfig.SIZE_CHART_URL + mParentProductId);
    intent.putExtra(TITLE, getResources().getString(R.string.pdpSizeChart));
    startActivity(intent);
  }

  /**
   * listener for menu item click for review abort.
   *
   * @param item menu item references
   * @return returns boolean
   */
  @Override
  public boolean onMenuItemClick(MenuItem item) {
    if (item.getItemId() == R.id.flagAsAbusive) {
      if (mUserInfoHandler.isUserLoggedIn()) {
        mProductDetailsViewModel.callReportReviewApi(mReviewId);
      } else {
        startBoardingAct();
      }
      return true;
    }
    return false;
  }

  /**
   * menu item click call back
   */
  @Override
  public void onMenuClick(int likeOrMenu, int pos, AppCompatImageView optionMenu,
      boolean likeOrDislike) {
    if (!mUserInfoHandler.isUserLoggedIn()) {
      startBoardingAct();
      return;
    }
    UserReviewData reviewData = mUserReviewDataArrayList.get(pos);
    this.mReviewId = reviewData.getReviewId();
    this.mPosition = pos;
    switch (likeOrMenu) {
      case ONE:
        PopupMenu popup = new PopupMenu(this, optionMenu, Gravity.TOP);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.item_reviews_menu);
        popup.show();
        break;
      case TWO:
        mProductDetailsViewModel.callLikeOrDislikeApi(mReviewId, likeOrDislike);
        break;
    }
  }

  /**
   * listens when we click on the review image
   */
  @Override
  public void onImgClick(int pos, int reviewPos) {
    startPreviewImgActivity(pos, reviewPos);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case ALL_REVIEWS_CODE:
        if (data != null && resultCode == RESULT_OK) {
          ArrayList<UserReviewData> userReviewData = data.getParcelableArrayListExtra(
              USER_REVIEW_DATA);
          mUserReviewDataArrayList.clear();
          mUserReviewDataArrayList.addAll(userReviewData);
          mReviewsAdapter.notifyDataSetChanged();
        }
        break;
      case ALL_PREVIEW_CODE:
        if (data != null && resultCode == RESULT_OK) {
          mUserReviewData = data.getParcelableExtra(USER_REVIEW_DATA);
          mUserReviewDataArrayList.remove(mReviewPos);
          mUserReviewDataArrayList.add(mReviewPos, mUserReviewData);
          mReviewsAdapter.notifyItemChanged(mReviewPos);
        }
    }
  }

  /**
   * set up the nested scroll view listener
   */
  private void setupNestedScrollView() {
    mBinding.include.tvPdpBrandName.setAlpha(POINT_ZERO);
    mBinding.nsPdp.setOnScrollChangeListener(
        (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
          if (scrollY < ONE_FIFTY) {
            mBinding.include.tvPdpBrandName.setAlpha(POINT_ZERO);
          } else if (scrollY > ONE_FIFTY && scrollY < TWO_FIFTY) {
            mBinding.include.tvPdpBrandName.setAlpha(POINT_THREE);
          } else if (scrollY > TWO_FIFTY && scrollY < THREE_FIFTY) {
            mBinding.include.tvPdpBrandName.setAlpha(POINT_SIX);
          } else if (scrollY > THREE_FIFTY && scrollY < FIVE_HUNDRED) {
            mBinding.include.tvPdpBrandName.setAlpha(POINT_EIGHT);
          } else {
            mBinding.include.tvPdpBrandName.setAlpha(ONE);
          }
        });
  }

  @Override
  public void onNotifyMail(String mail) {
    if (!mail.isEmpty()) {
      EcomUtil.printLog("eMail" + mail);
      mProductDetailsViewModel.callNotifyProductApi(mail, mParentProductId);
    } else {
      onError(getResources().getString(R.string.pdpPleaseEnterMail));
    }
  }

  /**
   * it will open the cart activity
   */
  private void startGoToCartAct() {
    Intent intent = new Intent(this, EcomCartActivity.class);
    startActivity(intent);
  }

  @Override
  public void onOfferItemClick(String value) {
    if (value != null && !value.isEmpty()) {
      openTermsAndConBottomSheet(value);
    }
  }

  @Override
  public void onSuccess(double latitude, double longitude) {
    mLat = latitude;
    mLan = longitude;
    mUserInfoHandler.setLatLong(latitude, longitude);
    mProductDetailsViewModel.callProductDetailsApi(mProductId,
        mParentProductId, mLat, mLan);
  }

  /**
   * get the count of lines
   */
  private void setLinesCount(String description, boolean isClicked) {
    mBinding.tvPdpProductDescription.setMaxLines(!isClicked ? (FOUR * moreIndex) : mLineCount);
    mBinding.tvPdpProductDescription.setEllipsize(
        mLineCount > FOUR * moreIndex ? TextUtils.TruncateAt.END : null);
    mBinding.tvPdpProductDesMore.setVisibility(
        (!isClicked) ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onClick(int pos) {
    SupplierData supplierData = mSupplierArrayList.get(pos);
    if (supplierData != null) {
      mProductDetailsViewModel.callProductDetailsApi(supplierData.getSupplier().getProductId(),
          mParentProductId, mLat, mLan);
    }
  }
}