package com.customer.fivecanale.alldetails;

import static com.customer.fivecanale.util.EcomConstants.TRUE;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;

/**
 * view mData class for the all details activity
 */
public class AllDetailsViewModel extends ViewModel {
  public ObservableField<String> mProductImage = new ObservableField<>();
  private MutableLiveData<Boolean> mLiveData = new MutableLiveData<>();

  /**
   * constructor class for the all details activity.
   */
  @Inject
  AllDetailsViewModel() {
  }

  /**
   * <p>this method is used listen when cross icon clicked.</p>
   */
  public void onCrossIconClicked() {
    mLiveData.postValue(TRUE);
  }

  /**
   * notify when cross icon clicked
   */
  MutableLiveData<Boolean> onCrossClicked() {
    return mLiveData;
  }
}