package com.customer.fivecanale.notifications;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.PACK_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ORDER_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.notification.NotificationListData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.historyproductdetail.HistoryProductDetailActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.ActivityNotificationsBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * This activity is used to show all the notifications for the user.
 */
public class NotificationsActivity extends DaggerAppCompatActivity implements SelectItem {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityNotificationsBinding mActivityNotificationsBinding;
  private NotificationsViewModel mNotificationsViewModel;
  private NotificationsAdapter mNotificationsAdapter;
  private ArrayList<NotificationListData> mNotificationList = new ArrayList<>();
  private int mPenCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subScribeToNotificationData();
    subScribeToBack();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mActivityNotificationsBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_notifications);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mNotificationsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        NotificationsViewModel.class);
    mActivityNotificationsBinding.setViewmodel(mNotificationsViewModel);
    mActivityNotificationsBinding.header.tvCenterCategoryName.setText(
        getResources().getString(R.string.notificaions));
    mNotificationsAdapter = new NotificationsAdapter(mNotificationList, this::onSelectItem);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mActivityNotificationsBinding.rvNotifications.setLayoutManager(linearLayoutManager);
    mActivityNotificationsBinding.rvNotifications.setAdapter(mNotificationsAdapter);
    mNotificationsViewModel.callGetNotificationsApi(String.valueOf(ZERO), String.valueOf(LIMIT));
    mActivityNotificationsBinding.rvNotifications.addOnScrollListener(
        new MyScrollListener(linearLayoutManager) {
          @Override
          protected void loadMoreItems() {
            EcomUtil.printLog(
                "exe" + "mNotifica   " + mNotificationList.size() + "mPenCount" + mPenCount);
            if (mNotificationList.size() < mPenCount) {
              EcomUtil.printLog("exe" + "sizeWhileCalling" + mNotificationList.size() + "LIMIT " + (
                  mNotificationList.size() + LIMIT) + "mPenCount" + mPenCount);
              mNotificationsViewModel.callGetNotificationsApi(
                  String.valueOf(mNotificationList.size()),
                  String.valueOf(mNotificationList.size() + LIMIT));
            }
          }

          @Override
          public boolean isLastPage() {
            return mNotificationsViewModel.progressVisible.get();
          }

          @Override
          public boolean isLoading() {
            return mNotificationsViewModel.progressVisible.get();
          }
        });
    mActivityNotificationsBinding.refreshSr.setOnRefreshListener(() -> {
      if (mActivityNotificationsBinding.refreshSr.isRefreshing()) {
        mActivityNotificationsBinding.refreshSr.setRefreshing(FALSE);
      }
      mNotificationsViewModel.callGetNotificationsApi(String.valueOf(ZERO), String.valueOf(LIMIT));
    });
  }

  /**
   * subscribe to notification data
   */
  private void subScribeToNotificationData() {
    mNotificationsViewModel.getNotificationData().observe(this,
        integerArrayListPair -> {
          mPenCount = integerArrayListPair.first;
          mNotificationList.clear();
          mNotificationList.addAll(integerArrayListPair.second);
          mActivityNotificationsBinding.vgNoNotificationsFound.setVisibility(
              mNotificationList.size() > ZERO ? View.GONE : View.VISIBLE);
          mNotificationsAdapter.notifyDataSetChanged();
        });
  }

  /**
   * subscribe for back icon click.
   */
  private void subScribeToBack() {
    mNotificationsViewModel.onBackClicked().observe(this, aBoolean -> finish());
  }

  @Override
  public void onSelectItem(int position) {
    NotificationListData notificationListData = mNotificationList.get(position);
    Bundle bundle = new Bundle();
    bundle.putString(PRODUCT_ORDER_ID, notificationListData.getProductOrderId());
    bundle.putString(PACK_ID, notificationListData.getPackageId());
    if (notificationListData.getProductOrderId() != null
        && !notificationListData.getProductOrderId().isEmpty()) {
      Intent historyDetailIntent = new Intent(this, HistoryProductDetailActivity.class);
      historyDetailIntent.putExtras(bundle);
      startActivity(historyDetailIntent);
    }
  }
}
