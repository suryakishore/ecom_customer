package com.customer.fivecanale.cancelorder;

import static com.customer.fivecanale.cancelorder.CancelOrderUiAction.CROSS;
import static com.customer.fivecanale.cancelorder.CancelOrderUiAction.SELECT_REASON;
import static com.customer.fivecanale.cancelorder.CancelOrderUiAction.SUBMIT_REQUEST;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ORDER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/**
 * view model class for the cancel product activity.
 */
public class CancelOrderOrProductViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<String> imageUrl = new ObservableField<>();
  public ObservableField<String> productName = new ObservableField<>();
  public ObservableField<String> productPrice = new ObservableField<>();
  public ObservableField<String> productActualPrice = new ObservableField<>();
  public ObservableField<Boolean> offerViews = new ObservableField<>(FALSE);
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public String orderId = "", reason = "";
  private String comments = "";
  private UseCaseHandler mHandler;
  private CancelOrderUseCase mCancelOrderUseCase;
  private MutableLiveData<CancelOrderUiAction> mClick = new MutableLiveData<>();

  @Inject
  CancelOrderOrProductViewModel(CancelOrderUseCase cancelOrderUseCase, UseCaseHandler handler) {
    mCancelOrderUseCase = cancelOrderUseCase;
    mHandler = handler;
  }

  /**
   * listen when submit request clicked.
   */
  public void submitRequest() {
    EcomUtil.printLog("exe" + "submitRequest" + "orderId" + orderId);
    callCancelOrderApi();
  }

  /**
   * listen when select reason clicked.
   */
  public void selectReason() {
    EcomUtil.printLog("exe" + "submitRequest");
    mClick.postValue(SELECT_REASON);
  }

  /**
   * handle text changes of comments
   */
  public void onCommentsChanged(CharSequence s, int start, int before, int count) {
    comments = s.toString();
  }

  /**
   * call the cancel order  api
   */
  private void callCancelOrderApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<CancelOrderUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<CancelOrderUseCase.ResponseValues>() {
          @Override
          public void onSuccess(CancelOrderUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("CancelOrder Succ");
            mClick.postValue(SUBMIT_REQUEST);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("CancelOrder Fail" + e.getMessage());
          }
        };
    mHandler.execute(mCancelOrderUseCase,
        new CancelOrderUseCase.RequestValues(PRODUCT_ORDER_TYPE, orderId, reason, comments),
        disposableSingleObserver);
  }

  @Override
  public void backButtonClickListener(View view) {
    mClick.postValue(CROSS);
  }

  /**
   * subscribe to  click on views.
   */
  public MutableLiveData<CancelOrderUiAction> onClick() {
    return mClick;
  }
}
