package com.customer.fivecanale.historyfilter;

import static com.customer.fivecanale.util.EcomConstants.CANCELLED_ORDER_STATUS;
import static com.customer.fivecanale.util.EcomConstants.COMPLETED_ORDER_STATUS;
import static com.customer.fivecanale.util.EcomConstants.MINUS_ONE;
import static com.customer.fivecanale.util.EcomConstants.NONE;
import static com.customer.fivecanale.util.EcomConstants.OPEN_ORDER_STATUS;
import static com.customer.fivecanale.util.EcomConstants.ORDER_NAME;
import static com.customer.fivecanale.util.EcomConstants.ORDER_STATUS;
import static com.customer.fivecanale.util.EcomConstants.ORDER_TIME;
import static com.customer.fivecanale.util.EcomConstants.POSITION;
import static com.customer.fivecanale.util.EcomConstants.STATUS;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.filter.FilterListData;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityHistoryOrderFilterBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * history filter screen based on month and year
 */
public class HistoryOrderFilterActivity extends DaggerAppCompatActivity implements
    HistoryOrderFilterAdapter.OnHistoryFilteredDataSelected,
    CustomDialogUtil.SimpleAlertDialogClickHandler {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityHistoryOrderFilterBinding mBinding;
  private HistoryOrderFilterViewModel mHistoryOrderFilterViewModel;
  private ArrayList<FilterListData> mFilterListData = new ArrayList<>();
  private HistoryOrderFilterAdapter mHistoryOrderFilterAdapter;
  private String mTimeStamp = "", mName;
  private int status, mPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_history_order_filter);
    initialize();
    subScribeFilterData();
    subScribeBackData();
    mHistoryOrderFilterViewModel.getHistoryFilterData(getIntent().getStringExtra(ORDER_NAME));
  }

  /**
   * initialize view model
   */
  private void initialize() {
    mBinding.incHeader.tvClearFilter.setVisibility(View.VISIBLE);
    mBinding.incHeader.tvCenterCategoryName.setText(
        getResources().getString(R.string.productFilterHeader));
    mHistoryOrderFilterViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        HistoryOrderFilterViewModel.class);
    mBinding.setViewModel(mHistoryOrderFilterViewModel);
    mHistoryOrderFilterAdapter = new HistoryOrderFilterAdapter(mFilterListData,
        this::onFilteredDataSelectedListener);
    mBinding.rvOrderType.setAdapter(mHistoryOrderFilterAdapter);
    status = getIntent().getIntExtra(STATUS, ZERO);
    mPosition = getIntent().getIntExtra(POSITION, MINUS_ONE);
    mBinding.incHeader.tvClearFilter.setOnClickListener(view -> {
      CustomDialogUtil.basicAlertDialog(this, this, getString(R.string.alert),
          getString(R.string.clearFilterWarning), NONE);
    });
    mBinding.btnFilterApply.setOnClickListener(view -> {
      setOrderStatus();
      Intent intent = new Intent();
      intent.putExtra(ORDER_NAME, mName);
      intent.putExtra(POSITION, mPosition);
      intent.putExtra(ORDER_TIME, mTimeStamp);
      intent.putExtra(STATUS, status);
      setResult(RESULT_OK, intent);
      finish();
    });
  }

  /**
   * subscribe for filter data
   */
  private void subScribeFilterData() {
    mHistoryOrderFilterViewModel.getFilterParamLiveData().observe(this,
        filterListData -> {
          mFilterListData.clear();
          mFilterListData.addAll(filterListData);
          mHistoryOrderFilterAdapter.notifyDataSetChanged();
          initializeValues();
        });
  }

  /**
   * subscribe to back click
   */
  private void subScribeBackData() {
    mHistoryOrderFilterViewModel.backLiveData().observe(this, aBoolean -> finish());
  }

  @Override
  public void onFilteredDataSelectedListener(int position, String name, String timeStamp) {
    mName = name;
    mPosition = position;
    mTimeStamp = timeStamp;
  }

  @Override
  public void onOkClickListener(int type) {
    mPosition = MINUS_ONE;
    mTimeStamp = "";
    status = ZERO;
    Intent intent = new Intent();
    intent.putExtra(ORDER_NAME, mName);
    intent.putExtra(POSITION, mPosition);
    intent.putExtra(ORDER_TIME, mTimeStamp);
    intent.putExtra(STATUS, status);
    setResult(RESULT_OK, intent);
    finish();
  }

  /**
   * initialize the order status
   */
  private void setOrderStatus() {
    if (mBinding.rbOrders.isChecked()) {
      status = ORDER_STATUS;
    } else if (mBinding.rbOpenOrders.isChecked()) {
      status = OPEN_ORDER_STATUS;
    } else if (mBinding.rbCompletedOrders.isChecked()) {
      status = COMPLETED_ORDER_STATUS;
    } else {
      status = CANCELLED_ORDER_STATUS;
    }
  }

  /**
   * initializing values
   */
  private void initializeValues() {
    EcomUtil.printLog("exe" + "status" + status);
    switch (status) {
      case ORDER_STATUS:
        mBinding.rbOrders.setChecked(TRUE);
        break;
      case OPEN_ORDER_STATUS:
        mBinding.rbOpenOrders.setChecked(TRUE);
        break;
      case COMPLETED_ORDER_STATUS:
        mBinding.rbCompletedOrders.setChecked(TRUE);
        break;
      default:
        mBinding.rbCancelledOrders.setChecked(TRUE);
    }
    if (mPosition != MINUS_ONE) {
      mFilterListData.get(mPosition).setSelected(TRUE);
    }
  }
}
