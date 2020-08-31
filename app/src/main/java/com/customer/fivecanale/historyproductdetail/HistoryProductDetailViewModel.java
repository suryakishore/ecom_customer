package com.customer.fivecanale.historyproductdetail;

import static com.customer.fivecanale.historyproductdetail.HistoryProductDetailUiAction.BACK;
import static com.customer.fivecanale.historyproductdetail.HistoryProductDetailUiAction.CANCEL_ORDER;
import static com.customer.fivecanale.historyproductdetail.HistoryProductDetailUiAction.DOWNLOAD_INVOICE;
import static com.customer.fivecanale.historyproductdetail.HistoryProductDetailUiAction.RATE_NOW;
import static com.customer.fivecanale.historyproductdetail.HistoryProductDetailUiAction.REORDER;
import static com.customer.fivecanale.historyproductdetail.HistoryProductDetailUiAction.TRACKING;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ORDER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.SEVEN;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.util.Pair;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetOrderDetailsUseCase;
import com.customer.domain.interactor.GetPackageDetailsUseCase;
import com.customer.domain.interactor.ReorderUseCase;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.Objects;
import javax.inject.Inject;

/**
 * view model class for the HistoryProductDetail activity
 */
public class HistoryProductDetailViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<String> deliveryAddressName = new ObservableField<>();
  public ObservableField<String> deliveryAddress = new ObservableField<>();
  public ObservableField<String> deliveryPhoneNum = new ObservableField<>();
  public ObservableField<String> billingAddressName = new ObservableField<>();
  public ObservableField<String> billingAddress = new ObservableField<>();
  public ObservableField<String> billingPhoneNum = new ObservableField<>();
  public ObservableField<String> trackingId = new ObservableField<>();
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> returnProductVisible = new ObservableField<>(FALSE);
  public ObservableField<String> cancelOrReorder = new ObservableField<>();
  public String productOrderId = "";
  private MutableLiveData<HistoryProductDetailUiAction> mClick = new MutableLiveData<>();
  private GetPackageDetailsUseCase mGetPackageDetailsUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<Pair<Boolean, Object>> mOrderDataMutableLiveData =
      new MutableLiveData<>();
  private ReorderUseCase mReorderUseCase;
  private GetOrderDetailsUseCase mGetOrderDetailUSeCase;
  private int mStatus;

  /**
   * constructor for this class
   */
  @Inject
  public HistoryProductDetailViewModel(GetPackageDetailsUseCase packageDetailsUseCase,
      ReorderUseCase reorderUseCase, GetOrderDetailsUseCase getOrderDetailUSeCase,
      UseCaseHandler handler) {
    this.mHandler = handler;
    this.mReorderUseCase = reorderUseCase;
    this.mGetPackageDetailsUseCase = packageDetailsUseCase;
    this.mGetOrderDetailUSeCase = getOrderDetailUSeCase;
  }

  /**
   * call the package details use case
   */
  void getPackageDetails(String productOrderId, String packageId) {
    EcomUtil.printLog("exe"+"productOrderId"+productOrderId);
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetPackageDetailsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetPackageDetailsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetPackageDetailsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mOrderDataMutableLiveData.postValue(Pair.create(TRUE, responseValues.getData()));
            if (responseValues.getData().getProducts().get(ZERO) != null) {
              mStatus = Integer.parseInt(responseValues.getData().getProducts().get(
                  ZERO).getStatus().getStatus());
              EcomUtil.printLog("exe" + "mStatus" + mStatus);
              if (mStatus == ONE || mStatus == TWO) {
                cancelOrReorder.set(
                    ApplicationManager.getInstance().getString(R.string.historyCancelOrder));
              } else if (mStatus == THREE || mStatus == SEVEN) {
                cancelOrReorder.set(
                    ApplicationManager.getInstance().getString(R.string.historyReOrder));
              }
              if (mStatus == SEVEN) {
                returnProductVisible.set(TRUE);
              }
            }
            trackingId.set((responseValues.getData().getPartnerData().getTrackingId() != null
                && !responseValues.getData().getPartnerData().getTrackingId().isEmpty())
                ? String.format("%s (%s)",
                ApplicationManager.getInstance().getResources().getString(
                    R.string.historyTrackShipment),
                responseValues.getData().getPartnerData().getTrackingId())
                : String.format("%s", ApplicationManager.getInstance().getResources().getString(
                    R.string.historyTrackShipment)));
            if (responseValues.getData().getDeliveryAddress() != null) {
              AddressListItemData addressListItemData =
                  responseValues.getData().getDeliveryAddress();
              deliveryAddressName.set(addressListItemData.getName());
              deliveryAddress.set(String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getAddLine2(), addressListItemData.getLocality(),
                  addressListItemData.getPincode()));
              deliveryPhoneNum.set(String.format("%s:%s %s",
                  ApplicationManager.getInstance().getString(R.string.phoneNumber),
                  addressListItemData.getMobileNumberCode(),
                  addressListItemData.getMobileNumber()));
            }
            if (responseValues.getData().getBillingAddress() != null) {
              AddressListItemData addressListItemData =
                  responseValues.getData().getBillingAddress();
              billingAddressName.set(addressListItemData.getName());
              billingAddress.set(String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getAddLine2(), addressListItemData.getLocality(),
                  addressListItemData.getPincode()));
              billingPhoneNum.set(String.format("%s:%s %s",
                  ApplicationManager.getInstance().getString(R.string.phoneNumber),
                  addressListItemData.getMobileNumberCode(),
                  addressListItemData.getMobileNumber()));
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("History Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetPackageDetailsUseCase,
        new GetPackageDetailsUseCase.RequestValues(productOrderId, packageId),
        disposableSingleObserver);
  }

  /**
   * call the product details api
   *
   * @param orderId product id
   */
  void callGetHistoryApi(String orderId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetOrderDetailsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetOrderDetailsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetOrderDetailsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mOrderDataMutableLiveData.postValue(Pair.create(FALSE, responseValues.getData()));
            if (responseValues.getData().getProducts() != null
                && responseValues.getData().getProducts().size() > ZERO) {
              int status = Integer.parseInt(responseValues.getData().getProducts().get(
                  ZERO).getStatus().getStatus());
              if (status == ONE || status == TWO) {
                cancelOrReorder.set(
                    ApplicationManager.getInstance().getString(R.string.historyCancelOrder));
              } else if (status == THREE || status == SEVEN) {
                cancelOrReorder.set(
                    ApplicationManager.getInstance().getString(R.string.historyReOrder));
              }
              if (status == SEVEN) {
                returnProductVisible.set(TRUE);
              }
            }
            if (responseValues.getData().getDeliveryAddress() != null) {
              AddressListItemData addressListItemData =
                  responseValues.getData().getDeliveryAddress();
              deliveryAddressName.set(addressListItemData.getName());
              deliveryAddress.set(String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getAddLine2(), addressListItemData.getLocality(),
                  addressListItemData.getPincode()));
              deliveryPhoneNum.set(String.format("%s:%s %s",
                  ApplicationManager.getInstance().getString(R.string.phoneNumber),
                  addressListItemData.getMobileNumberCode(),
                  addressListItemData.getMobileNumber()));
            }
            if (responseValues.getData().getBillingAddress() != null) {
              AddressListItemData addressListItemData =
                  responseValues.getData().getBillingAddress();
              billingAddressName.set(addressListItemData.getName());
              billingAddress.set(String.format("%s%s,%s,%s", addressListItemData.getAddLine1(),
                  addressListItemData.getAddLine2(), addressListItemData.getLocality(),
                  addressListItemData.getPincode()));
              billingPhoneNum.set(String.format("%s:%s %s",
                  ApplicationManager.getInstance().getString(R.string.phoneNumber),
                  addressListItemData.getMobileNumberCode(),
                  addressListItemData.getMobileNumber()));
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("History Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetOrderDetailUSeCase,
        new GetOrderDetailsUseCase.RequestValues(PRODUCT_ORDER_TYPE, orderId),
        disposableSingleObserver);
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
            mClick.postValue(REORDER);
            EcomUtil.printLog("exe" + "History Succ");
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("History Fail" + e.getMessage());
          }
        };
    mHandler.execute(mReorderUseCase,
        new ReorderUseCase.RequestValues(PRODUCT_ORDER_TYPE, orderId),
        disposableSingleObserver);
  }

  /**
   * listen when cancel order clicked.
   */
  public void cancelOrReorderClicked() {
    if (Objects.requireNonNull(cancelOrReorder.get()).equals(
        ApplicationManager.getInstance().getString(R.string.historyReOrder))) {
      callReorderApi(productOrderId);
    } else {
      mClick.postValue(CANCEL_ORDER);
    }
  }

  /**
   * listen click on rate now.
   */
  public void rateNow() {
    mClick.postValue(RATE_NOW);
  }

  /**
   * listen click on rate now.
   */
  public void downLoadInvoice() {
    mClick.postValue(DOWNLOAD_INVOICE);
  }

  /**
   * listen click on rate now.
   */
  public void tracking() {
    mClick.postValue(TRACKING);
  }

  /**
   * subscribe to  click on views.
   */
  public MutableLiveData<HistoryProductDetailUiAction> onClick() {
    return mClick;
  }

  /**
   * subscribe to  click on views.
   */
  public MutableLiveData<Pair<Boolean, Object>> getData() {
    return mOrderDataMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mClick.postValue(BACK);
  }
}
