package com.customer.fivecanale.pdp.attributebottomsheet;

import static com.customer.fivecanale.pdp.attributebottomsheet.PdpBottomSheetUiAction.CROSS_ICON;
import static com.customer.fivecanale.pdp.attributebottomsheet.PdpBottomSheetUiAction.GOTO_CART;
import static com.customer.fivecanale.pdp.attributebottomsheet.PdpBottomSheetUiAction.GOTO_CART_STATUS;
import static com.customer.fivecanale.pdp.attributebottomsheet.PdpBottomSheetUiAction.NOTIFY_ME;
import static com.customer.fivecanale.pdp.attributebottomsheet.PdpBottomSheetUiAction.QUANTITY;
import static com.customer.fivecanale.util.EcomConstants.ADD_CART;
import static com.customer.fivecanale.util.EcomConstants.DEFAULT_LAT_LANG;
import static com.customer.fivecanale.util.EcomConstants.DELETE_CART;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.MULTI_STORE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.RETAILER;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_CART;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.AddProductToCartUseCase;
import com.customer.domain.interactor.NotifyProductAvailabilityUseCase;
import com.customer.domain.interactor.PdpUseCase;
import com.customer.domain.interactor.UpdateCartUseCase;
import com.customer.domain.interactor.handler.CartHandler;
import com.customer.domain.model.common.FinalPriceListData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.model.pdp.ProductModel;
import com.customer.domain.model.pdp.VariantsData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;

/**
 * view mData class for the pdp bottom sheet.
 */
public class AttributesBottomSheetViewModel extends ViewModel {
  public ObservableField<String> productPrice = new ObservableField<>();
  public ObservableField<Boolean> productAddToCart = new ObservableField<>(FALSE);
  public ObservableField<String> addToCartTxt = new ObservableField<>();
  public ObservableField<String> productActualPrice = new ObservableField<>();
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<String> productOfferPer = new ObservableField<>();
  public ObservableField<Boolean> offerViews = new ObservableField<>(FALSE);
  public ObservableField<String> productName = new ObservableField<>();
  public ObservableField<String> imageUrl = new ObservableField<>();
  public ObservableField<String> currentQuantity = new ObservableField<>();
  public ObservableField<Boolean> productCartOptions = new ObservableField<>(TRUE);
  public ObservableField<Boolean> productOutOfStock = new ObservableField<>(FALSE);
  @Inject
  CartHandler mCartHandler;
  private UseCaseHandler mHandler;
  private MutableLiveData<PdpBottomSheetUiAction> mUiAction = new MutableLiveData<>();
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private MutableLiveData<ArrayList<VariantsData>> mVariantsData =
      new MutableLiveData<>();
  private MutableLiveData<Boolean> mCartCountData = new MutableLiveData<>();
  private MutableLiveData<PdpUseCase.ResponseValues> mPdpResponseValues =
      new MutableLiveData<>();
  private PdpUseCase mPdpUseCase;
  private AddProductToCartUseCase mAddProductToCartUseCase;
  private NotifyProductAvailabilityUseCase mNotifyProductAvailabilityUseCase;
  private UpdateCartUseCase mUpdateCartUseCase;
  private String mProductId = "", mParentProductId = "";
  private String mUnitId, mStoreId;
  private String mDeviceId = "";
  private String mDeviceIpAddress = "";
  private PdpOfferData mOffersListData;
  private int mAvailableCount;
  private boolean mIsOutOfStock;
  private int mCartCount;

  /**
   * constructor for this mData
   *
   * @param pdpUseCase pdp use case
   * @param handler    nodeApiHandler for this attribute bottom sheet.
   */
  @Inject
  public AttributesBottomSheetViewModel(AddProductToCartUseCase addProductToCartUseCase,
      UpdateCartUseCase updateCartUseCase,
      NotifyProductAvailabilityUseCase notifyProductAvailabilityUseCase,
      PdpUseCase pdpUseCase, UseCaseHandler handler) {
    this.mPdpUseCase = pdpUseCase;
    this.mAddProductToCartUseCase = addProductToCartUseCase;
    this.mNotifyProductAvailabilityUseCase = notifyProductAvailabilityUseCase;
    this.mUpdateCartUseCase = updateCartUseCase;
    this.mHandler = handler;
  }

  /**
   * for getting device details
   *
   * @param deviceId        [Id of device]
   * @param deviceIpAddress [IP of device]
   */
  void getDeviceDetails(String deviceId, String deviceIpAddress) {
    this.mDeviceId = deviceId;
    this.mDeviceIpAddress = deviceIpAddress;
  }

  /**
   * for getting the input data
   *
   * @param storeId store id
   * @param unitId  unit id
   */
  void getInputData(String storeId, String unitId, String productId, String parentProductId,
      int availableCount, boolean isOutOfStock, int cartCount,
      PdpOfferData offersListData) {
    this.mStoreId = storeId;
    this.mUnitId = unitId;
    this.mProductId = productId;
    this.mParentProductId = parentProductId;
    this.mOffersListData = offersListData;
    this.mAvailableCount = availableCount;
    this.mIsOutOfStock = isOutOfStock;
    this.mCartCount = cartCount;
    showCartStatus();
  }

  /**
   * call the product details api
   *
   * @param productId       product id
   * @param parentProductId parent product id
   */
  void callProductDetailsApi(String productId, String parentProductId) {
    progressVisible.set(TRUE);
    this.mProductId = productId;
    this.mParentProductId = parentProductId;
    DisposableSingleObserver<PdpUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<PdpUseCase.ResponseValues>() {
          @Override
          public void onSuccess(PdpUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("Pdp succ");
            progressVisible.set(FALSE);
            ProductModel productModel = responseValues.getData().getProductData().getData().get(
                ZERO);
            if (productModel != null) {
              mUnitId = productModel.getUnitId();
              mStoreId = productModel.getSupplier().getId();
              productOutOfStock.set(productModel.isOutOfStock());
              productCartOptions.set(!productModel.isOutOfStock());
              if (productModel.getProductName() != null) {
                productName.set(productModel.getProductName());
              }
              FinalPriceListData finalPriceListData = productModel.getFinalPriceList();
              if (finalPriceListData.getFinalPrice() != null) {
                productPrice.set(
                    String.format("%s %s", productModel.getCurrencySymbol(),
                        finalPriceListData.getFinalPrice()));
              }
              if (finalPriceListData.getBasePrice() != null) {
                productActualPrice.set(String.format("%s %s", productModel.getCurrencySymbol(),
                    finalPriceListData.getBasePrice()));
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
              if (productModel.getVariantsData() != null
                  && productModel.getVariantsData().size() > ZERO) {
                mVariantsData.setValue(productModel.getVariantsData());
              }
              showCartStatus();
            }
            mPdpResponseValues.setValue(responseValues);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("Pdp Fail" + e.getMessage());
          }
        };
    mHandler.execute(mPdpUseCase,
        new PdpUseCase.RequestValues(productId, parentProductId, 0.0, 0.0),
        disposableSingleObserver);
  }

  /**
   * actions for add to add to cart click
   */
  public void addToCart() {
    if (addToCartTxt.get() != null && addToCartTxt.get().equals(
        ApplicationManager.getInstance().getString(R.string.pdpAddToCart))) {
      callUpdateCartApi(ADD_CART, ADD_CART);
    } else {
      mUiAction.postValue(GOTO_CART);
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
        mValidateOnErrorLiveData.postValue(
            ApplicationManager.getInstance().getString(R.string.pdpAddToCartWarning));
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
   * <p>calls update cart API</p>
   */
  private void callUpdateCartApi(int action, int newQuantity) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<UpdateCartUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UpdateCartUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UpdateCartUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("UpdateCartCart Succ" + responseValues.getData().getMessage());
            progressVisible.set(FALSE);
            if (action == ADD_CART) {
              mCartCountData.setValue(TRUE);
            }
            addToCartTxt.set(
                (action == ADD_CART || action == UPDATE_CART)
                    ? ApplicationManager.getInstance().getString(R.string.pdpAddGoCart)
                    : ApplicationManager.getInstance().getString(R.string.pdpAddToCart));
            productAddToCart.set((action == ADD_CART || action == UPDATE_CART) ? TRUE : FALSE);
            if (action == ADD_CART || action == UPDATE_CART) {
              currentQuantity.set(String.valueOf(newQuantity));
            } else {
              mUiAction.setValue(GOTO_CART_STATUS);
              mCartCountData.setValue(TRUE);
              productCartOptions.set(TRUE);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("UpdateCartCart Fail" + e.getMessage());
          }
        };
    mHandler.execute(mUpdateCartUseCase,
        new UpdateCartUseCase.RequestValues(RETAILER, mParentProductId, mProductId, mUnitId,
            mStoreId, MULTI_STORE,
            "", "0", "", newQuantity, ONE, action, MULTI_STORE, mDeviceIpAddress, DEFAULT_LAT_LANG,
            DEFAULT_LAT_LANG),
        disposableSingleObserver);
  }

  /**
   * call the notify  product  api
   */
  void callNotifyProductApi(String email, String parentProductId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<NotifyProductAvailabilityUseCase.ResponseValues>
        disposableSingleObserver =
        new DisposableSingleObserver<NotifyProductAvailabilityUseCase.ResponseValues>() {
          @Override
          public void onSuccess(NotifyProductAvailabilityUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Notify Succ");
            mValidateOnErrorLiveData.postValue(
                responseValues.getData().getMessage());
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.postValue(e.getMessage());
            EcomUtil.printLog("Notify Fail" + e.getMessage());
          }
        };
    mHandler.execute(mNotifyProductAvailabilityUseCase,
        new NotifyProductAvailabilityUseCase.RequestValues(mProductId, email, parentProductId),
        disposableSingleObserver);
  }

  /**
   * this method will show the cart button status for this bottom sheet
   */
  private void showCartStatus() {
    if (mIsOutOfStock) {
      productOutOfStock.set(mIsOutOfStock);
      productCartOptions.set(!mIsOutOfStock);
      return;
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
      EcomUtil.printLog("getQuantity" + mCartHandler.getQuantity(mProductId) + "currentQuantity"
          + currentQuantity.get() + "exist" + mCartHandler.isProductExistInCart(mProductId));
    }).start();
  }

  /**
   * notify when cross icon clicked.
   */
  public void onCrossClicked() {
    mUiAction.postValue(CROSS_ICON);
  }

  /**
   * notify when quantity icon clicked.
   */
  public void onQuantityClicked() {
    mUiAction.postValue(QUANTITY);
  }

  /**
   * listen when out of stock clicked
   */
  public void outOfStockClicked() {
    mUiAction.postValue(NOTIFY_ME);
  }

  /*
   * notify when clicking action happened
   */
  MutableLiveData<PdpBottomSheetUiAction> onClick() {
    return mUiAction;
  }

  /*
   * notify activity when variant mData came
   */
  MutableLiveData<ArrayList<VariantsData>> onGetVariants() {
    return mVariantsData;
  }

  /*
   * notify activity when variant mData came
   */
  MutableLiveData<PdpUseCase.ResponseValues> onGetResponseValues() {
    return mPdpResponseValues;
  }

  /*
   * notify when onError comes
   */
  public MutableLiveData<String> onError() {
    return mValidateOnErrorLiveData;
  }

  /*
   * notify when cart count comes
   */
  MutableLiveData<Boolean> onCartCount() {
    return mCartCountData;
  }
}