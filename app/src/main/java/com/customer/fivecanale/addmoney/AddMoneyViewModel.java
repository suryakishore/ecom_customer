package com.customer.fivecanale.addmoney;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.util.BackButtonClickListener;
import javax.inject.Inject;

/**
 * view model class for add money activity.
 */
public class AddMoneyViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  private MutableLiveData<Boolean> mBackLiveData = new MutableLiveData<>();

  @Inject
  AddMoneyViewModel() {
  }

  /**
   * notify wallet data comes
   *
   * @return mutable live data of strings
   */
  MutableLiveData<Boolean> setBackClick() {
    return mBackLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mBackLiveData.setValue(TRUE);
  }
}
