package com.customer.fivecanale.pdp;

import static com.customer.fivecanale.pdp.PdpUiAction.CROSS_ICON;
import static com.customer.fivecanale.pdp.PdpUiAction.GOTO_CART;
import static com.customer.fivecanale.pdp.PdpUiAction.LOGIN;
import static com.customer.fivecanale.pdp.PdpUiAction.MORE;
import static com.customer.fivecanale.pdp.PdpUiAction.OPEN_LOGIN;
import static com.customer.fivecanale.pdp.PdpUiAction.RATING_AND_REVIEWS;
import static com.customer.fivecanale.pdp.PdpUiAction.SIZE_CHART;
import static com.customer.fivecanale.util.EcomConstants.ADD_CART;
import static com.customer.fivecanale.util.EcomConstants.ALL_DETAILS_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.ALL_OFFERS;
import static com.customer.fivecanale.util.EcomConstants.DEFAULT_LAT_LANG;
import static com.customer.fivecanale.util.EcomConstants.DELETE_CART;
import static com.customer.fivecanale.util.EcomConstants.ERROR_MSG;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GET_ATTRIBUTES;
import static com.customer.fivecanale.util.EcomConstants.GET_ATTRIBUTES_RATING;
import static com.customer.fivecanale.util.EcomConstants.GET_DESCRIPTION;
import static com.customer.fivecanale.util.EcomConstants.GET_HILIGHTS;
import static com.customer.fivecanale.util.EcomConstants.GET_RATINGS;
import static com.customer.fivecanale.util.EcomConstants.GET_SELLERS;
import static com.customer.fivecanale.util.EcomConstants.GET_VARIENT_DATA;
import static com.customer.fivecanale.util.EcomConstants.LIKE_DISLIKE;
import static com.customer.fivecanale.util.EcomConstants.LINK;
import static com.customer.fivecanale.util.EcomConstants.MULTI_STORE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.OUT_OF_STOCK_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.RATING_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.RETAILER;
import static com.customer.fivecanale.util.EcomConstants.REVIEW;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_CLICKED;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_DATA;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_IMAGES;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_CART;
import static com.customer.fivecanale.util.EcomConstants.VIEW_PAGER_IMAGES;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.net.Uri;
import androidx.core.util.Pair;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.BuildConfig;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.AddProductToCartUseCase;
import com.customer.domain.interactor.AddProductToWishListUseCase;
import com.customer.domain.interactor.DeleteWishListProductUseCase;
import com.customer.domain.interactor.GetSellerListUseCase;
import com.customer.domain.interactor.LikeDisLikeReviewUseCase;
import com.customer.domain.interactor.NotifyProductAvailabilityUseCase;
import com.customer.domain.interactor.PdpUseCase;
import com.customer.domain.interactor.ReportReviewUseCase;
import com.customer.domain.interactor.UpdateCartUseCase;
import com.customer.domain.interactor.handler.CartHandler;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.common.FinalPriceListData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.model.pdp.ProductModel;
import com.customer.domain.model.pdp.ReviewData;
import com.customer.domain.model.pdp.SupplierData;
import com.customer.domain.model.pdp.VariantsData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.EcomUtil;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javax.inject.Inject;

/**
 * view model class for the product detail activity
 */
public class ProductDetailsViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> progress = new ObservableField<>(FALSE);
  public ObservableField<Boolean> otherSellers = new ObservableField<>(FALSE);
  public ObservableField<String> otherSellersCount = new ObservableField<>();
  public ObservableField<Boolean> allDetHighLights = new ObservableField<>(FALSE);
  public ObservableField<Boolean> productCartOptions = new ObservableField<>(TRUE);
  public ObservableField<Boolean> productAddToCart = new ObservableField<>(FALSE);
  public ObservableField<Boolean> productOutOfStock = new ObservableField<>(FALSE);
  public ObservableField<Boolean> reviewsCountVisible = new ObservableField<>(TRUE);
  public ObservableField<Boolean> offerViews = new ObservableField<>(FALSE);
  public ObservableField<Boolean> allDetSpecifications = new ObservableField<>(FALSE);
  public ObservableField<Boolean> productOnOffer = new ObservableField<>(FALSE);
  public ObservableField<Boolean> sizeChartVisible = new ObservableField<>(TRUE);
  public ObservableField<Boolean> productHighLights = new ObservableField<>(FALSE);
  public ObservableField<Boolean> sellerInForm = new ObservableField<>(FALSE);
  public ObservableField<Boolean> highLightsVisibility = new ObservableField<>(TRUE);
  public ObservableField<Boolean> productSpecifications = new ObservableField<>(FALSE);
  public ObservableField<Boolean> cartCountVisibility = new ObservableField<>(FALSE);
  public ObservableField<Boolean> availableOffers = new ObservableField<>(TRUE);
  public ObservableField<Integer> productAddedFav = new ObservableField<>();
  public ObservableField<String> productName = new ObservableField<>();
  public ObservableField<String> totalReviews = new ObservableField<>();
  public ObservableField<String> sellerName = new ObservableField<>();
  public ObservableField<String> productSpecificationTitle = new ObservableField<>();
  public ObservableField<String> avgRating = new ObservableField<>();
  public ObservableField<String> sellerRating = new ObservableField<>();
  public ObservableField<String> totalRating = new ObservableField<>();
  public ObservableField<String> productPrice = new ObservableField<>();
  public ObservableField<String> totalStarRating = new ObservableField<>();
  public ObservableField<String> totalRatingAndReviews = new ObservableField<>();
  public ObservableField<String> productActualPrice = new ObservableField<>();
  public ObservableField<String> brandName = new ObservableField<>();
  public ObservableField<String> cartCount = new ObservableField<>();
  public ObservableField<String> productOfferPer = new ObservableField<>();
  public ObservableField<String> addToCartTxt = new ObservableField<>();
  public ObservableField<String> currentQuantity = new ObservableField<>();
  private PdpOfferData mOffer;
  private String mShareLink = BuildConfig.APP_LINK;
  @Inject
  public UserInfoHandler mUserInfoHandler;
  String mPrimaryImage, mColor;
  @Inject
  CartHandler mCartHandler;
  String mOfferPrice = "";
  private PdpUseCase mPdpUseCase;
  private String mDeviceIpAddress = "";
  private ReportReviewUseCase mReportReviewUseCase;
  private NotifyProductAvailabilityUseCase mNotifyProductAvailabilityUseCase;
  private LikeDisLikeReviewUseCase mLikeDisLikeReviewUseCase;
  private DeleteWishListProductUseCase mRemoveWishListUseCase;
  private AddProductToWishListUseCase mAddProductToWishListUseCase;
  private UpdateCartUseCase mUpdateCartUseCase;
  private UseCaseHandler handler;
  private MutableLiveData<PdpUiAction> mClick = new MutableLiveData<>();
  private MutableLiveData<Pair<String, Object>> mLiveData =
      new MutableLiveData<>();
  private MutableLiveData<HashMap<Integer, Object>> mVariants =
      new MutableLiveData<>();
  private MutableLiveData<Uri> mShortLink = new MutableLiveData<>();
  private String mProductId, mParentProductId;
  private ReviewData mReviewData;
  private int mSellerCount, mAvailableCount;
  private SupplierData mSupplierData;
  private String mSellerSince;
  private String mUnitId, mStoreId;
  private PdpOfferData mPdpOfferData;
  private AddProductToCartUseCase mAddProductToCartUseCase;
  private GetSellerListUseCase mGetSellerListUseCase;
  private int mVarientSize;

  /**
   * constructor for this mData
   *
   * @param pdpUseCase pdp use case
   * @param handler    handler for this product detail view model.
   */
  @Inject
  ProductDetailsViewModel(PdpUseCase pdpUseCase, ReportReviewUseCase reportReviewUseCase,
      LikeDisLikeReviewUseCase likeDisLikeReviewUseCase,
      AddProductToWishListUseCase addProductToWishListUseCase,
      DeleteWishListProductUseCase deleteWishListProductUseCase,
      UpdateCartUseCase updateCartUseCase,
      NotifyProductAvailabilityUseCase notifyProductAvailabilityUseCase,
      AddProductToCartUseCase addProductToCartUseCase, GetSellerListUseCase getSellerListUseCase,
      UseCaseHandler handler) {
    this.mPdpUseCase = pdpUseCase;
    this.mUpdateCartUseCase = updateCartUseCase;
    this.mReportReviewUseCase = reportReviewUseCase;
    this.mLikeDisLikeReviewUseCase = likeDisLikeReviewUseCase;
    this.mAddProductToWishListUseCase = addProductToWishListUseCase;
    this.mRemoveWishListUseCase = deleteWishListProductUseCase;
    this.mNotifyProductAvailabilityUseCase = notifyProductAvailabilityUseCase;
    this.mAddProductToCartUseCase = addProductToCartUseCase;
    this.mGetSellerListUseCase = getSellerListUseCase;
    this.handler = handler;
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
   * call the product details api
   *
   * @param productId       product id
   * @param parentProductId parent product id
   */
  void callProductDetailsApi(String productId, String parentProductId, double lat, double lan) {
    progressVisible.set(TRUE);
    productCartOptions.set(FALSE);
    mProductId = productId;
    mParentProductId = parentProductId;
    DisposableSingleObserver<PdpUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<PdpUseCase.ResponseValues>() {
          @Override
          public void onSuccess(PdpUseCase.ResponseValues responseValues) {
            mSellerCount = ZERO;
            parsePdpResponseValues(responseValues);
            EcomUtil.printLog("http" + "mSellerCount" + mSellerCount);
            if (mSellerCount > ONE) {
              callGetSellersApi(mProductId, mParentProductId);
            } else {
              progressVisible.set(FALSE);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            productCartOptions.set(TRUE);
            mLiveData.postValue(Pair.create(ERROR_MSG, (Object) e.getMessage()));
            EcomUtil.printLog("Pdp Fail" + e.getMessage());
          }
        };
    handler.execute(mPdpUseCase, new PdpUseCase.RequestValues(productId, parentProductId, lat, lan),
        disposableSingleObserver);
  }

  /** This method is using for subscribing to User cart data */
  void onCartUpdate() {
    EcomUtil.printLog("onCartUpdatePdp");
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getCartDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cartData -> {
              switch (cartData.getAction()) {
                case ADD_CART:
                  if (mProductId.equals(cartData.getProductId())) {
                    addToCartTxt.set(
                        ApplicationManager.getInstance().getString(R.string.pdpAddGoCart));
                    productAddToCart.set(TRUE);
                    currentQuantity.set(String.valueOf(cartData.getNewQuantity()));
                  }
                  setCartStatus();
                  break;
                case UPDATE_CART:
                  if (mProductId.equals(cartData.getProductId())) {
                    addToCartTxt.set(
                        ApplicationManager.getInstance().getString(R.string.pdpAddGoCart));
                    productAddToCart.set(TRUE);
                    currentQuantity.set(String.valueOf(cartData.getNewQuantity()));
                  }
                  break;
                case DELETE_CART:
                  if (mProductId.equals(cartData.getProductId())) {
                    addToCartTxt.set(
                        ApplicationManager.getInstance().getString(R.string.pdpAddToCart));
                    productAddToCart.set(FALSE);
                  }
                  setCartStatus();
                  break;
                default:
                  break;
              }
            }));
  }

  /**
   * <p>calls update cart API</p>
   */
  private void callUpdateCartApi(int action, int newQuantity) {
    progress.set(TRUE);
    DisposableSingleObserver<UpdateCartUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UpdateCartUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UpdateCartUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("UpdateCartCart Succ" + responseValues.getData().getMessage());
            progress.set(FALSE);
            if (action == ADD_CART) {
              cartCount.set(cartCount.get() != null && Integer.parseInt(
                  Objects.requireNonNull(cartCount.get())) < ONE
                  ? String.valueOf(ONE)
                  : String.valueOf(
                      Integer.parseInt(Objects.requireNonNull(cartCount.get())) + ONE));
              cartCountVisibility.set(TRUE);
            }
            addToCartTxt.set(
                (action == ADD_CART || action == UPDATE_CART)
                    ? ApplicationManager.getInstance().getString(
                    R.string.pdpAddGoCart)
                    : ApplicationManager.getInstance().getString(R.string.pdpAddToCart));
            productAddToCart.set((action == ADD_CART || action == UPDATE_CART) ? TRUE : FALSE);
            if (action == ADD_CART || action == UPDATE_CART) {
              currentQuantity.set(String.valueOf(newQuantity));
            } else {
              cartCount.set(Integer.parseInt(
                  Objects.requireNonNull(cartCount.get())) > ZERO ? String.valueOf(
                  Integer.parseInt(Objects.requireNonNull(cartCount.get())) - ONE)
                  : String.valueOf(ONE));
              if (Integer.parseInt(Objects.requireNonNull(cartCount.get())) == ZERO) {
                cartCountVisibility.set(FALSE);
              }
              productCartOptions.set(TRUE);
            }
          }

          @Override
          public void onError(Throwable e) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, (Object) e.getMessage()));
            EcomUtil.printLog("UpdateCartCart Fail" + e.getMessage());
          }
        };
    handler.execute(mUpdateCartUseCase,
        new UpdateCartUseCase.RequestValues(RETAILER, mParentProductId, mProductId, mUnitId,
            mStoreId, MULTI_STORE,
            "", "0", "", newQuantity, ONE, action, MULTI_STORE, mDeviceIpAddress, DEFAULT_LAT_LANG,
            DEFAULT_LAT_LANG),
        disposableSingleObserver);
  }

  /**
   * call the product details api
   *
   * @param reviewId review id
   */
  void callReportReviewApi(String reviewId) {
    progress.set(TRUE);
    DisposableSingleObserver<ReportReviewUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ReportReviewUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ReportReviewUseCase.ResponseValues responseValues) {
            progress.set(FALSE);
            EcomUtil.printLog("REview Succ");
            mLiveData.postValue(
                Pair.create(ERROR_MSG, (Object) responseValues.getData().getMessage()));
          }

          @Override
          public void onError(Throwable e) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, (Object) e.getMessage()));
            EcomUtil.printLog("REview Fail" + e.getMessage());
          }
        };
    handler.execute(mReportReviewUseCase, new ReportReviewUseCase.RequestValues(reviewId),
        disposableSingleObserver);
  }

  /**
   * call the notify  product  api
   */
  void callNotifyProductApi(String email, String parentProductId) {
    progress.set(TRUE);
    DisposableSingleObserver<NotifyProductAvailabilityUseCase.ResponseValues>
        disposableSingleObserver =
        new DisposableSingleObserver<NotifyProductAvailabilityUseCase.ResponseValues>() {
          @Override
          public void onSuccess(NotifyProductAvailabilityUseCase.ResponseValues responseValues) {
            progress.set(FALSE);
            EcomUtil.printLog("Notify Succ");
            mLiveData.postValue(
                Pair.create(ERROR_MSG, (Object) responseValues.getData().getMessage()));
          }

          @Override
          public void onError(Throwable e) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, (Object) e.getMessage()));
            EcomUtil.printLog("Notify Fail" + e.getMessage());
          }
        };
    handler.execute(mNotifyProductAvailabilityUseCase,
        new NotifyProductAvailabilityUseCase.RequestValues(mProductId, email, parentProductId),
        disposableSingleObserver);
  }

  /**
   * call the LikeOrDislike api
   *
   * @param reviewId review id
   */
  void callLikeOrDislikeApi(String reviewId, boolean likeOrDislike) {
    progress.set(TRUE);
    DisposableSingleObserver<LikeDisLikeReviewUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<LikeDisLikeReviewUseCase.ResponseValues>() {
          @Override
          public void onSuccess(LikeDisLikeReviewUseCase.ResponseValues responseValues) {
            progress.set(FALSE);
            EcomUtil.printLog("LIke Succ");
            mLiveData.postValue(Pair.create(LIKE_DISLIKE, likeOrDislike));
          }

          @Override
          public void onError(Throwable e) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, (Object) e.getMessage()));
            EcomUtil.printLog("Like Fail" + e.getMessage());
          }
        };
    if (likeOrDislike) {
      handler.execute(mLikeDisLikeReviewUseCase,
          new LikeDisLikeReviewUseCase.RequestValues(reviewId, likeOrDislike),
          disposableSingleObserver);
    } else {
      handler.execute(mLikeDisLikeReviewUseCase,
          new LikeDisLikeReviewUseCase.RequestValues(TRUE, reviewId),
          disposableSingleObserver);
    }
  }

  /**
   * call to add to wishList api
   *
   * @param productId product id
   */
  private void callAddWishListApi(String productId) {
    progress.set(TRUE);
    DisposableSingleObserver<AddProductToWishListUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<AddProductToWishListUseCase.ResponseValues>() {
          @Override
          public void onSuccess(AddProductToWishListUseCase.ResponseValues responseValues) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, responseValues.getData().getMessage()));
            productAddedFav.set(R.drawable.fav_icon_selected);
            EcomUtil.printLog("wishList Succ");
          }

          @Override
          public void onError(Throwable e) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, e.getMessage()));
            EcomUtil.printLog("wishList Fail" + e.getMessage());
          }
        };
    handler.execute(mAddProductToWishListUseCase,
        new AddProductToWishListUseCase.RequestValues(productId, mDeviceIpAddress,
            DEFAULT_LAT_LANG,
            DEFAULT_LAT_LANG),
        disposableSingleObserver);
  }

  /**
   * call to remove from  wishList api
   *
   * @param productId product id
   */
  private void callDeleteWishListApi(String productId) {
    progress.set(TRUE);
    DisposableSingleObserver<DeleteWishListProductUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<DeleteWishListProductUseCase.ResponseValues>() {
          @Override
          public void onSuccess(DeleteWishListProductUseCase.ResponseValues responseValues) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, responseValues.getData().getMessage()));
            productAddedFav.set(R.drawable.fav_icon);
            EcomUtil.printLog("wishList Succ");
          }

          @Override
          public void onError(Throwable e) {
            progress.set(FALSE);
            mLiveData.postValue(Pair.create(ERROR_MSG, e.getMessage()));
            EcomUtil.printLog("wishList Fail" + e.getMessage());
          }
        };
    handler.execute(mRemoveWishListUseCase,
        new DeleteWishListProductUseCase.RequestValues(productId, mDeviceIpAddress,
            DEFAULT_LAT_LANG,
            DEFAULT_LAT_LANG),
        disposableSingleObserver);
  }

  /**
   * listens click on share
   */
  public void onClickShare() {
    if (EcomUtil.isNetworkAvailable(ApplicationManager.getInstance())) {
      shareDeepLink();
    } else {
      mLiveData.postValue(
          Pair.create(ERROR_MSG,
              ApplicationManager.getInstance().getString(R.string.internetIssue)));
    }
  }

  /**
   * listens click on share
   */
  public void goToCart() {
    mClick.postValue(GOTO_CART);
  }

  /**
   * pdp response values
   *
   * @param responseValues response value
   */
  @SuppressLint("DefaultLocale")
  void parsePdpResponseValues(PdpUseCase.ResponseValues responseValues) {
    ProductModel productModel = responseValues.getData().getProductData().getData().get(ZERO);
    if (productModel != null) {
      mShareLink = productModel.getShareLink();
      productOutOfStock.set(productModel.isOutOfStock());
      productCartOptions.set(!productModel.isOutOfStock());
      mAvailableCount = productModel.getAvailableQuantity();
      mLiveData.setValue(Pair.create(VIEW_PAGER_IMAGES, productModel.getImages()));
      if (productModel.getProductName() != null) {
        productName.set(productModel.getProductName());
      }
      productAddedFav.set(
          productModel.isFavorite() ? R.drawable.fav_icon_selected : R.drawable.fav_icon);
      FinalPriceListData finalPriceListData = productModel.getFinalPriceList();
      if (finalPriceListData.getFinalPrice() != null) {
        productPrice.set(
            String.format("%s%s", productModel.getCurrencySymbol(),
                String.format("%.2f", Float.parseFloat(finalPriceListData.getFinalPrice()))));
      }
      if (finalPriceListData.getBasePrice() != null) {
        productActualPrice.set(
            String.format("%.2f", Float.parseFloat(finalPriceListData.getBasePrice())));
      }
      if (finalPriceListData.getDiscountPercentage() != null && Integer.parseInt(
          finalPriceListData.getDiscountPercentage()) != ZERO) {
        productOfferPer.set(
            String.format("(%s %s%% %s)",
                ApplicationManager.getInstance().getString(R.string.pdpOnUpto),
                finalPriceListData.getDiscountPercentage(),
                ApplicationManager.getInstance().getString(R.string.pdpOff)));
        offerViews.set(TRUE);
      } else {
        offerViews.set((finalPriceListData.getDiscountPrice() != null
            && !finalPriceListData.getDiscountPrice().isEmpty()
            && !finalPriceListData.getDiscountPrice().equals("" + ZERO)));
        if (finalPriceListData.getDiscountPrice() != null
            && !finalPriceListData.getDiscountPrice().isEmpty()
            && !finalPriceListData.getDiscountPrice().equals("" + ZERO)) {
          productOfferPer.set(
              String.format("(%s %s %s)",
                  ApplicationManager.getInstance().getString(R.string.pdpFlat),
                  finalPriceListData.getDiscountPrice(),
                  ApplicationManager.getInstance().getString(R.string.pdpOff)));
        }
      }
      if (offerViews.get()) {
        mOfferPrice = productActualPrice.get();
      }
      if (productModel.getAllOffers() != null && productModel.getAllOffers().size() > ZERO) {
        mLiveData.postValue(Pair.create(ALL_OFFERS, productModel.getAllOffers()));
      } else {
        availableOffers.set(FALSE);
      }
      brandName.set(productModel.getBrandName());
      mSellerCount = productModel.getSellerCount();
      mSupplierData = productModel.getSupplier();
      sellerName.set(mSupplierData.getSupplierName());
      mSellerSince = mSupplierData.getSellerSince();
      if (mSupplierData.getRating() != null) {
        sellerRating.set((String.format("%.1f", Float.parseFloat(mSupplierData.getRating()))));
      }
      EcomUtil.printLog("visible" + (mSupplierData.getReviewParameter() != null
          && mSupplierData.getReviewParameter().size() > ZERO));
      sellerInForm.set((mSupplierData.getReviewParameter() != null
          && mSupplierData.getReviewParameter().size() > ZERO));
      if (productModel.getHighlight() != null && productModel.getHighlight().size() > ZERO) {
        productHighLights.set(TRUE);
        allDetHighLights.set(TRUE);
        mLiveData.setValue(Pair.create(GET_HILIGHTS, productModel.getHighlight()));
      } else {
        highLightsVisibility.set(FALSE);
      }
      if (productModel.getAttributes() != null && productModel.getAttributes().size() > ZERO) {
        if (productModel.getAttributes().get(ZERO).getInnerAttributes().size() > ZERO) {
          productSpecifications.set(TRUE);
          allDetSpecifications.set(TRUE);
          productSpecificationTitle.set(productModel.getAttributes().get(ZERO).getName());
          mLiveData.setValue(Pair.create(GET_ATTRIBUTES, productModel.getAttributes()));
        }
      }
      mLiveData.setValue(Pair.create(GET_DESCRIPTION, productModel.getDetailDesc()));
      if (productModel.getVariantsData() != null) {
        setVariantsData(productModel.getVariantsData());
        mVarientSize = productModel.getVariantsData().size();
        mLiveData.setValue(Pair.create(GET_VARIENT_DATA, productModel.getVariantsData()));
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(ONE, mUnitId);
        map.put(TWO, mSupplierData.getId());
        map.put(THREE, productModel.getVariantsData());
        map.put(FOUR, productModel.getOffers());
        map.put(FIVE, productModel.getAvailableQuantity());
        map.put(SIX, cartCount.get());
        mVariants.postValue(map);
        mStoreId = mSupplierData.getId();
        mPdpOfferData = productModel.getOffers();
      }
      new Thread(() -> {
        addToCartTxt.set((mCartHandler.isProductExistInCart(mProductId))
            ? ApplicationManager.getInstance().getString(R.string.pdpAddGoCart)
            : ApplicationManager.getInstance().getString(R.string.pdpAddToCart));
        productAddToCart.set((addToCartTxt.get() != null && Objects.requireNonNull(
            addToCartTxt.get()).equals(
            ApplicationManager.getInstance().getString(R.string.pdpAddGoCart))) ? TRUE : FALSE);
        if (mCartHandler.isProductExistInCart(mProductId)) {
          currentQuantity.set((mCartHandler.getQuantity(mProductId) != ZERO) ? String.valueOf(
              mCartHandler.getQuantity(mProductId)) : String.valueOf(ONE));
        } else {
          currentQuantity.set(String.valueOf(ZERO));
        }
        setCartStatus();
        EcomUtil.printLog("getQuantity" + mCartHandler.getQuantity(mProductId) + "currentQuantity"
            + currentQuantity.get() + "exist" + mCartHandler.isProductExistInCart(mProductId)
            + "totalCartItems" + mCartHandler.totalCartItems());
      }).start();
      if (productModel.getSizeChartData() != null) {
        sizeChartVisible.set(productModel.getSizeChartData().size() > ZERO);
      }
      if (offerViews.get() != null) {
        productOnOffer.set(offerViews.get());
      }
    }
    mReviewData = responseValues.getData().getReview();
    if (mReviewData != null) {
      if (mReviewData.getPenCount() != ZERO) {
        totalReviews.set(
            String.format("%s %d %s",
                ApplicationManager.getInstance().getString(R.string.reviewProductAll),
                mReviewData.getPenCount(), ApplicationManager.getInstance().getString(
                    R.string.pdpReviews)));
      } else {
        reviewsCountVisible.set(FALSE);
      }
      totalStarRating.set(String.format("%.1f", mReviewData.getTotalStarRating()));
      avgRating.set(String.format("%.1f", mReviewData.getTotalStarRating()));
      totalRating.set(String.format("%s %s", mReviewData.getTotalNoOfRatings(),
          ApplicationManager.getInstance().getString(
              R.string.pdpRatings)));
      totalRatingAndReviews.set(
          String.format("%s %s %s\n%d %s", mReviewData.getTotalNoOfRatings(),
              ApplicationManager.getInstance().getString(
                  R.string.pdpRatings), ApplicationManager.getInstance().getString(R.string.and),
              mReviewData.getTotalNoOfReviews(),
              ApplicationManager.getInstance().getString(R.string.pdpReviews)));
      if (mReviewData.getImages() != null && mReviewData.getImages().size() > ZERO) {
        mLiveData.setValue(Pair.create(REVIEW_IMAGES, mReviewData.getImages()));
      }
      if (mReviewData.getRatings() != null && mReviewData.getRatings().size() > ZERO) {
        mLiveData.setValue(Pair.create(GET_RATINGS, mReviewData.getRatings()));
      }
      if (mReviewData.getAttributeRating() != null
          && mReviewData.getAttributeRating().size() > ZERO) {
        mLiveData.setValue(Pair.create(GET_ATTRIBUTES_RATING, mReviewData.getAttributeRating()));
      }
      if (mReviewData.getUserReviews() != null && mReviewData.getUserReviews().size() > ZERO) {
        mLiveData.setValue(Pair.create(REVIEW_DATA, mReviewData.getUserReviews()));
      }
    }
  }

  /*actions for click event for seller*/
  public void onSellerInformation() {
    if (mSupplierData.getReviewParameter() != null
        && mSupplierData.getReviewParameter().size() > ZERO) {
      mLiveData.postValue(Pair.create(REVIEW, mSupplierData.getReviewParameter()));
    } else {
      mLiveData.postValue(Pair.create(ERROR_MSG,
          ApplicationManager.getInstance().getString(R.string.somethingWentWrong)));
    }
  }

  /**
   * actions for add to cart click
   */
  public void addItemToCart() {
    new Thread(() -> {
      EcomUtil.printLog("mAvailableCount" + mAvailableCount + "mCartHandler.getQuantity(mProductId)"
          + mCartHandler.getQuantity(mProductId));
      if (mAvailableCount != Integer.parseInt(Objects.requireNonNull(currentQuantity.get()))) {
        if (mCartHandler.isProductExistInCart(mProductId)) {
          int newQuantity = Integer.parseInt(Objects.requireNonNull(currentQuantity.get())) + ONE;
          callUpdateCartApi(UPDATE_CART, newQuantity);
        }
      } else {
        mLiveData.postValue(Pair.create(ERROR_MSG,
            ApplicationManager.getInstance().getString(R.string.pdpAddToCartWarning)));
      }
    }).start();
  }

  /**
   * actions for add to cart click
   */
  public void removeItemFromCart() {
    new Thread(() -> {
      if (Integer.parseInt(Objects.requireNonNull(currentQuantity.get())) != ONE) {
        int newQuantity = Integer.parseInt(Objects.requireNonNull(currentQuantity.get())) - ONE;
        callUpdateCartApi(UPDATE_CART, newQuantity);
      } else {
        callUpdateCartApi(DELETE_CART, ZERO);
      }
    }).start();
  }

  /**
   * actions for add to add to cart click
   */
  public void addToCart() {
    if (!mUserInfoHandler.isUserLoggedIn()) {
      mClick.setValue(OPEN_LOGIN);
    } else {
      if (addToCartTxt.get() != null && addToCartTxt.get().equals(
          ApplicationManager.getInstance().getString(R.string.pdpAddToCart))) {
        if (mVarientSize > ONE) {
          mClick.setValue(PdpUiAction.OPEN_SHEET);
        } else {
          callUpdateCartApi(ADD_CART, ADD_CART);
        }
      } else {
        mClick.postValue(GOTO_CART);
      }
    }
  }

  /**
   * actions for rating and reviews
   */
  public void ratingAndReviews() {
    mClick.postValue(RATING_AND_REVIEWS);
  }

  /**
   * actions for rating and reviews
   */
  public void more() {
    mClick.postValue(MORE);
  }

  /**
   * call to add to wishList api
   *
   * @param parentProductId product id
   */
  private void callGetSellersApi(String productId, String parentProductId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetSellerListUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetSellerListUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetSellerListUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData().getData() != null
                && responseValues.getData().getData().size() > ZERO) {
              mLiveData.setValue(Pair.create(GET_SELLERS,
                  responseValues.getData().getData().get(ZERO).getSupplier()));
              if (responseValues.getData().getData().get(ZERO).getSupplier() != null
                  && responseValues.getData().getData().get(ZERO).getSupplier().size() > ZERO) {
                otherSellers.set(TRUE);
                otherSellersCount.set(String.format("%s(%d)",
                    ApplicationManager.getInstance().getString(R.string.allOtherSellers),
                    responseValues.getData().getData().get(ZERO).getSupplier().size()));
              }
            }
          }

          @Override
          public void onError(Throwable e) {
            mLiveData.postValue(Pair.create(ERROR_MSG, e.getMessage()));
            EcomUtil.printLog("allReviews Fail" + e.getMessage());
          }
        };
    handler.execute(mGetSellerListUseCase,
        new GetSellerListUseCase.RequestValues(productId, parentProductId,
            String.valueOf(RETAILER)),
        disposableSingleObserver);
  }

  /**
   * listens click on reviews
   */
  public void onReviewsClicked() {
    mLiveData.postValue(Pair.create(REVIEW_CLICKED, mReviewData.getPenCount()));
  }

  /**
   * listens when onclick clicked
   */
  public void onAllDetailsClicked() {
    mLiveData.setValue(
        Pair.create(ALL_DETAILS_CLICKED, ApplicationManager.getInstance().getResources().getString(
            R.string.pdpHighlightsTitle)));
  }

  /**
   * listen when out of stock clicked
   */
  public void outOfStockClicked() {
    mLiveData.postValue(
        Pair.create(OUT_OF_STOCK_CLICKED, mUserInfoHandler.isUserLoggedIn() ? TRUE : FALSE));
  }

  /**
   * listen when onclick clicked
   */
  public void onAllDetSpecificationClicked() {
    mLiveData.setValue(
        Pair.create(ALL_DETAILS_CLICKED, productSpecificationTitle.get()));
  }

  /**
   * listen when onclick clicked
   */
  public void onRatingClicked() {
    mLiveData.postValue(Pair.create(RATING_CLICKED, TRUE));
  }

  /**
   * notify when backButton clicked
   */
  public void onBackButtonClicked() {
    mClick.postValue(CROSS_ICON);
  }

  /**
   * notify when addToWishList clicked
   */
  public void onWishListClicked() {
    if (mUserInfoHandler.isUserLoggedIn()) {
      if (productAddedFav != null) {
        if (productAddedFav.get() == R.drawable.fav_icon) {
          callAddWishListApi(mProductId);
        } else {
          callDeleteWishListApi(mProductId);
        }
      }
    } else {
      mClick.setValue(LOGIN);
    }
  }

  /**
   * notify when size chart clicked
   */
  public void onSizeChartClicked() {
    mClick.postValue(SIZE_CHART);
  }

  /**
   * returns live data clicking.
   */
  MutableLiveData<PdpUiAction> onClick() {
    return mClick;
  }

  /**
   * returns the variants array list
   */
  MutableLiveData<HashMap<Integer, Object>> getVariants() {
    return mVariants;
  }

  /**
   * setting the primary image primary color name
   */
  private void setVariantsData(ArrayList<VariantsData> variantsData) {
    if (variantsData != null && variantsData.size() > ZERO) {
      StringBuilder colorQuantity = new StringBuilder();
      colorQuantity.delete(ZERO, colorQuantity.length());
      for (int i = 0; i < variantsData.size(); i++) {
        if (variantsData.get(i) != null && variantsData.get(i).getName() != null
            && variantsData.get(i).getName().equals(
            ApplicationManager.getInstance().getResources().getString(R.string.pdpColors))) {
          if (variantsData.get(i).getSizeData() != null && variantsData.get(i).getSizeData().size()
              > ZERO) {
            for (int j = 0; j < variantsData.get(i).getSizeData().size(); j++) {
              if (variantsData.get(i).getSizeData().get(j).getIsPrimary()) {
                mPrimaryImage = variantsData.get(i).getSizeData().get(j).getImage();
                colorQuantity.append(variantsData.get(i).getSizeData().get(j).getName()).append(
                    ",");
                this.mProductId = variantsData.get(i).getSizeData().get(j).getChildProductId();
                this.mUnitId = variantsData.get(i).getSizeData().get(j).getUnitId();
                EcomUtil.printLog("mProductId" + mProductId);
                break;
              }
            }
          }
        } else {
          for (int j = 0; j < variantsData.get(i).getSizeData().size(); j++) {
            if (variantsData.get(i).getSizeData().get(j).getIsPrimary()) {
              colorQuantity.append(variantsData.get(i).getSizeData().get(j).getName()).append(",");
              break;
            }
          }
        }
      }
      if (!colorQuantity.toString().isEmpty()) {
        mColor = colorQuantity.toString().substring(ZERO, colorQuantity.toString().length() - ONE);
      }
    }
  }

  /**
   * this method will set the cart total cart count which we added.
   */
  void setCartStatus() {
    new Thread(() -> {
      cartCount.set(
          mCartHandler.totalCartItems() > ZERO ? String.valueOf(mCartHandler.totalCartItems())
              : String.valueOf(ZERO));
      cartCountVisibility.set(mCartHandler.totalCartItems() > ZERO ? TRUE : FALSE);
    }).start();
  }

  /**
   * generating the deep with respective params
   */
  private void shareDeepLink() {
    progress.set(TRUE);
//     https://shoppd.page.link/?link=https://shoppd.net/&apn=com.customer
//     .fivecanale&afl=https://shoppd.net/
    String longLink =
        String.format("%s%s%s", BuildConfig.DYNAMIC_LINK, LINK, mShareLink);
    EcomUtil.printLog("exe" + "longLink" + longLink);
    FirebaseDynamicLinks.getInstance().createDynamicLink()
        .setLongLink(Uri.parse(longLink))
        .setAndroidParameters(
            new DynamicLink.AndroidParameters.Builder(BuildConfig.APPLICATION_ID)
                .build())
        .buildShortDynamicLink()
        .addOnCompleteListener(task -> {
          progress.set(FALSE);
          if (task.isSuccessful()) {
            mShortLink.setValue(Objects.requireNonNull(task.getResult()).getShortLink());
          } else {
            EcomUtil.printLog("shareDeepLink getException " + task.getException());
            EcomUtil.printLog("shareDeepLink getResult " + task.getResult());
          }
        });
  }

  /**
   * return the short link to share
   *
   * @return MutableLiveData<Uri>
   */
  MutableLiveData<Uri> getShortLink() {
    return mShortLink;
  }

  /**
   * This method using to get Seller experience
   *
   * @return seller since val
   */
  String getSellerSince() {
    return mSellerSince;
  }

  MutableLiveData<Pair<String, Object>> getLiveData() {
    return mLiveData;
  }
}