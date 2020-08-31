package com.customer.fivecanale.historyfilter;

import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.BuildConfig;
import com.R;
import com.customer.domain.model.filter.FilterListData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.DateAndTimeUtil;
import com.customer.fivecanale.util.EcomUtil;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;

/**
 * view model class for the history order filter activity
 */
public class HistoryOrderFilterViewModel extends ViewModel implements BackButtonClickListener {
  private MutableLiveData<ArrayList<FilterListData>> mFilterParams = new MutableLiveData<>();
  private MutableLiveData<Boolean> mBack = new MutableLiveData<>();

  @Inject
  public HistoryOrderFilterViewModel() {
  }

  @Override
  public void backButtonClickListener(View view) {
    mBack.postValue(TRUE);
  }

  /**
   * Getter for Filter Param LiveData
   *
   * @return Filter param LiveData
   */
  MutableLiveData<ArrayList<FilterListData>> getFilterParamLiveData() {
    return mFilterParams;
  }

  /**
   * Getter for Filter Param LiveData
   *
   * @return Filter param LiveData
   */
  MutableLiveData<Boolean> backLiveData() {
    return mBack;
  }

  /*
   * used to get the history filter data
   *
   * @return filter array list
   */
  void getHistoryFilterData(String orderTime) {
    ArrayList<FilterListData> subListData = new ArrayList<>();
    String[] historyFilterTypes = ApplicationManager.getInstance().getResources().getStringArray(
        R.array.history_filter_types);
    for (int i = ZERO; i < historyFilterTypes.length; i++) {
      FilterListData filterListData = null;
      String filterName = historyFilterTypes[i];
      filterListData = new FilterListData(filterName, DateAndTimeUtil.getCurrentTimestamp(i));
      if (filterName.equals(orderTime)) {
        filterListData.setSelected(TRUE);
      }
      subListData.add(filterListData);
    }
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    for (int i = year; i >= BuildConfig.APP_STARTED_YEAR; i--) {
      FilterListData filterListData = null;
      filterListData = new FilterListData(String.valueOf(i),
          DateAndTimeUtil.getTimeStampBasedOnYear(i));
      EcomUtil.printLog("exe" + "timeStamp " + DateAndTimeUtil.getTimeStampBasedOnYear(i));
      if (String.valueOf(i).equals(orderTime)) {
        filterListData.setSelected(TRUE);
      }
      subListData.add(filterListData);
    }
    mFilterParams.postValue(subListData);
  }
}
