package com.customer.fivecanale.orderdetails;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetOrderDetailsUseCase;
import com.customer.domain.interactor.ReorderUseCase;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.orderdetails.OrderData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.model.orderhistory.OrderHistoryAccountingData;
import com.customer.domain.model.orderhistory.PayByData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

import static com.customer.fivecanale.orderdetails.HistoryOrderDetailUiAction.BACK;
import static com.customer.fivecanale.orderdetails.HistoryOrderDetailUiAction.BILLING_PHONE_NUM;
import static com.customer.fivecanale.orderdetails.HistoryOrderDetailUiAction.DELIVERY_PHONE_NUM;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.MASTER_ORDER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ORDER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.SEVEN;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

/**
 * view model class for history order detail.
 */
public class HistoryOrderDetailViewModel extends ViewModel implements
    BackButtonClickListener {
  public ObservableField<String> deliveryAddressName = new ObservableField<>();
  public ObservableField<String> deliveryAddress = new ObservableField<>();
  public ObservableField<SpannableString> deliveryPhoneNum = new ObservableField<>();
  public ObservableField<String> billingAddressName = new ObservableField<>();
  public ObservableField<String> billingAddress = new ObservableField<>();
  public ObservableField<SpannableString> billingPhoneNum = new ObservableField<>();
  public ObservableField<String> cancelOrReorder = new ObservableField<>();
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  String mOrderId;
  private MutableLiveData<HistoryOrderDetailUiAction> mClick = new MutableLiveData<>();
  private MutableLiveData<ArrayList<OrderData>> mLiveData = new MutableLiveData<>();
  private MutableLiveData<ArrayList<OrderHistProductData>> mLiveBoxesData = new MutableLiveData<>();
  private MutableLiveData<OrderHistoryAccountingData> mPriceDet = new MutableLiveData<>();
  private MutableLiveData<Integer> paymentMethod = new MutableLiveData<>();
  private GetOrderDetailsUseCase mGetOrderDetailUSeCase;
  private ReorderUseCase mReorderUseCase;
  private UseCaseHandler mHandler;

  /**
   * constructor for history order detail view model.
   *
   * @param getOrderDetailUSeCase get order details use case.
   * @param handler               handler object.
   */
  @Inject
  HistoryOrderDetailViewModel(GetOrderDetailsUseCase getOrderDetailUSeCase,
      ReorderUseCase reorderUseCase,
      UseCaseHandler handler) {
    this.mReorderUseCase = reorderUseCase;
    this.mGetOrderDetailUSeCase = getOrderDetailUSeCase;
    this.mHandler = handler;
  }

  /**
   * call the product details api.
   *
   * @param orderId product id
   */
  void callGetHistoryApi(String orderId, boolean splitOrder) {
    progressVisible.set(TRUE);
    String orderType = splitOrder ? PRODUCT_ORDER_TYPE : MASTER_ORDER_TYPE;
    DisposableSingleObserver<GetOrderDetailsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetOrderDetailsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetOrderDetailsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (splitOrder) {
              mLiveBoxesData.setValue(responseValues.getData().getProducts());
            } else {
              mLiveData.setValue(responseValues.getData().getStoreOrders());
            }
            if (responseValues.getData().getDeliveryAddress() != null) {
              AddressListItemData addressListItemData =
                  responseValues.getData().getDeliveryAddress();
              deliveryAddressName.set(addressListItemData.getName());
              deliveryAddress.set(String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getAddLine2(), addressListItemData.getLocality(),
                  addressListItemData.getPincode()));
              deliveryPhoneNum.set(getSpannableString(String.format("%s:%s %s",
                      ApplicationManager.getInstance().getString(R.string.phoneNumber),
                      addressListItemData.getMobileNumberCode(),
                      addressListItemData.getMobileNumber())));
            }
            if (responseValues.getData().getBillingAddress() != null) {
              AddressListItemData addressListItemData =
                  responseValues.getData().getBillingAddress();
              billingAddressName.set(addressListItemData.getName());
              billingAddress.set(String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getAddLine2(), addressListItemData.getLocality(),
                  addressListItemData.getPincode()));
              billingPhoneNum.set(getSpannableString(String.format("%s:%s %s",
                  ApplicationManager.getInstance().getString(R.string.phoneNumber),
                  addressListItemData.getMobileNumberCode(),
                  addressListItemData.getMobileNumber())));
            }
            mPriceDet.setValue(responseValues.getData().getAccounting());
            if (responseValues.getData().getStatus() != null) {
              int status = Integer.parseInt(responseValues.getData().getStatus().getStatus());
              if (status == ONE || status == TWO) {
                cancelOrReorder.set(
                    ApplicationManager.getInstance().getString(R.string.historyCancelOrder));
              } else if (status == THREE || status == SEVEN) {
                cancelOrReorder.set(
                    ApplicationManager.getInstance().getString(R.string.historyReOrder));
              }
            }
            int payment = ZERO;
            double walletAmt = ZERO;
            double cardAmt = ZERO;
            double cashAmt = ZERO;
            PayByData payByData = responseValues.getData().getAccounting().getPayBy();
            walletAmt = Float.parseFloat(payByData.getWallet());
            cardAmt = Float.parseFloat(payByData.getCard());
            cashAmt = Float.parseFloat(payByData.getCash());
            if (walletAmt > ZERO && cardAmt > ZERO) {
              payment = ONE;
            } else if (walletAmt > ZERO && cashAmt > ZERO) {
              payment = TWO;
            } else if (walletAmt > ZERO) {
              payment = THREE;
            } else if (cardAmt > ZERO) {
              payment = FOUR;
            } else if (cashAmt > ZERO) {
              payment = FIVE;
            }
            paymentMethod.setValue(payment);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("History Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetOrderDetailUSeCase,
        new GetOrderDetailsUseCase.RequestValues(orderType, orderId),
        disposableSingleObserver);
  }

  private SpannableString getSpannableString(String phone) {
    SpannableString spannable = new SpannableString(phone);
    spannable.setSpan(new ForegroundColorSpan(Color.BLUE),
            SIX, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannable;
  }

  /**
   * call the reorder  api
   *
   * @param orderId product id
   */
  private void callReorderApi(String orderId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ReorderUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ReorderUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ReorderUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mClick.postValue(BACK);
            EcomUtil.printLog("History Succ");
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("History Fail" + e.getMessage());
          }
        };
    mHandler.execute(mReorderUseCase,
        new ReorderUseCase.RequestValues(MASTER_ORDER_TYPE, orderId),
        disposableSingleObserver);
  }

  /**
   * listen when cancel order clicked.
   */
  public void cancelOrReorderClicked() {
    if (cancelOrReorder.get().equals(
        ApplicationManager.getInstance().getString(R.string.historyReOrder))) {
      callReorderApi(mOrderId);
    }
  }

  /**
   * called when delivery phone click
   */
  public void onDeliveryPhoneNumClick() {
    mClick.postValue(DELIVERY_PHONE_NUM);
  }

  /**
   * called when billing phone click
   */
  public void onBillingPhoneNumClick() {
    mClick.postValue(BILLING_PHONE_NUM);
  }

  /**
   * subscribe to on click
   */
  public MutableLiveData<HistoryOrderDetailUiAction> onClick() {
    return mClick;
  }

  /**
   * subscribe to on history product data
   */
  MutableLiveData<ArrayList<OrderData>> onHistoryProductsData() {
    return mLiveData;
  }

  /**
   * subscribe to on history product boxes data
   */
  MutableLiveData<ArrayList<OrderHistProductData>> onHistoryBoxData() {
    return mLiveBoxesData;
  }

  /**
   * notify for price details
   */
  MutableLiveData<OrderHistoryAccountingData> getPriceDet() {
    return mPriceDet;
  }

  /**
   * notify for payment method
   */
  MutableLiveData<Integer> getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * notify when back button clicked.
   *
   * @param view view which is clicked
   */
  @Override
  public void backButtonClickListener(View view) {
    mClick.postValue(BACK);
  }
}
