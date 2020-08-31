package com.customer.fivecanale.notifications;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TEN;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.util.Pair;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetNotificationsUseCase;
import com.customer.domain.model.notification.NotificationListData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for the notifications activity.
 */
public class NotificationsViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField(FALSE);
  private UseCaseHandler mHandler;
  private GetNotificationsUseCase mGetNotificationsUseCase;
  private MutableLiveData<Boolean> mBack = new MutableLiveData<>();
  private MutableLiveData<Pair<Integer, ArrayList<NotificationListData>>> mListMutableLiveData =
      new MutableLiveData<>();
  private ArrayList<NotificationListData> mNotificationList = new ArrayList<>();

  /**
   * constructor for this class
   */
  @Inject
  public NotificationsViewModel(UseCaseHandler handler,
      GetNotificationsUseCase getNotificationsUseCase) {
    this.mHandler = handler;
    this.mGetNotificationsUseCase = getNotificationsUseCase;
  }

  /**
   * call the reorder  api
   *
   * @param from from index
   * @Param to index.
   */
  void callGetNotificationsApi(String from, String to) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetNotificationsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetNotificationsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetNotificationsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              if (Integer.parseInt(to) > TEN) {
                mNotificationList.addAll(responseValues.getData().getData());
              } else {
                mNotificationList.clear();
                mNotificationList.addAll(responseValues.getData().getData());
              }
              mListMutableLiveData.postValue(
                  Pair.create(Integer.parseInt(responseValues.getData().getTotalCount()),
                      mNotificationList));
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Notification Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetNotificationsUseCase,
        new GetNotificationsUseCase.RequestValues(from, to),
        disposableSingleObserver);
  }

  /**
   * notify when notification data comes..
   */
  MutableLiveData<Pair<Integer, ArrayList<NotificationListData>>> getNotificationData() {
    return mListMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mBack.postValue(TRUE);
  }

  /**
   * subscribe to  click on views.
   */
  MutableLiveData<Boolean> onBackClicked() {
    return mBack;
  }
}
