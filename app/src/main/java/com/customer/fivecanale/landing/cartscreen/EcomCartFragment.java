package com.customer.fivecanale.landing.cartscreen;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_LIST_DATA;
import static com.customer.fivecanale.util.EcomConstants.AMOUNT;
import static com.customer.fivecanale.util.EcomConstants.APPLY_PROMO;
import static com.customer.fivecanale.util.EcomConstants.ATTRIBUTES_DATA;
import static com.customer.fivecanale.util.EcomConstants.BILLING_ADDRESS_REQUEST;
import static com.customer.fivecanale.util.EcomConstants.CARD;
import static com.customer.fivecanale.util.EcomConstants.CARD_ID;
import static com.customer.fivecanale.util.EcomConstants.CARD_NUMBER;
import static com.customer.fivecanale.util.EcomConstants.CART_AMT;
import static com.customer.fivecanale.util.EcomConstants.CART_ID;
import static com.customer.fivecanale.util.EcomConstants.CART_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.CHANGE_ADDRESS_REQUEST;
import static com.customer.fivecanale.util.EcomConstants.CITY_ID;
import static com.customer.fivecanale.util.EcomConstants.COMING_FROM;
import static com.customer.fivecanale.util.EcomConstants.CONFIRM_ORDER;
import static com.customer.fivecanale.util.EcomConstants.COUNTRY_ID;
import static com.customer.fivecanale.util.EcomConstants.CURRENCY;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.IS_FROM_CART;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_SHOW_MY_ORDERS;
import static com.customer.fivecanale.util.EcomConstants.LOGIN;
import static com.customer.fivecanale.util.EcomConstants.LOGIN_RC;
import static com.customer.fivecanale.util.EcomConstants.MANGE_ADDRESS_REQUEST;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT_BY_WALLET;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT_METHOD_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.PAYMENT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PROMO_CODE;
import static com.customer.fivecanale.util.EcomConstants.PROMO_CODE_ID;
import static com.customer.fivecanale.util.EcomConstants.REQUEST_CODE_PERMISSION_MULTIPLE;
import static com.customer.fivecanale.util.EcomConstants.SPECIFICATIONS;
import static com.customer.fivecanale.util.EcomConstants.STORE_ID;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.WALLET_AMT;
import static com.customer.fivecanale.util.EcomConstants.WALLET_PLUS_CARD;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.PARENT_PRODUCT_ID;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.AddressHandler;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.getcart.CartAccoutingData;
import com.customer.domain.model.getcart.CartAttributesData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.getcart.CartProductItemData;
import com.customer.domain.model.getcart.CartTaxData;
import com.customer.domain.model.promocode.ApplyPromoCodeData;
import com.customer.domain.model.promocode.ProductPromoInput;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.addresslist.SavedAddressListActivity;
import com.customer.fivecanale.alldetails.AllDetailsActivity;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.cart.EcomCartActivity;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.payment.PaymentMethodActivity;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.pdp.attributebottomsheet.PiecesPopUpAdapter;
import com.customer.fivecanale.pdp.attributebottomsheet.QuantityItemClick;
import com.customer.fivecanale.promocode.PromoCodesActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.LocationManagerUtil;
import com.databinding.FragmentCartScreenBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerFragment;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@ActivityScoped
public class EcomCartFragment extends DaggerFragment implements QuantityClick, QuantityItemClick,
    SelectItem {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  AddressHandler mAddressHandler;
  @Inject
  UserInfoHandler mUserInfoHandler;
  private CartViewModel mViewModel;
  private FragmentCartScreenBinding mBinding;
  private ListPopupWindow mPopupWindow;
  private ArrayList<CartProductItemData> mCartProductItemData = new ArrayList<>();
  private CartAdapter mCartAdapter;
  private int mPosition;
  private boolean mConfirmOrder;
  private CartData mCartData;
  private ApplyPromoCodeData mApplyPromoCodeData;
  private int mPaymentType;

  @Inject
  public EcomCartFragment() {
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_screen, container, false);
    initialize();
    subscribeLocationLiveData();
    subscribeAddressLiveData();
    subscribeUiAction();
    subscribeOnPromoData();
    subscribeOnError();
    return mBinding.getRoot();
  }

  /**
   * Does Initialization of basic activity resources
   */
  private void initialize() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CartViewModel.class);
    mBinding.setViewModel(mViewModel);//used to connect mBinding to viewModel
    if (getActivity() != null) {
      mBinding.includeCartHeader.ivBackBtn.setVisibility(
          getActivity() instanceof HomeActivity ? View.GONE : View.VISIBLE);
    }
    if (getArguments() != null) {
      mConfirmOrder = getArguments().getBoolean(CONFIRM_ORDER, FALSE);
      mViewModel.mConfirmOrder = mConfirmOrder;
      mViewModel.cardId = getArguments().getString(CARD_ID);
      mViewModel.paymentType = getArguments().getInt(PAYMENT_TYPE);
      mViewModel.payByWallet = getArguments().getBoolean(PAYMENT_BY_WALLET);
      float totalAmt = getArguments().getFloat(AMOUNT);
      setPaymentMethod(totalAmt);
      mBinding.includeCartHeader.tvTitle.setText(
          mConfirmOrder ? getResources().getString(R.string.confirmOrder)
              : getResources().getString(R.string.myCart));
      mViewModel.cartAmtGroup.set(!mConfirmOrder);
      mViewModel.paymentGroup.set(mConfirmOrder);
      mBinding.btnPlaceOrder.setText(mConfirmOrder
          ? getResources().getString(R.string.confirmAndPlaceOrder)
          : getResources().getString(R.string.cartPlaceOrder));
      if (mConfirmOrder) {
        mBinding.includeCartHeader.ivBackBtn.setImageResource((R.drawable.all_back));
        mBinding.tvPayMentMethod.setVisibility(View.VISIBLE);
        mBinding.tvPmChange.setVisibility(View.VISIBLE);
        mBinding.viewCart.setVisibility(View.VISIBLE);
        mBinding.vgApplyPromoCode.setVisibility(View.VISIBLE);
        if (mUserInfoHandler.isUserLoggedIn()) {
          mBinding.vgBillingAddress.setVisibility(View.VISIBLE);
          mViewModel.billingAddVisible.set(TRUE);
        }
      }
    }
    mViewModel.changeVisible.set(mUserInfoHandler.isUserLoggedIn() ? TRUE : FALSE);
    mViewModel.addAddressVisible.set(mUserInfoHandler.isUserLoggedIn() ? FALSE : TRUE);
    mViewModel.getCartDataMutableLiveData().observe(Objects.requireNonNull(getActivity()),
        cartData -> {
          if (!EcomUtil.isEmptyArray(cartData.getSellers())) {
            mCartData = cartData;
            CartAccoutingData accounting = cartData.getAccounting();
            if (accounting != null) {
              if (accounting.getSubTotal() != null) {
                mBinding.accoutingDetails.tvCartSubTotalAmt.setText(
                    String.format("%s%s", cartData.getCurrencySymbol(),
                        String.format("%.2f", Float.parseFloat(accounting.getFinalUnitPrice()))));
              }
              if (accounting.getDeliveryFee() != null) {
                mBinding.accoutingDetails.tvCartShippingAmt.setText(
                    (accounting.getDeliveryFee().equals(String.valueOf(ZERO)))
                        ? getResources().getString(R.string.free) :
                        String.format("%s%s", cartData.getCurrencySymbol(),
                            accounting.getDeliveryFee()));
              }
              if (accounting.getOfferDiscount() != null) {
                mBinding.accoutingDetails.tvCartTotalSavingsAmt.setText(
                    String.format("%s%s", cartData.getCurrencySymbol(),
                        String.format("%.2f", Float.parseFloat(accounting.getOfferDiscount()))));
              }
              if (accounting.getFinalTotal() != null && !accounting.getFinalTotal().isEmpty()) {
                mBinding.accoutingDetails.tvCartOrderTotalAmt.setText(
                    String.format("%s%s", cartData.getCurrencySymbol(),
                        String.format("%.2f", Float.parseFloat(accounting.getFinalTotal()))));
                mBinding.tvCartAmount.setText(
                    String.format("%s%s", cartData.getCurrencySymbol(),
                        String.format("%.2f", Float.parseFloat(accounting.getFinalTotal()))));
              }
            }
            EcomUtil.printLog("exe" + "mCartData" + mCartData.getTaxData().size());
            if (mCartData.getAccounting().getTax().size() > ZERO) {
              setTaxData(mCartData);
            } else {
              mBinding.accoutingDetails.tvTaxes.setVisibility(View.GONE);
            }
            mCartProductItemData.clear();
            mBinding.clEmptyCart.setVisibility(View.GONE);
            mBinding.clCartFooter.setVisibility(View.VISIBLE);
            if (cartData.getSellers() != null) {
              for (int i = ZERO; i < cartData.getSellers().size(); i++) {
                mCartProductItemData.addAll(cartData.getSellers().get(i).getProducts());
              }
              if (!mConfirmOrder) {
                mBinding.includeCartHeader.tvTitle.setText(
                    String.format("%s( %d )", getResources().getString(R.string.cart),
                        mCartProductItemData.size()));
              }
            }
            mCartAdapter = new CartAdapter(mCartProductItemData, this::onItemClick,
                this::onSelectItem, mConfirmOrder);
            mBinding.rvCartList.setAdapter(mCartAdapter);
            EcomUtil.printLog("exe" + "mCartProductItemData" + mCartProductItemData.size());
            mBinding.clEmptyCart.setVisibility(
                mCartProductItemData.size() > ZERO ? View.GONE : View.VISIBLE);
            mBinding.clCartFooter.setVisibility(
                mCartProductItemData.size() > ZERO ? View.VISIBLE : View.GONE);
          } else {
            mBinding.includeCartHeader.tvTitle.setText(
                String.format("%s( %d )", getResources().getString(R.string.cart), ZERO));
            mBinding.clEmptyCart.setVisibility(View.VISIBLE);
            mBinding.clCartFooter.setVisibility(View.GONE);
          }
        });
    mViewModel.getCartProducts();
    mViewModel.onUserDataUpdate();
    mViewModel.onCartUpdate();
    mBinding.cbDeliveryAddress.setOnCheckedChangeListener(
        (buttonView, isChecked) -> {
          mViewModel.billingAddVisible.set(!isChecked);
          mBinding.tvCartBillingName.setTextColor(
              isChecked ? getResources().getColor(R.color.colorSilverChalice)
                  : getResources().getColor(R.color.colorBlack));
          mBinding.tvCartBillingAddress.setTextColor(
              isChecked ? getResources().getColor(R.color.colorSilverChalice)
                  : getResources().getColor(R.color.colorBlack));
          if (isChecked) {
            mBinding.tvCartBillingName.setText(mBinding.tvCartDeliverName.getText().toString());
            mBinding.tvCartBillingAddress.setText(mBinding.tvCartDelAddress.getText().toString());
          }
        });
  }

  /*subscribes the location live data */
  private void subscribeLocationLiveData() {
    mViewModel.getLocationLiveData().observe(getActivity(), aBoolean -> {
      if (aBoolean) {
        if (mUserInfoHandler.isUserLoggedIn()) {
          Thread t = new Thread() {
            public void run() {
              if (mAddressHandler.isAddressListEmpty()) {
                //permission for location
                if (mCartProductItemData.size() > ZERO) {
                  checkPerMission();
                }
              } else {
                if (mAddressHandler.getDefaultAddress().getName() != null
                    && !mAddressHandler.getDefaultAddress().getName().isEmpty()) {
                  String name = mAddressHandler.getDefaultAddress().getName();
                  Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    mBinding.tvCartDeliverName.setText(name);
                    mBinding.tvCartBillingName.setText(name);
                  });
                } else {
                  Objects.requireNonNull(getActivity()).runOnUiThread(
                      () -> mBinding.tvCartDeliverName.setVisibility(View.GONE));
                  Objects.requireNonNull(getActivity()).runOnUiThread(
                      () -> mBinding.tvCartBillingName.setVisibility(View.GONE));
                }
                String addLine1 = mAddressHandler.getDefaultAddress().getAddLine1();
                String landMark = mAddressHandler.getDefaultAddress().getLandmark();
                String city = mAddressHandler.getDefaultAddress().getCity();
                String pinCOde = mAddressHandler.getDefaultAddress().getPincode();
                String addID = mAddressHandler.getDefaultAddress().getId();
                String addressType = mAddressHandler.getDefaultAddress().getTaggedAs();
                String phoneNum = String.format("+%s %s",
                    mAddressHandler.getDefaultAddress().getMobileNumberCode(),
                    mAddressHandler.getDefaultAddress().getMobileNumber());
                mViewModel.cityId = mAddressHandler.getDefaultAddress().getCityId();
                mViewModel.countryId = mAddressHandler.getDefaultAddress().getCountryId();
                getActivity().runOnUiThread(() -> {
                  mBinding.tvCartDelAddress.setText(
                      String.format("%s%s,%s,%s",
                          addLine1,
                          landMark,
                          city,
                          String.format("%s\n%s:%s", pinCOde,
                              getResources().getString(R.string.phoneNumber), phoneNum)));
                  mViewModel.addressId = addID;
                  mBinding.tvCartDeliverType.setText(String.format("%s", addressType));
                  mBinding.tvCartBillingAddress.setText(
                      String.format("%s%s,%s,%s",
                          addLine1,
                          landMark,
                          city,
                          String.format("%s\n%s:%s", pinCOde,
                              getResources().getString(R.string.phoneNumber), phoneNum)));
                  mViewModel.billingAddressId = addID;
                  mBinding.cbDeliveryAddress.setChecked(TRUE);
                });
              }
            }
          };
          t.start();
        } else {
          mBinding.vgBillingAddress.setVisibility(View.GONE);
          mBinding.tvCartDeliverName.setVisibility(View.GONE);
          if (mCartProductItemData.size() > ZERO) {
            checkPerMission();
          }
        }
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    EcomUtil.printLog("exe" + "onResume");
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    if (!hidden) {
      //do when hidden
      mViewModel.getCartProducts();
    }
  }

  /**
   * subscribe to location data
   */
  private void subscribeAddressLiveData() {
    mViewModel.getAddressData().observe(Objects.requireNonNull(getActivity()),
        location -> mBinding.tvCartDelAddress.setText(location));
  }

  /**
   * subscribe to location data
   */
  private void subscribeUiAction() {
    mViewModel.uiAction().observe(Objects.requireNonNull(getActivity()), cartUiAction -> {
      switch (cartUiAction) {
        case LOGIN_RESULT:
          loginActivity();
          break;
        case LOGIN:
          doLogin();
          break;
        case PAYMENT:
        case CHANGE_PAYMENT:
          paymentActivity();
          break;
        case MANAGE_ADDRESS:
          manageAddress();
          break;
        case CHANGE_ADDRESS:
          changeAddress();
          break;
        case BILLING_ADDRESS:
          addBillingAddress();
          break;
        case CROSS_CLICK:
          if (getActivity() != null) {
            getActivity().finish();
          }
          break;
        case CONTINUE_SHOPPING:
          if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).showHomeFragment();
          } else {
            getActivity().finish();
          }
          break;
        case EMPTY_CART:
          mBinding.includeCartHeader.tvTitle.setText(
              String.format("%s( %d )", getResources().getString(R.string.cart), ZERO));
          mBinding.clEmptyCart.setVisibility(View.VISIBLE);
          mBinding.clCartFooter.setVisibility(View.GONE);
          break;
        case ON_SUCCESS:
          onSuccess();
          break;
        case SUB_TOTAL:
          break;
        case ENABLE_BACK:
          mBinding.includeCartHeader.ivBackBtn.setEnabled(TRUE);
          if (getActivity() instanceof EcomCartActivity) {
            EcomCartActivity ecomCartActivity = (EcomCartActivity) getActivity();
            ecomCartActivity.allowBack(TRUE);
          }
          break;
        case DISABLE_BACK:
          mBinding.includeCartHeader.ivBackBtn.setEnabled(FALSE);
          if (getActivity() instanceof EcomCartActivity) {
            EcomCartActivity ecomCartActivity = (EcomCartActivity) getActivity();
            ecomCartActivity.allowBack(FALSE);
          }
          break;
        case CLEAR:
          mViewModel.coupon = "";
          mViewModel.couponId = "";
          mBinding.tvPromoCode.setText(getResources().getString(R.string.selectPromoCode));
          mBinding.accoutingDetails.vgPromotionsApplied.setVisibility(View.GONE);
          float finalTotalAmt = Float.parseFloat(mCartData.getAccounting().getFinalTotal());
          mBinding.accoutingDetails.tvCartOrderTotalAmt.setText(
              String.format("%s%s", mCartData.getCurrencySymbol(),
                  String.format("%.2f", finalTotalAmt)));
          float yourSavingsAmt = Float.parseFloat(mCartData.getAccounting().getOfferDiscount());
          mBinding.accoutingDetails.tvCartTotalSavingsAmt.setText(
              String.format("%s%s", mCartData.getCurrencySymbol(),
                  String.format("%.2f", yourSavingsAmt)));
          if (mPaymentType == WALLET_PLUS_CARD) {
            float walletAmt = getArguments().getFloat(WALLET_AMT);
            mBinding.tvCardNumberAmt.setText(
                String.format("%s %s", mCartData.getCurrencySymbol(),
                    String.format("%.2f",
                        (Float.parseFloat(mCartData.getAccounting().getFinalTotal())
                            - walletAmt))));
          } else if (mPaymentType == CARD) {
            mBinding.tvCardNumberAmt.setText(
                String.format("%s%s", mCartData.getCurrencySymbol(),
                    String.format("%.2f", finalTotalAmt)));
          }
          break;
        case PROMO_CODE:
          promoCodeAct(mViewModel.countryId, mViewModel.cityId, mViewModel.mCartId, getStoreId());
          break;
        default:
          break;
      }
    });
  }

  /**
   * set the tax data
   *
   * @param cartData cart data object from server.
   */
  private void setTaxData(CartData cartData) {
    LinearLayout linearLayout = mBinding.accoutingDetails.cartAddTaxLl;
    ArrayList<CartTaxData> taxData = cartData.getAccounting().getTax();
    linearLayout.removeAllViews();
    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    View viewInflater;
    if (taxData != null && taxData.size() > ZERO) {
      for (int i = ZERO; i < taxData.size(); i++) {
        viewInflater = layoutInflater.inflate(R.layout.tax_row, null);
        TextView taxTxtTv = viewInflater.findViewById(R.id.taxTxtTv);
        TextView taxValueTv = viewInflater.findViewById(R.id.taxValueTv);
        taxTxtTv.setText(taxData.get(i).getTaxName());
        taxValueTv.setText(
            String.format("%s %s", cartData.getCurrencySymbol(), taxData.get(i).getTotalValue()));
        linearLayout.addView(viewInflater);
      }
    }
  }

  /**
   * check permission camera,read external storage
   */
  private void checkPerMission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ((Objects.requireNonNull(
        getActivity()).checkSelfPermission(
        ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED))) {
      requestPermissions(new String[]{ACCESS_FINE_LOCATION},
          REQUEST_CODE_PERMISSION_MULTIPLE);
    } else {
      //permission granted.
      accessLocation();
    }
  }

  /**
   * getLocation data
   */
  private void accessLocation() {
    mBinding.tvCartDelAddress.setText(LocationManagerUtil.getCurrentLocation(getActivity()));
    Thread t = new Thread() {
      public void run() {
        if (mAddressHandler.getDefaultAddress().getName() != null
            && !mAddressHandler.getDefaultAddress().getName().isEmpty()) {
          new Handler(Looper.getMainLooper()).post(() -> {
            EcomUtil.printLog("address" + mAddressHandler.getDefaultAddress().getName());
            mBinding.tvCartDeliverName.setText(mAddressHandler.getDefaultAddress().getName());
            mBinding.tvCartDeliverType.setText(mAddressHandler.getDefaultAddress().getTaggedAs());
            mBinding.tvCartBillingName.setText(mAddressHandler.getDefaultAddress().getName());
          });
        } else {
          new Handler(Looper.getMainLooper()).post(() -> {
            Objects.requireNonNull(getActivity()).runOnUiThread(
                () -> mBinding.tvCartDeliverName.setVisibility(View.GONE));
            Objects.requireNonNull(getActivity()).runOnUiThread(
                () -> mBinding.tvCartDeliverType.setVisibility(View.GONE));
            Objects.requireNonNull(getActivity()).runOnUiThread(
                () -> mBinding.tvCartBillingName.setVisibility(View.GONE));
          });
        }
        EcomUtil.printLog("address" + "isAddressListEmpty()"
            + mAddressHandler.isAddressListEmpty()
            + mAddressHandler.getDefaultAddress().getName());
        mViewModel.addressId = mAddressHandler.getDefaultAddress().getId();
        mViewModel.billingAddressId = mAddressHandler.getDefaultAddress().getId();
        mViewModel.cityId = mAddressHandler.getDefaultAddress().getCityId();
        mViewModel.countryId = mAddressHandler.getDefaultAddress().getCountryId();
      }
    };
    t.start();
  }

  @Override
  public void onItemClick(AppCompatTextView appCompatTextView, String unitName, int pos,
      int action) {
    mPosition = pos;
    if (appCompatTextView != null && unitName != null) {
      CartProductItemData cartProductItemData = mCartProductItemData.get(mPosition);
      ArrayList<String> unitList = new ArrayList<>();
      for (int i = ONE; i <= cartProductItemData.getAvailableQuantity(); i++) {
        unitList.add(String.format("%d %s", i, unitName));
      }
      PiecesPopUpAdapter piecesPopUpAdapter = new PiecesPopUpAdapter(unitList, this);
      mPopupWindow = new ListPopupWindow(Objects.requireNonNull(getActivity()));
      mPopupWindow.setAdapter(piecesPopUpAdapter);
      mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
      mPopupWindow.setDropDownGravity(Gravity.BOTTOM);
      mPopupWindow.setOverlapAnchor(FALSE);
      mPopupWindow.setAnchorView(appCompatTextView);
      if (!mPopupWindow.isShowing()) {
        mPopupWindow.show();
        mCartProductItemData.get(pos).setEnable(FALSE);
        mCartAdapter.notifyItemChanged(pos);
      }
      mPopupWindow.setOnDismissListener(() -> {
        mCartProductItemData.get(pos).setEnable(TRUE);
        mCartAdapter.notifyItemChanged(pos);
      });
    } else {
      switch (action) {
        case THREE:
          mViewModel.callUpdateCartApi(action, ZERO,
              mCartProductItemData.get(mPosition).getCentralProductId(),
              mCartProductItemData.get(mPosition).getId(),
              mCartProductItemData.get(mPosition).getUnitId(),
              mCartProductItemData.get(mPosition).getStoreId());
          break;
        case FOUR:
          startAllDetailsAct(mCartProductItemData.get(mPosition).getAttributes());
          break;
      }
    }
  }

  @Override
  public void onItemClick(String value) {
    EcomUtil.printLog("exe" + "value" + value);
    if (mPopupWindow.isShowing()) {
      mPopupWindow.dismiss();
      String[] quantity = value.split(" ");
      mCartProductItemData.get(mPosition).getQuantity().setValue(quantity[ZERO]);
      mCartProductItemData.get(mPosition).getQuantity().setUnit(value.substring(TWO));
      mCartAdapter.notifyItemChanged(mPosition);
      mViewModel.callUpdateCartApi(TWO, Integer.parseInt(quantity[ZERO]),
          mCartProductItemData.get(mPosition).getCentralProductId(),
          mCartProductItemData.get(mPosition).getId(),
          mCartProductItemData.get(mPosition).getUnitId(),
          mCartProductItemData.get(mPosition).getStoreId());
    }
  }

  /**
   * start all details activity
   */
  private void startAllDetailsAct(ArrayList<CartAttributesData> cartAttributesData) {
    Intent intent = new Intent(getActivity(), AllDetailsActivity.class);
    intent.putExtra(IS_FROM_CART, TRUE);
    intent.putExtra(SPECIFICATIONS, getResources().getString(R.string.customizations));
    intent.putExtra(ATTRIBUTES_DATA, cartAttributesData);
    startActivity(intent);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
      @NotNull String[] permissions, @NotNull int[] grantResults) {
    switch (requestCode) {
      case REQUEST_CODE_PERMISSION_MULTIPLE: {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ((Objects.requireNonNull(
            getActivity()).checkSelfPermission(
            ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED))) {
          Thread thread = new Thread() {
            @Override
            public void run() {
              if (!mAddressHandler.isAddressListEmpty()) {
                if (mAddressHandler.getDefaultAddress().getName() != null
                    && !mAddressHandler.getDefaultAddress().getName().isEmpty()) {
                  new Handler(Looper.getMainLooper()).post(() -> {
                    mBinding.tvCartDeliverName.setText(
                        mAddressHandler.getDefaultAddress().getName());
                    mBinding.tvCartBillingName.setText(
                        mAddressHandler.getDefaultAddress().getName());
                  });
                } else {
                  new Handler(Looper.getMainLooper()).post(() -> {
                    Objects.requireNonNull(getActivity()).runOnUiThread(
                        () -> mBinding.tvCartDeliverName.setVisibility(View.GONE));
                    Objects.requireNonNull(getActivity()).runOnUiThread(
                        () -> mBinding.tvCartBillingName.setVisibility(View.GONE));
                  });
                }
                mViewModel.addressId = mAddressHandler.getDefaultAddress().getId();
                mViewModel.billingAddressId = mAddressHandler.getDefaultAddress().getId();
                mViewModel.cityId = mAddressHandler.getDefaultAddress().getCityId();
                mViewModel.countryId = mAddressHandler.getDefaultAddress().getCountryId();
              }
              mViewModel.getAddress(EcomUtil.getIpAddress());
            }
          };
          thread.start();
        } else {
          accessLocation();
        }
        break;
      }
    }
  }

  /**
   * start promo code activity
   */
  private void promoCodeAct(String countryId, String cityId, String cartId,
      String storeId) {
    Intent intent = new Intent(getActivity(), PromoCodesActivity.class);
    intent.putExtra(COUNTRY_ID, countryId);
    intent.putExtra(CITY_ID, cityId);
    intent.putExtra(CART_ID, cartId);
    intent.putExtra(STORE_ID, storeId);
    startActivityForResult(intent, APPLY_PROMO);
  }

  /**
   * start manage address activity
   */
  public void manageAddress() {
    Intent intent = new Intent(getActivity(), SavedAddressListActivity.class);
    intent.putExtra(IS_FROM_CART, TRUE);
    startActivityForResult(intent, MANGE_ADDRESS_REQUEST);
  }

  /**
   * start manage address activity
   */
  public void changeAddress() {
    Intent intent = new Intent(getActivity(), SavedAddressListActivity.class);
    intent.putExtra(IS_FROM_CART, TRUE);
    startActivityForResult(intent, CHANGE_ADDRESS_REQUEST);
  }

  /**
   * start manage address activity
   */
  public void addBillingAddress() {
    Intent intent = new Intent(getActivity(), SavedAddressListActivity.class);
    intent.putExtra(IS_FROM_CART, TRUE);
    startActivityForResult(intent, BILLING_ADDRESS_REQUEST);
  }

  /**
   * start manage address activity
   */
  private void loginActivity() {
    startActivityForResult(new Intent(getActivity(), EcomLoginActivity.class),
        LOGIN_RC);
  }

  /**
   * start manage address activity
   */
  private void doLogin() {
    startActivityForResult(new Intent(getActivity(), EcomLoginActivity.class), LOGIN);
  }

  /**
   * start payment activity
   */
  private void paymentActivity() {
    String totalAmtToBePaid =
        String.format("%s,%s", mCartData.getCurrencyCode(),
            mCartData.getAccounting().getFinalTotal());
    Intent intent = new Intent(getActivity(), PaymentMethodActivity.class);
    intent.putExtra(CART_AMT, totalAmtToBePaid);
    intent.putExtra(COMING_FROM, mConfirmOrder ? ORDER_SCREEN : CART_SCREEN);
    if (mConfirmOrder) {
      startActivityForResult(intent, PAYMENT_METHOD_REQ_CODE);
    } else {
      startActivity(intent);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && data != null) {
      switch (requestCode) {
        case MANGE_ADDRESS_REQUEST:
          AddressListItemData addressListItemData = data.getParcelableExtra(ADDRESS_LIST_DATA);
          String phoneNum = String.format("+%s %s",
              addressListItemData.getMobileNumberCode(),
              addressListItemData.getMobileNumber());
          mBinding.tvCartDelAddress.setText(
              String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getLandmark(), addressListItemData.getCity(),
                  String.format("%s\n%s:%s", addressListItemData.getPincode(),
                      getResources().getString(
                          R.string.phoneNumber), phoneNum)));
          mBinding.tvCartDeliverType.setText(
              String.format("%s", addressListItemData.getTaggedAs()));
          mBinding.tvCartDeliverName.setVisibility(View.VISIBLE);
          mViewModel.addressId = addressListItemData.getId();
          mBinding.tvCartDeliverName.setText(addressListItemData.getName());
          mViewModel.changeVisible.set(TRUE);
          mViewModel.addAddressVisible.set(FALSE);
          paymentActivity();
          break;
        case CHANGE_ADDRESS_REQUEST:
          AddressListItemData changeAddressListItemData = data.getParcelableExtra(
              ADDRESS_LIST_DATA);
          String changePhoneNum = String.format("+%s %s",
              changeAddressListItemData.getMobileNumberCode(),
              changeAddressListItemData.getMobileNumber());
          mBinding.tvCartDelAddress.setText(
              String.format("%s%s,%s,%s", changeAddressListItemData.getAddLine1(),
                  changeAddressListItemData.getLandmark(), changeAddressListItemData.getCity(),
                  changeAddressListItemData.getPincode() + "\n" + getResources().getString(
                      R.string.phoneNumber) + ":" + changePhoneNum));
          mBinding.tvCartDeliverType.setText(
              String.format("%s", changeAddressListItemData.getTaggedAs()));
          mBinding.tvCartDeliverName.setVisibility(View.VISIBLE);
          mViewModel.addressId = changeAddressListItemData.getId();
          mBinding.tvCartDeliverName.setText(changeAddressListItemData.getName());
          mViewModel.changeVisible.set(TRUE);
          mViewModel.addAddressVisible.set(FALSE);
          break;
        case BILLING_ADDRESS_REQUEST:
          AddressListItemData billingAddressListItemData = data.getParcelableExtra(
              ADDRESS_LIST_DATA);
          String billingAddPhoneNum = String.format("+%s %s",
              billingAddressListItemData.getMobileNumberCode(),
              billingAddressListItemData.getMobileNumber());
          mBinding.tvCartBillingAddress.setText(
              String.format("%s%s,%s,%s", billingAddressListItemData.getAddLine1(),
                  billingAddressListItemData.getLandmark(), billingAddressListItemData.getCity(),
                  String.format("%s\n%s:%s", billingAddressListItemData.getPincode(),
                      getResources().getString(
                          R.string.phoneNumber), billingAddPhoneNum)));
          mBinding.tvCartBillingName.setVisibility(View.VISIBLE);
          mViewModel.billingAddressId = billingAddressListItemData.getId();
          mViewModel.cityId = billingAddressListItemData.getCityId();
          mViewModel.countryId = billingAddressListItemData.getCountryId();
          mBinding.tvCartBillingName.setText(billingAddressListItemData.getName());
          mViewModel.billingAddVisible.set(TRUE);
          break;
        case LOGIN_RC:
          changeAddress();
          break;
        case LOGIN:
          mViewModel.placeOrder();
          break;
        case APPLY_PROMO:
          String promoCode = data.getStringExtra(PROMO_CODE);
          String promoCodeId = data.getStringExtra(PROMO_CODE_ID);
          mBinding.tvPromoCode.setText(promoCode);
          mViewModel.coupon = promoCode;
          mViewModel.couponId = promoCodeId;
          mViewModel.applyPromoCodeApi(mCartData.getCurrencySymbol(),
              Float.parseFloat(mCartData.getAccounting().getFinalTotal()),
              Float.parseFloat(mCartData.getAccounting().getDeliveryFee()),
              mCartData.getCurrencyCode(), getProductPromoList());
          EcomUtil.printLog("exe" + "promoCode" + promoCode);
          break;
        case PAYMENT_METHOD_REQ_CODE:
          mViewModel.cardId = data.getStringExtra(CARD_ID);
          mViewModel.paymentType = data.getIntExtra(PAYMENT_TYPE, ZERO);
          mViewModel.payByWallet = data.getBooleanExtra(PAYMENT_BY_WALLET, FALSE);
          float totalAmt = data.getFloatExtra(AMOUNT, 0f);
          int paymentType = data.getIntExtra(PAYMENT, ZERO);
          float walletAmt = data.getFloatExtra(WALLET_AMT, 0f);
          String currency = data.getStringExtra(CURRENCY);
          String cardNum = data.getStringExtra(CARD_NUMBER);
          setPaymentMethod(totalAmt, paymentType, walletAmt, currency, cardNum);
          break;
        default:
          break;
      }
    }
  }

  /**
   * subscribe for onError
   */
  private void subscribeOnPromoData() {
    mViewModel.getPromoDataMutableLiveData().observe(getActivity(),
        applyPromoCodeData -> {
          mApplyPromoCodeData = applyPromoCodeData;
          mBinding.accoutingDetails.vgPromotionsApplied.setVisibility(View.VISIBLE);
          mBinding.accoutingDetails.tvCartPromotionsAmt.setText(
              String.format("-%s%s", mCartData.getCurrencySymbol(),
                  String.format("%.2f", mApplyPromoCodeData.getReducedAmt())));
          float totalAmt = Float.parseFloat(mCartData.getAccounting().getFinalTotal());
          float finalTotalAmt = totalAmt - mApplyPromoCodeData.getReducedAmt();
          mBinding.accoutingDetails.tvCartOrderTotalAmt.setText(
              String.format("%s%s", mCartData.getCurrencySymbol(),
                  String.format("%.2f", finalTotalAmt)));
          float yourSavingsAmt = Float.parseFloat(mCartData.getAccounting().getOfferDiscount())
              + mApplyPromoCodeData.getReducedAmt();
          mBinding.accoutingDetails.tvCartTotalSavingsAmt.setText(
              String.format("%s%s", mCartData.getCurrencySymbol(),
                  String.format("%.2f", yourSavingsAmt)));
          if (mPaymentType == WALLET_PLUS_CARD) {
            float walletAmt = getArguments().getFloat(WALLET_AMT);
            mBinding.tvCardNumberAmt.setText(
                String.format("%s %s", mCartData.getCurrencySymbol(),
                    String.format("%.2f",
                        (totalAmt - walletAmt - mApplyPromoCodeData.getReducedAmt()))));
          } else if (mPaymentType == CARD) {
            mBinding.tvCardNumberAmt.setText(
                String.format("%s%s", mCartData.getCurrencySymbol(),
                    String.format("%.2f", finalTotalAmt)));
          }
        });
  }

  /**
   * subscribe for onError
   */
  private void subscribeOnError() {
    mViewModel.onError().observe(getActivity(), this::onError);
  }

  /**
   * it will show snack bar message
   *
   * @param error string mError message
   */
  public void onError(String error) {
    Log.d("exe", "error" + error);
    Snackbar.make(mBinding.clCart, error, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * <p>This method is used to show the success dialog for 2 seconds.</p>
   */
  public void onSuccess() {
    EcomUtil.hideSoftKeyboard(mBinding.clCart);
    mBinding.successCheckOut.sucecssTv.setText(
        getResources().getString(R.string.confirmOrderlSuccess));
    mBinding.successCheckOut.tvDesc.setVisibility(View.GONE);
    mBinding.includeCartHeader.clActionBar.setVisibility(View.GONE);
    mBinding.clCartFooter.setVisibility(View.GONE);
    mBinding.successCheckOut.clSuccess.setVisibility(View.VISIBLE);
    ((Animatable) mBinding.successCheckOut.successTick.getDrawable()).start();
    CountDownTimer countDownTimer = new CountDownTimer(THOUSAND * TWO, THOUSAND) {
      @Override
      public void onTick(long millisUntilFinished) {
      }

      @Override
      public void onFinish() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra(IS_TO_SHOW_MY_ORDERS, TRUE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
      }
    };
    countDownTimer.start();
  }

  /**
   * This method used set the payment method
   */
  private void setPaymentMethod(float totalAmt) {
    mPaymentType = getArguments().getInt(PAYMENT);
    float walletAmt = getArguments().getFloat(WALLET_AMT);
    String currency = getArguments().getString(CURRENCY);
    String cardNum = getArguments().getString(CARD_NUMBER);
    switch (mPaymentType) {
      case ONE:
        mBinding.vgWallet.setVisibility(View.VISIBLE);
        mBinding.vgCardMethod.setVisibility(View.VISIBLE);
        mBinding.tvCashOnDel.setVisibility(View.GONE);
        mBinding.tvCardNumber.setText(
            String.format("%s(%s %s)", getResources().getString(R.string.card),
                getResources().getString(R.string.ending), cardNum));
        mBinding.tvCardNumberAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", (totalAmt - walletAmt))));
        mBinding.tvWalletAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", walletAmt)));
        break;
      case TWO:
        mBinding.vgWallet.setVisibility(View.VISIBLE);
        mBinding.tvCashOnDel.setVisibility(View.VISIBLE);
        mBinding.vgCardMethod.setVisibility(View.GONE);
        mBinding.tvWalletAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", walletAmt)));
        break;
      case THREE:
        mBinding.vgCardMethod.setVisibility(View.GONE);
        mBinding.vgWallet.setVisibility(View.VISIBLE);
        mBinding.tvCashOnDel.setVisibility(View.GONE);
        mBinding.tvWalletAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", walletAmt)));
        break;
      case FOUR:
        mBinding.vgCardMethod.setVisibility(View.VISIBLE);
        mBinding.vgWallet.setVisibility(View.GONE);
        mBinding.tvCashOnDel.setVisibility(View.GONE);
        mBinding.tvCardNumber.setText(
            String.format("%s(%s %s)", getResources().getString(R.string.card),
                getResources().getString(R.string.ending), cardNum));
        mBinding.tvCardNumberAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", totalAmt)));
        break;
      case FIVE:
        mBinding.tvCashOnDel.setVisibility(View.VISIBLE);
        mBinding.vgCardMethod.setVisibility(View.GONE);
        mBinding.vgWallet.setVisibility(View.GONE);
        break;
    }
  }

  /**
   * This method used set the payment method
   */
  private void setPaymentMethod(float totalAmt, int paymentType, float walletAmt, String currency,
      String cardNum) {
    switch (paymentType) {
      case ONE:
        mBinding.vgWallet.setVisibility(View.VISIBLE);
        mBinding.vgCardMethod.setVisibility(View.VISIBLE);
        mBinding.tvCashOnDel.setVisibility(View.GONE);
        mBinding.tvCardNumber.setText(
            String.format("%s(%s %s)", getResources().getString(R.string.card),
                getResources().getString(R.string.ending), cardNum));
        mBinding.tvCardNumberAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", (totalAmt - walletAmt))));
        mBinding.tvWalletAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", walletAmt)));
        break;
      case TWO:
        mBinding.vgWallet.setVisibility(View.VISIBLE);
        mBinding.tvCashOnDel.setVisibility(View.VISIBLE);
        mBinding.vgCardMethod.setVisibility(View.GONE);
        mBinding.tvWalletAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", walletAmt)));
        break;
      case THREE:
        mBinding.vgCardMethod.setVisibility(View.GONE);
        mBinding.vgWallet.setVisibility(View.VISIBLE);
        mBinding.tvCashOnDel.setVisibility(View.GONE);
        mBinding.tvWalletAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", walletAmt)));
        break;
      case FOUR:
        mBinding.vgCardMethod.setVisibility(View.VISIBLE);
        mBinding.vgWallet.setVisibility(View.GONE);
        mBinding.tvCashOnDel.setVisibility(View.GONE);
        mBinding.tvCardNumber.setText(
            String.format("%s(%s %s)", getResources().getString(R.string.card),
                getResources().getString(R.string.ending), cardNum));
        mBinding.tvCardNumberAmt.setText(
            String.format("%s %s", currency, String.format("%.2f", totalAmt)));
        break;
      case FIVE:
        mBinding.tvCashOnDel.setVisibility(View.VISIBLE);
        mBinding.vgCardMethod.setVisibility(View.GONE);
        mBinding.vgWallet.setVisibility(View.GONE);
        break;
    }
  }

  @Override
  public void onSelectItem(int position) {
    Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
    intent.putExtra(PARENT_PRODUCT_ID, mCartProductItemData.get(position).getCentralProductId());
    intent.putExtra(PRODUCT_ID, mCartProductItemData.get(position).getId());
    startActivity(intent);
  }

  /**
   * this method used to get the product details for promo.
   *
   * @return return the product list.
   */
  private ArrayList<ProductPromoInput> getProductPromoList() {
    ArrayList<ProductPromoInput> productPromoInputs = new ArrayList<>();
    if (mCartProductItemData != null && mCartProductItemData.size() > ZERO) {
      for (CartProductItemData details : mCartProductItemData) {
        ProductPromoInput productPromoInput = new ProductPromoInput();
        productPromoInput.setProduct_id(details.getId());
        productPromoInput.setName(details.getName());
        productPromoInput.setBrand_name(details.getBrandName());
        productPromoInput.setPrice(Float.parseFloat(details.getAccounting().getFinalTotal()));
        productPromoInput.setDelivery_fee(
            Float.parseFloat(details.getDeliveryDetails().getDeliveryFee()));
        productPromoInput.setCentralproduct_id(details.getCentralProductId());
        productPromoInput.setUnitPrice(Float.parseFloat(details.getAccounting().getUnitPrice()));
        productPromoInput.setTaxAmount(Float.parseFloat(details.getAccounting().getTaxAmount()));
        productPromoInput.setCategory_id(null);
        productPromoInput.setTax(null);
        productPromoInput.setCityId(mViewModel.cityId);
        productPromoInput.setStore_id(details.getStoreId());
        productPromoInput.setQuantityData(details.getQuantity());
        productPromoInputs.add(productPromoInput);
        EcomUtil.printLog("exe" + "storeId" + details.getStoreId());
      }
    }
    return productPromoInputs;
  }

  String getStoreId() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < mCartProductItemData.size(); i++) {
      stringBuilder.append(mCartProductItemData.get(i).getStoreId()).append(",");
    }
    String storeIdValue = "";
    if (stringBuilder.toString().length() > ZERO) {
      storeIdValue = stringBuilder.toString().substring(ZERO,
          stringBuilder.toString().length() - ONE);
    }
    return storeIdValue;
  }
}