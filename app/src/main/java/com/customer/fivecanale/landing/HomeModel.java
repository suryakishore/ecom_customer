package com.customer.fivecanale.landing;

import static com.customer.fivecanale.util.EcomConstants.ACTIVE_ORDER_STATUS;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.TEN;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.os.Build;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.OrderCountUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.ordercount.OrderCountItemListData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.inject.Inject;

/**
 * model class for home activity.
 */
public class HomeModel extends ViewModel {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  private UseCaseHandler mHandler;
  private OrderCountUseCase mOrderCountUseCase;
  private MutableLiveData<Integer> mCartCountData = new MutableLiveData<>();
  private MutableLiveData<Integer> mHistoryCountData = new MutableLiveData<>();

  @Inject
  HomeModel(UseCaseHandler useCaseHandler,
      OrderCountUseCase orderCountUseCase) {
    this.mHandler = useCaseHandler;
    this.mOrderCountUseCase = orderCountUseCase;
  }

  /** This method is using for subscribing to User cart data */
  void onCartUpdate() {
    EcomUtil.printLog("onHomeUpdate");
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getCartDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cartData -> {
              EcomUtil.printLog("onHomeUpdate" + cartData.getAction());
              if (cartData.getAction() == TEN) {
                callGetOrdersCountApi();
              }
              if (cartData.getAction() == ZERO || cartData.getAction() == ONE
                  || cartData.getAction() == THREE || cartData.getAction() == FOUR
                  || cartData.getAction() == FIVE || cartData.getAction() == SIX) {
                mCartCountData.setValue(cartData.getAction());
              }
            }));
  }

  /**
   * call the app config  api
   */
  void callGetOrdersCountApi() {
    DisposableSingleObserver<OrderCountUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<OrderCountUseCase.ResponseValues>() {
          @Override
          public void onSuccess(OrderCountUseCase.ResponseValues responseValues) {
            if (responseValues.getData() != null) {
              if (responseValues.getData().getData() != null) {
                ArrayList<OrderCountItemListData> orderCountItemListData =
                    responseValues.getData().getData();
                if (orderCountItemListData != null && orderCountItemListData.size() > ZERO) {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Predicate<OrderCountItemListData> condition =
                        orderCountItemData -> orderCountItemData.getStatus() != ACTIVE_ORDER_STATUS;
                    orderCountItemListData.removeIf(condition);
                  }
                  if (orderCountItemListData.size() == ONE) {
                    OrderCountItemListData orderCountItem = orderCountItemListData.get(ZERO);
                    mHistoryCountData.postValue(orderCountItem.getCount());
                  }
                }
              }
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Order Count Fail" + e.getMessage());
          }
        };
    mHandler.execute(mOrderCountUseCase,
        new OrderCountUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * notify for the live data
   */
  public MutableLiveData<Integer> getCartCountData() {
    return mCartCountData;
  }

  /**
   * notify for the live data when history count data comes.
   */
  public MutableLiveData<Integer> getHistoryCountData() {
    return mHistoryCountData;
  }
}
