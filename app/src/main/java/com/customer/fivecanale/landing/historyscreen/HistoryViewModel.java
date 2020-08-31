package com.customer.fivecanale.landing.historyscreen;

import static com.customer.fivecanale.util.EcomConstants.ALL_ORDERS;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.TEN;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetOrderHistoryUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.orderhistory.OrderHistoryItemData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for history view model
 */
public class HistoryViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  public ObservableField<Boolean> itemLoadingVisible = new ObservableField<>(FALSE);
  @Inject
  public UserInfoHandler mUserInfoHandler;
  boolean isLoading;
  ArrayList<OrderHistoryItemData> mOrderHistoryItemData = new ArrayList<>();
  private MutableLiveData<String> mSearchData = new MutableLiveData<>();
  private GetOrderHistoryUseCase mGetOrderHistoryUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<ArrayList<OrderHistoryItemData>> mHistoryItemDataMutableLiveData =
      new MutableLiveData<>();
  private ArrayList<OrderHistoryItemData> mFilterHistoryItemData = new ArrayList<>();
  private MutableLiveData<Integer> mCountLivaData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mApplyFilter = new MutableLiveData<>();

  @Inject
  public HistoryViewModel(GetOrderHistoryUseCase getOrderHistoryUseCase, UseCaseHandler handler) {
    this.mGetOrderHistoryUseCase = getOrderHistoryUseCase;
    this.mHandler = handler;
  }

  /** This method is using for subscribing to User history data */
  void onHistoryUpdate() {
    EcomUtil.printLog("onHistoryUpdate");
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getHistoryDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(histProductData -> {
              EcomUtil.printLog("onHistoryUpdate" + histProductData.getName());
              mOrderHistoryItemData.clear();
              callGetHistoryApi(ZERO, LIMIT, ZERO, "", "");
            }));
  }

  /**
   * get the history of orders
   *
   * @param skip  skip
   * @param limit limit
   */
  void callGetHistoryApi(int skip, int limit, int status, String search, String timeStamp) {
    progressVisible.set(TRUE);
    isLoading = TRUE;
    DisposableSingleObserver<GetOrderHistoryUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetOrderHistoryUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetOrderHistoryUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            itemLoadingVisible.set(FALSE);
            isLoading = FALSE;
            mCountLivaData.setValue(responseValues.getData().getCount());
            if (responseValues.getData() != null) {
              if (!timeStamp.isEmpty()) {
                if (limit > TEN) {
                  mFilterHistoryItemData.addAll(responseValues.getData().getData());
                  mHistoryItemDataMutableLiveData.setValue(mFilterHistoryItemData);
                } else {
                  mFilterHistoryItemData.clear();
                  mFilterHistoryItemData.addAll(responseValues.getData().getData());
                  mHistoryItemDataMutableLiveData.setValue(mFilterHistoryItemData);
                }
              } else if (search.isEmpty()) {
                if (limit > TEN) {
                  mOrderHistoryItemData.addAll(responseValues.getData().getData());
                } else {
                  mOrderHistoryItemData.clear();
                  mOrderHistoryItemData.addAll(responseValues.getData().getData());
                }
                mHistoryItemDataMutableLiveData.setValue(mOrderHistoryItemData);
              } else {
                if (limit > TEN) {
                  mFilterHistoryItemData.addAll(responseValues.getData().getData());
                  mHistoryItemDataMutableLiveData.setValue(mFilterHistoryItemData);
                } else {
                  mFilterHistoryItemData.clear();
                  mFilterHistoryItemData.addAll(responseValues.getData().getData());
                  mHistoryItemDataMutableLiveData.setValue(mFilterHistoryItemData);
                }
              }
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            itemLoadingVisible.set(FALSE);
            isLoading = FALSE;
            EcomUtil.printLog("Pdp Fail" + e.toString());
          }
        };
    mHandler.execute(mGetOrderHistoryUseCase,
        new GetOrderHistoryUseCase.RequestValues(limit, skip, status, ALL_ORDERS, ALL_ORDERS,
            ALL_ORDERS, search, timeStamp),
        disposableSingleObserver);
  }

  public void myOrdersClicked() {
    mApplyFilter.postValue(TRUE);
  }

  /**
   * <p>this method is used listen when cross icon clicked.</p>
   */
  public void onCrossIconClicked() {
    mSearchData.postValue("");
  }

  /**
   * This method is using to get the history Mutable mData
   *
   * @return history mutable mData
   */
  MutableLiveData<ArrayList<OrderHistoryItemData>> getOrderHistoryLiveData() {
    return mHistoryItemDataMutableLiveData;
  }

  /**
   * This method is using to get the history Mutable mData
   *
   * @return history mutable mData
   */
  MutableLiveData<Boolean> applyFilter() {
    return mApplyFilter;
  }

  /**
   * This method is using to get the history Mutable mData
   *
   * @return history mutable mData
   */
  MutableLiveData<Integer> getOrderHistoryCount() {
    return mCountLivaData;
  }

  /**
   * returns live data clicking.
   */
  MutableLiveData<String> searchData() {
    return mSearchData;
  }
}
