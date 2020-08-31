package com.customer.fivecanale.helpcenter;

import static com.customer.remote.http.RemoteConstants.PACK_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ORDER_ID;

import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.model.notification.NotificationListData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.historyproductdetail.HistoryProductDetailActivity;
import com.customer.fivecanale.notifications.NotificationsViewModel;
import com.databinding.HelpIndexBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * This activity is used to show all the help center points for the user.
 */
public class HelpIndexActivity extends DaggerAppCompatActivity implements SelectItem {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private HelpIndexBinding mHelpIndexBinding;
  private NotificationsViewModel mHelpIndexViewModel;
  private HelpIndexAdapter mHelpIndexAdapter;
  private ArrayList<NotificationListData> mNotificationList = new ArrayList<>();

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
    mHelpIndexBinding = DataBindingUtil.setContentView(this,
        R.layout.help_index);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mHelpIndexViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        NotificationsViewModel.class);
/*
    mHelpIndexBinding.setViewmodel(mHelpIndexViewModel);
*/
    mHelpIndexBinding.header.tvCenterCategoryName.setText(
        getResources().getString(R.string.helpcenter));
    mHelpIndexAdapter = new HelpIndexAdapter(mNotificationList, this::onSelectItem);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mHelpIndexBinding.recyclerHelpIndex.setLayoutManager(linearLayoutManager);
    mHelpIndexBinding.recyclerHelpIndex.setAdapter(mHelpIndexAdapter);
/*
    mHelpIndexViewModel.callGetNotificationsApi(String.valueOf(ZERO), String.valueOf(LIMIT));
*/
  }

  /**
   * subscribe to notification data
   */
  private void subScribeToNotificationData() {
  }

  /**
   * subscribe for back icon click.
   */
  private void subScribeToBack() {
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
