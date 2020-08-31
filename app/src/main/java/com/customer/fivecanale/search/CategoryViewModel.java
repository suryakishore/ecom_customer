package com.customer.fivecanale.search;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.customer.fivecanale.util.BackButtonClickListener;

import javax.inject.Inject;

import static com.customer.fivecanale.util.EcomConstants.TRUE;

/**
 * view model class for category activity
 */
public class CategoryViewModel extends ViewModel implements BackButtonClickListener {
  public MutableLiveData<Boolean> mLiveData = new MutableLiveData<>();

  @Inject
  public CategoryViewModel() {
  }

  /**
   * listen when back icon clicked.
   *
   * @param view view which is clicked
   */
  @Override
  public void backButtonClickListener(View view) {
    mLiveData.postValue(TRUE);
  }

  /**
   * notify when bacl icon clicked.
   *
   * @return returns the live data
   */
  MutableLiveData<Boolean> onBackClick() {
    return mLiveData;
  }
}
