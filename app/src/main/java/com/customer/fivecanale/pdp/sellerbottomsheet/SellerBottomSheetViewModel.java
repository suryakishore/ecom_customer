package com.customer.fivecanale.pdp.sellerbottomsheet;

import static com.customer.fivecanale.util.EcomConstants.TRUE;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;

/**
 * holds the logic of sellers info dialog
 */
public class SellerBottomSheetViewModel extends ViewModel {
  public ObservableField<String> termsOrPrivacyData = new ObservableField();
  private MutableLiveData<Boolean> mLiveData = new MutableLiveData<>();

  /**
   * constructor for this mData
   */
  @Inject
  public SellerBottomSheetViewModel() {
  }

  /**
   * notify when cross icon clicked.
   */
  public void onCrossClicked() {
    mLiveData.postValue(TRUE);
  }

  /*
   * notify when clicking action happened
   */
  MutableLiveData<Boolean> onBackIconClick() {
    return mLiveData;
  }
}