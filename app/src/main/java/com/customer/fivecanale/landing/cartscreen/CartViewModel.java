package com.customer.fivecanale.landing.cartscreen;

import static com.customer.fivecanale.landing.cartscreen.CartUiAction.BILLING_ADDRESS;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.CHANGE_ADDRESS;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.CHANGE_PAYMENT;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.CLEAR;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.CONTINUE_SHOPPING;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.CROSS_CLICK;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.DISABLE_BACK;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.EMPTY_CART;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.ENABLE_BACK;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.LOGIN;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.LOGIN_RESULT;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.MANAGE_ADDRESS;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.ON_SUCCESS;
import static com.customer.fivecanale.landing.cartscreen.CartUiAction.PROMO_CODE;
import static com.customer.fivecanale.util.EcomConstants.CASH_PAYMENT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.DEFAULT_LAT_LANG;
import static com.customer.fivecanale.util.EcomConstants.DELIVERY_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ERROR;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.MULTI_STORE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.RETAILER;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.STORE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.ApplyPromoCodesUseCase;
import com.customer.domain.interactor.GetCartProductsUseCase;
import com.customer.domain.interactor.IpAddressToLocationUseCase;
import com.customer.domain.interactor.PlaceOrderUseCase;
import com.customer.domain.interactor.UpdateCartUseCase;
import com.customer.domain.interactor.handler.AddressHandler;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.promocode.ApplyPromoCodeData;
import com.customer.domain.model.promocode.ProductPromoInput;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import javax.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;

/*holds the logic of the cart page*/
public class CartViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> changeVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> billingAddVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> addAddressVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> getCartprogress = new ObservableField<>(FALSE);
  public ObservableField<Boolean> cartAmtGroup = new ObservableField<>(TRUE);
  public ObservableField<Boolean> paymentGroup = new ObservableField<>(FALSE);
  @Inject
  public UserInfoHandler mUserInfoHandler;
  public ObservableField<Boolean> userNameVisibility = new ObservableField<>();
  public ObservableField<String> userName = new ObservableField<>();
  public ObservableField<String> userAddress = new ObservableField<>();
  public ObservableField<String> billingUserName = new ObservableField<>();
  public ObservableField<String> promoCodeError = new ObservableField<>();
  public ObservableField<String> billingUserAddress = new ObservableField<>();
  public String mCartId;
  String addressId;
  String billingAddressId;
  String cardId;
  String cityId;
  String countryId;
  String coupon = "";
  String couponId = "";
  int paymentType;
  boolean payByWallet;
  boolean mConfirmOrder;
  @Inject
  AddressHandler mAddressHandler;
  private double mDiscount = 0.0;
  private UseCaseHandler mHandler;
  private GetCartProductsUseCase mGetCartProductsUseCase;
  private IpAddressToLocationUseCase mIpAddressToLocationUseCase;
  private MutableLiveData<String> mValidateOnErrorLiveData = new MutableLiveData<>();
  private MutableLiveData<CartData> mCartDataMutableLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mLocation = new MutableLiveData<>();
  private MutableLiveData<String> mAddressData = new MutableLiveData<>();
  private MutableLiveData<CartUiAction> mCartUiAction = new MutableLiveData<>();
  private MutableLiveData<ApplyPromoCodeData> mApplyPromoCodeData = new MutableLiveData<>();
  private UpdateCartUseCase mUpdateCartUseCase;
  private PlaceOrderUseCase mPlaceOrderUseCase;
  private ApplyPromoCodesUseCase mApplyPromoCodesUseCase;
  private String mIpAddress = "";

  @Inject
  public CartViewModel(UseCaseHandler handler, GetCartProductsUseCase getCartProductsUseCase,
      IpAddressToLocationUseCase ipAddressToLocationUseCase, UpdateCartUseCase updateCartUseCase,
      PlaceOrderUseCase placeOrderUseCase, ApplyPromoCodesUseCase applyPromoCodesUseCase) {
    this.mHandler = handler;
    this.mGetCartProductsUseCase = getCartProductsUseCase;
    this.mIpAddressToLocationUseCase = ipAddressToLocationUseCase;
    this.mUpdateCartUseCase = updateCartUseCase;
    this.mPlaceOrderUseCase = placeOrderUseCase;
    this.mApplyPromoCodesUseCase = applyPromoCodesUseCase;
    Thread thread = new Thread() {
      @Override
      public void run() {
        if (EcomUtil.isNetworkAvailable(ApplicationManager.getInstance())) {
          mIpAddress = EcomUtil.getIpAddress();
        } else {
          mCartUiAction.postValue(CROSS_CLICK);
        }
      }
    };
    thread.start();
    promoCodeError.set(
        ApplicationManager.getInstance().getResources().getString(R.string.selectPromoCode));
  }

  /**
   * This method is using to get all product in cart from API
   */
  void getCartProducts() {
    getCartprogress.set(TRUE);
    DisposableSingleObserver<GetCartProductsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetCartProductsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetCartProductsUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("GetCart Success" + responseValues.getData().getSubTotal());
            getCartprogress.set(FALSE);
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              mCartId = responseValues.getData().getId();
              if (responseValues.getData().getTotalDiscount() != null) {
                mDiscount = Double.parseDouble(responseValues.getData().getTotalDiscount());
              }
            }
            mCartDataMutableLiveData.setValue(responseValues.getData());
            mLocation.setValue(TRUE);
          }

          @Override
          public void onError(Throwable e) {
            mCartUiAction.postValue(EMPTY_CART);
            EcomUtil.printLog("GetCart Fail" + e.toString());
            mLocation.setValue(TRUE);
            getCartprogress.set(FALSE);
            progressVisible.set(FALSE);
          }
        };
    mHandler.execute(
        mGetCartProductsUseCase,
        new GetCartProductsUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * This method is used to call the apply promo  from API
   */
  void applyPromoCodeApi(String currencySymbol, float totalPurchaseValue,
      float deliveryFee, String currencyName,
      ArrayList<ProductPromoInput> productPromoInputArrayList) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ApplyPromoCodesUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ApplyPromoCodesUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ApplyPromoCodesUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              mApplyPromoCodeData.postValue(responseValues.getData());
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            try {
              JSONObject jsonObject = new JSONObject(e.getMessage());
              if (jsonObject.has(ERROR)) {
                coupon = "";
                promoCodeError.set(jsonObject.getString(ERROR));
              }
            } catch (JSONException ex) {
              ex.printStackTrace();
            }
          }
        };
    mHandler.execute(
        mApplyPromoCodesUseCase,
        new ApplyPromoCodesUseCase.RequestValues(CASH_PAYMENT_TYPE, coupon, couponId,
            currencySymbol,
            totalPurchaseValue, mCartId, deliveryFee, currencyName, productPromoInputArrayList),
        disposableSingleObserver);
  }

  /** This method is using for subscribing to User data */
  void onUserDataUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getUserDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userData -> {
              if (userData.getName() == null) {
                userName.set("");
              }
              addAddressVisible.set(userData.getName() == null ? TRUE : FALSE);
              changeVisible.set(userData.getName() == null ? FALSE : TRUE);
              EcomUtil.printLog("userName" + userData.getName());
            }));
  }

  /** This method is using for subscribing to User cart data */
  void onCartUpdate() {
    EcomUtil.printLog("onCartUpdate");
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getCartDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cartData -> {
              EcomUtil.printLog("onCartUpdate" + cartData.getAction());
              if (cartData.getAction() == ZERO) {
                mCartUiAction.postValue(EMPTY_CART);
              } else if (cartData.getAction() != SIX) {
                getCartProducts();
              }
            }));
  }

  /**
   * This method is using to get all product in cart from API
   */
  void getAddress(String ipAddress) {
    EcomUtil.printLog("getAddress " + ipAddress);
    DisposableSingleObserver<IpAddressToLocationUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<IpAddressToLocationUseCase.ResponseValues>() {
          @Override
          public void onSuccess(IpAddressToLocationUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("getAddress Success");
            mAddressData.postValue(String.format("%s %s", responseValues.getData().getPostal(),
                responseValues.getData().getRegion()));
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("getAddress Fail" + e.getMessage());
          }
        };
    mHandler.execute(
        mIpAddressToLocationUseCase,
        new IpAddressToLocationUseCase.RequestValues(ipAddress),
        disposableSingleObserver);
  }

  /**
   * listens add address clicked.
   */
  public void addAddress() {
    mCartUiAction.postValue(CHANGE_ADDRESS);
  }

  /**
   * listens add address clicked.
   */
  public void billingAddress() {
    mCartUiAction.postValue(BILLING_ADDRESS);
  }

  /**
   * listens add address clicked.
   */
  public void changePaymentMethod() {
    mCartUiAction.postValue(CHANGE_PAYMENT);
  }

  /**
   * listens clear promo code clicked.
   */
  public void clearPromoCode() {
    mCartUiAction.postValue(CLEAR);
  }

  /**
   * listens clear promo code clicked.
   */
  public void promoCode() {
    mCartUiAction.postValue(PROMO_CODE);
  }

  /**
   * listens change address clicked.
   */
  public void changeAddress() {
    mCartUiAction.postValue(!mUserInfoHandler.isUserLoggedIn() ? LOGIN_RESULT : CHANGE_ADDRESS);
  }

  /**
   * listens change address clicked.
   */
  public void placeOrder() {
    if (mUserInfoHandler.isUserLoggedIn()) {
      if (mConfirmOrder) {
        mCartUiAction.setValue(DISABLE_BACK);
        callPlaceOrderApi(mCartId, addressId, billingAddressId, mDiscount, cardId, paymentType,
            payByWallet);
      } else {
        mCartUiAction.postValue(MANAGE_ADDRESS);
      }
    } else {
      mCartUiAction.postValue(LOGIN);
    }
  }

  /**
   * listens add address clicked.
   */
  public void continueShopping() {
    mCartUiAction.postValue(CONTINUE_SHOPPING);
  }

  /**
   * <p>calls update cart API</p>
   */
  void callUpdateCartApi(int action, int newQuantity, String parentProductId, String productId,
      String unitId, String storeId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<UpdateCartUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UpdateCartUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UpdateCartUseCase.ResponseValues responseValues) {
            EcomUtil.printLog(
                "UpdateCartCart Succ" + responseValues.getData().getMessage() + "newQuantity"
                    + newQuantity);
            //  getCartProducts();
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mValidateOnErrorLiveData.setValue(e.getMessage());
            EcomUtil.printLog("UpdateCartCart Fail" + e.getMessage());
          }
        };
    mHandler.execute(mUpdateCartUseCase,
        new UpdateCartUseCase.RequestValues(RETAILER, parentProductId, productId, unitId,
            storeId, MULTI_STORE,
            "", "", "", newQuantity, ONE, action, MULTI_STORE, mIpAddress, DEFAULT_LAT_LANG,
            DEFAULT_LAT_LANG),
        disposableSingleObserver);
  }

  /**
   * <p>calls place order API</p>
   */
  private void callPlaceOrderApi(String cartId, String addressId, String billingAddressId,
      double discount, String cardId,
      int paymentType, boolean payByWallet) {
    EcomUtil.printLog("exe" + "addressId  " + addressId + "billingAddressId  " + billingAddressId);
    progressVisible.set(TRUE);
    DisposableSingleObserver<PlaceOrderUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<PlaceOrderUseCase.ResponseValues>() {
          @Override
          public void onSuccess(PlaceOrderUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mCartUiAction.postValue(ON_SUCCESS);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mCartUiAction.setValue(ENABLE_BACK);
            mValidateOnErrorLiveData.setValue(e.getMessage());
            EcomUtil.printLog("UpdateCartCart Fail" + e.getMessage());
          }
        };
    mHandler.execute(mPlaceOrderUseCase,
        new PlaceOrderUseCase.RequestValues(cartId, addressId, billingAddressId, paymentType,
            cardId, payByWallet,
            coupon, couponId,discount, DEFAULT_LAT_LANG, DEFAULT_LAT_LANG, mIpAddress, "", STORE_TYPE,
            DELIVERY_TYPE),
        disposableSingleObserver);
  }

  /**
   * This method is using to get the cart Mutable mData
   *
   * @return cart mutable mData
   */
  MutableLiveData<CartData> getCartDataMutableLiveData() {
    return mCartDataMutableLiveData;
  }

  /**
   * This method is using to get the cart Mutable mData
   *
   * @return cart mutable mData
   */
  MutableLiveData<ApplyPromoCodeData> getPromoDataMutableLiveData() {
    return mApplyPromoCodeData;
  }

  /**
   * This method is using to get the location data
   */
  MutableLiveData<Boolean> getLocationLiveData() {
    return mLocation;
  }

  MutableLiveData<String> getAddressData() {
    return mAddressData;
  }

  MutableLiveData<CartUiAction> uiAction() {
    return mCartUiAction;
  }

  /*
   * notify when onError comes
   */
  public MutableLiveData<String> onError() {
    return mValidateOnErrorLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mCartUiAction.postValue(CartUiAction.CROSS_CLICK);
  }
}