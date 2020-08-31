package com.customer.fivecanale.selectreason;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.REASON;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.cancelreasons.CancelReasonsItemData;
import com.databinding.ActivitySelectReasonBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.inject.Inject;

/**
 * used for select the particular reason for the select reason activity.
 */
public class SelectReasonActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private SelectReasonModel mSelectReasonModel;
  private ActivitySelectReasonBinding mActivitySelectReasonBinding;
  private SelectReasonAdapter mSelectReasonAdapter;
  private ArrayList<CancelReasonsItemData> mReasonsItemDataArrayList = new ArrayList<>();
  private ArrayList<CancelReasonsItemData> mSelectedList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeOnClick();
    subscribeGetReasons();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mActivitySelectReasonBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_select_reason);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mSelectReasonModel = ViewModelProviders.of(this, mViewModelFactory).get(
        SelectReasonModel.class);
    mActivitySelectReasonBinding.setViewModel(mSelectReasonModel);
    mActivitySelectReasonBinding.includeCartHeader.ivBackBtn.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.all_back));
    mActivitySelectReasonBinding.includeCartHeader.tvTitle.setText(
        getResources().getString(R.string.selectReason));
    mSelectReasonAdapter = new SelectReasonAdapter(mReasonsItemDataArrayList);
    mActivitySelectReasonBinding.rvSelectReason.setAdapter(mSelectReasonAdapter);
    mSelectReasonModel.callGetReasonsApi();
  }

  /**
   * subscribe for the  on click
   */
  private void subscribeOnClick() {
    mSelectReasonModel.onClick().observe(this, cancelOrderUiAction -> {
      switch (cancelOrderUiAction) {
        case CROSS:
          finish();
          break;
        case SAVE:
          mSelectedList.clear();
          mSelectedList.addAll(mReasonsItemDataArrayList);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Predicate<CancelReasonsItemData> condition =
                cancelReasonsItemData -> cancelReasonsItemData.isChecked() == FALSE;
            mSelectedList.removeIf(condition);
          }
          if (mSelectedList.size() > ZERO) {
            CancelReasonsItemData cancelReasonsItemData = mSelectedList.get(ZERO);
            Intent intent = new Intent();
            intent.putExtra(REASON, cancelReasonsItemData.getReason());
            setResult(RESULT_OK, intent);
            finish();
          } else {
            showError(getResources().getString(R.string.pleaseSelectReason));
          }
          break;
        case ERROR:
          break;
      }
    });
  }

  /**
   * subscribe for the  on click
   */
  private void subscribeGetReasons() {
    mSelectReasonModel.onGetReasons().observe(this,
        cancelReasonsItemData -> {
          mReasonsItemDataArrayList.clear();
          mReasonsItemDataArrayList.addAll(cancelReasonsItemData);
          mSelectReasonAdapter.notifyDataSetChanged();
        });
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mActivitySelectReasonBinding.clSelectReason, error, Snackbar.LENGTH_SHORT).show();
  }
}
