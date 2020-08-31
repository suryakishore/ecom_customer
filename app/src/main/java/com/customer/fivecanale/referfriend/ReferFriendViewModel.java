package com.customer.fivecanale.referfriend;

import static com.customer.fivecanale.referfriend.ReferFriendUiAction.BACK;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.util.BackButtonClickListener;
import javax.inject.Inject;

public class ReferFriendViewModel extends ViewModel implements BackButtonClickListener {

  private MutableLiveData<ReferFriendUiAction> mUiActionLiveData = new MutableLiveData<>();

  @Inject
  ReferFriendViewModel() {
  }

  /**
   * This method is using to get Ui action observable live mData
   *
   * @return observable live mData
   */
  MutableLiveData<ReferFriendUiAction> getUiActionLiveData() {
    return mUiActionLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mUiActionLiveData.postValue(BACK);
  }
}
