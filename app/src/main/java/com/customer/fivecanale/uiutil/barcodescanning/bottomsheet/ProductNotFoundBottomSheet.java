package com.customer.fivecanale.uiutil.barcodescanning.bottomsheet;

import static com.customer.fivecanale.util.EcomConstants.BOTTOM_SHEET_LOAD_DELAY;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.fivecanale.uiutil.barcodescanning.BarCodeViewModel;
import com.customer.fivecanale.util.LockableBottomSheetBehavior;
import com.databinding.BottomsheetProductNotFoundBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;
import javax.inject.Inject;

/*shows the dialog for no products found*/
public class ProductNotFoundBottomSheet extends BottomSheetDialogFragment {
  private BottomSheetBehavior mBehavior;
  private BottomsheetProductNotFoundBinding mBinding;
  private ProductNotFoundDelegate mViewModel;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;

  private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
      new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
          switch (newState) {

            case BottomSheetBehavior.STATE_COLLAPSED:
              break;

            case BottomSheetBehavior.STATE_SETTLING:
              break;

            case BottomSheetBehavior.STATE_EXPANDED:
              break;
            case STATE_HIDDEN:
              dismiss();
              break;
            case BottomSheetBehavior.STATE_DRAGGING:
              mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
              break;

            case BottomSheetBehavior.STATE_HALF_EXPANDED:
              break;
          }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }

      };

  @Inject
  public ProductNotFoundBottomSheet() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
        R.layout.bottomsheet_product_not_found, null, false);
    mBinding.incEmptyScreen.clEmptyScreenMain.setVisibility(View.VISIBLE);
    mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()),
        mViewModelFactory).get(
        BarCodeViewModel.class);
    mBinding.ivCloseBtn.setOnClickListener(view -> {
      mViewModel.onCloseBtnClickListener();
      dismiss();
    });
    mBinding.btnScanAgain.setOnClickListener(view -> {
      mViewModel.onScanAgainBtnClickListener();
      dismiss();
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    setHeight();
  }

  /**
   * Set height of the bottomSheet dynamically based mData loaded
   */
  private void setHeight() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(
        displayMetrics);
    new Handler().postDelayed(() -> mBehavior.setPeekHeight(
        mBinding.clBtmSheetMain.getHeight()), BOTTOM_SHEET_LOAD_DELAY);
  }

  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(@NonNull Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    dialog.setContentView(mBinding.getRoot());
    FrameLayout bottomSheet = Objects.requireNonNull(dialog.getWindow()).findViewById(
        R.id.design_bottom_sheet);
    bottomSheet.setBackgroundResource(R.drawable.white_bag);
    initViews(mBinding.getRoot());
  }

  /**
   * This method is using to initialization of View parameters
   *
   * @param rootView rootView for get view elements
   */
  private void initViews(View rootView) {
    CoordinatorLayout.LayoutParams params =
        (CoordinatorLayout.LayoutParams) ((View) rootView.getParent()).getLayoutParams();
    mBehavior = new LockableBottomSheetBehavior();
    params.setBehavior(mBehavior);
    ((View) rootView.getParent()).setLayoutParams(params);
    if (mBehavior != null) {
      mBehavior.addBottomSheetCallback(mBottomSheetBehaviorCallback);
    }
  }
}