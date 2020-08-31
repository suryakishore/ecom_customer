package com.customer.fivecanale.uiutil.barcodescanning;

import static com.customer.fivecanale.uiutil.barcodescanning.BarCodeScannerUiAction.CLOSE;
import static com.customer.fivecanale.util.EcomConstants.CAMERA_PERMISSION_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.PermissionHandler;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.uiutil.barcodescanning.barcode.BarCodeCommunicator;
import com.customer.fivecanale.uiutil.barcodescanning.barcode.BarcodeScanningProcessor;
import com.customer.fivecanale.uiutil.barcodescanning.bottomsheet.ProductNotFoundBottomSheet;
import com.customer.fivecanale.uiutil.barcodescanning.camera.CameraSource;
import com.customer.fivecanale.uiutil.barcodescanning.camera.CameraSourcePreview;
import com.customer.fivecanale.uiutil.barcodescanning.graphic.GraphicOverlay;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.customer.fivecanale.util.PermissionUtil;
import com.databinding.ActivityLivePreviewBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.io.IOException;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/*
 * Purpose – This class is using for scanning the bar code using ML kit
 * @author 3Embed
 * Created on Jan 25, 2019
 * Modified on
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BarCodePreviewActivity extends DaggerAppCompatActivity
    implements ActivityCompat.OnRequestPermissionsResultCallback,
    AdapterView.OnItemSelectedListener, CustomDialogUtil.SimpleAlertDialogClickHandler,
    CompoundButton.OnCheckedChangeListener, BarCodeCommunicator {
  private static final String BARCODE_DETECTION = "Barcode Detection";
  private CameraSource mCameraSource = null;
  private CameraSourcePreview mSourcePreview;
  private GraphicOverlay mGraphicOverlay;
  private boolean mIsApiFired = false;
  private ActivityLivePreviewBinding mBinding;

  @Inject
  PermissionHandler mPermissionHandler;
  @Inject
  ProductNotFoundBottomSheet mNotFoundBottomSheet;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private BarCodeViewModel mViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_live_preview);
    initialize();
  }

  /**
   * This method is using to initialize all the basic resources
   */
  private void initialize() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        BarCodeViewModel.class);
    mSourcePreview = mBinding.firePreview;
    mGraphicOverlay = mBinding.fireFaceOverlay;
    mBinding.ivBackBtn.setOnClickListener(view -> finish());
    allPermissionsGranted();

    mViewModel.getBarCodeMutableLiveData().observe(this, data -> {
      if (data.getIsSuccess()) {
        Intent intent = new Intent(BarCodePreviewActivity.this, ProductDetailsActivity.class);
        intent.putExtra(PARENT_PRODUCT_ID, data.getParentProductId());
        intent.putExtra(PRODUCT_ID, data.getChildProductId());
        startActivity(intent);
        finish();
      } else {
        mNotFoundBottomSheet.setCancelable(false);
        mNotFoundBottomSheet.show(getSupportFragmentManager(), mNotFoundBottomSheet.getTag());
      }
    });
    mViewModel.getUiActionMutableLiveData().observe(this, barCodeScannerUiAction -> {
      if (barCodeScannerUiAction == CLOSE) {
        onBackPressed();
      } else {
        mIsApiFired = false;
      }
    });
  }

  @Override
  public synchronized void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    mSourcePreview.stop();

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_DENIED) {
      createCameraSource(BARCODE_DETECTION);
      startCameraSource();
    } else {
      allPermissionsGranted();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing.
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    if (mCameraSource != null) {
      mCameraSource.setFacing(
          isChecked ? CameraSource.CAMERA_FACING_FRONT : CameraSource.CAMERA_FACING_BACK);
    }
    mSourcePreview.stop();
    startCameraSource();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return true;
  }

  private void createCameraSource(String model) {
    if (mCameraSource == null) {
      mCameraSource = new CameraSource(this, mGraphicOverlay);
    }
    try {
      if (BARCODE_DETECTION.equals(model)) {
        mCameraSource.setMachineLearningFrameProcessor(new BarcodeScanningProcessor(this));
      }
    } catch (Exception e) {
      Toast.makeText(
          getApplicationContext(),
          "Can not create image processor: " + e.getMessage(),
          Toast.LENGTH_LONG)
          .show();
    }
  }

  /**
   * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
   * (e.g., because onResume was called before the camera source was created), this will be called
   * again when the camera source is created.
   */
  private void startCameraSource() {

    if (mCameraSource != null) {
      try {
        mSourcePreview.start(mCameraSource, mGraphicOverlay);
      } catch (IOException e) {
        mCameraSource.release();
        mCameraSource = null;
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    startCameraSource();
  }

  /**
   * Stops the camera.
   */
  @Override
  protected void onPause() {
    super.onPause();
    mSourcePreview.stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mCameraSource != null) {
      mCameraSource.release();
    }
  }

  private void allPermissionsGranted() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
        Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
      PermissionUtil.requestPermission(this, CAMERA_PERMISSION_REQ_CODE, mPermissionHandler,
          Manifest.permission.CAMERA);
    } else {
      createCameraSource(BARCODE_DETECTION);
    }
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NotNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == CAMERA_PERMISSION_REQ_CODE
        && grantResults[ZERO] == PackageManager.PERMISSION_GRANTED) {
      createCameraSource(BARCODE_DETECTION);
    } else {
      finish();
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public void onSuccessFullBarCodeRead(String barCode) {

    if (!mIsApiFired && !TextUtils.isEmpty(barCode)) {
      mViewModel.getProductDetails(barCode);
      mIsApiFired = true;
    }
  }

  @Override
  public void onOkClickListener(int type) {
    finish();
  }
}