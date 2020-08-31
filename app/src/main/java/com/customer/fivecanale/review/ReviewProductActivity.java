package com.customer.fivecanale.review;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.customer.fivecanale.util.EcomConstants.CAMERA_ITEM;
import static com.customer.fivecanale.util.EcomConstants.CAMERA_PIC;
import static com.customer.fivecanale.util.EcomConstants.CROP_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GALLERY_PIC;
import static com.customer.fivecanale.util.EcomConstants.IMAGE;
import static com.customer.fivecanale.util.EcomConstants.IMAGE_DATA;
import static com.customer.fivecanale.util.EcomConstants.IMAGE_SELECT;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PARENT_FOLDER;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PNG;
import static com.customer.fivecanale.util.EcomConstants.PRICE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_NAME;
import static com.customer.fivecanale.util.EcomConstants.PROVIDER;
import static com.customer.fivecanale.util.EcomConstants.RATE_PRODUCT;
import static com.customer.fivecanale.util.EcomConstants.RATE_SELLER;
import static com.customer.fivecanale.util.EcomConstants.RATING;
import static com.customer.fivecanale.util.EcomConstants.REQUEST_CODE_PERMISSION_MULTIPLE;
import static com.customer.fivecanale.util.EcomConstants.RETURN_DATA;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.getratable.RatableAttributesData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityReviewProductBinding;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.DaggerAppCompatActivity;
import eu.janmuller.android.simplecropimage.CropImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * This activity is used for giving the rating and reviews.
 */
public class ReviewProductActivity extends DaggerAppCompatActivity implements
    ReviewProductCameraClick, RatingOrReviewCallBack {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  ArrayList<RatableAttributesData> mRatingAttributesList = new ArrayList<>();
  ArrayList<RatableAttributesData> mSellerRatingAttributesList = new ArrayList<>();
  private ActivityReviewProductBinding mBinding;
  private ReviewProductViewModel mReviewProductViewModel;
  private String mState = Environment.getExternalStorageState();
  private Uri mNewProfileImageUri;
  private File mNewFile;
  private ArrayList<String> mReviewImagesList = new ArrayList<>();
  private ReviewProductsAdapter mReviewProductsAdapter;
  private RatingTypesAdapter mRatingTypesAdapter;
  private RatingTypesAdapter mSellerRatingTypesAdapter;
  private String mParentProductId;
  private ArrayList<File> mFiles = new ArrayList<>();
  private double mRating;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeToUiActions();
    subscribeAttributesData();
    subscribeSellerRatingData();
    subscribeRatingData();
    subscribeShopNowData();
    getIntentData();
    subscribeOnError();
    initializeRatingAdapter();
    initializeSellerRatingAdapter();
    initializeReviewAdapter();
    mReviewProductViewModel.callRatingAttributesApi(mParentProductId);
  }

  /**
   * Initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review_product);
  }

  /**
   * this method is to register for the mError message.
   */
  private void subscribeOnError() {
    mReviewProductViewModel.onError().observe(this, this::showError);
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showError(String error) {
    Snackbar.make(mBinding.clReviewData, error, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mReviewProductViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        ReviewProductViewModel.class);
    mBinding.setViewModel(mReviewProductViewModel);
    mReviewProductViewModel.getDeviceDetails(EcomUtil.getIpAddress(this));
  }

  /**
   * get the intent mData
   */
  private void getIntentData() {
    Intent bundle = getIntent();
    if (bundle != null) {
      String productName = bundle.getStringExtra(PRODUCT_NAME);
      String productPrice = bundle.getStringExtra(PRICE);
      String productImage = bundle.getStringExtra(PRODUCT_IMAGE);
      String productColor = bundle.getStringExtra(PRODUCT_COLOUR);
      mParentProductId = bundle.getStringExtra(PARENT_PRODUCT_ID);
      mReviewProductViewModel.productName.set(productName);
      mReviewProductViewModel.productImage.set(productImage);
      mReviewProductViewModel.productPrice.set(productPrice);
      if (productColor != null && !productColor.isEmpty()) {
        mReviewProductViewModel.productColor.set(productColor);
      } else {
        mBinding.tvReviewProColor.setVisibility(View.GONE);
      }
    }
  }

  /**
   * Listen this method when ui clicked
   */
  private void subscribeToUiActions() {
    mReviewProductViewModel.onClick().observe(this, reviewProductUiAction -> {
      switch (reviewProductUiAction) {
        case FINISH_ACT:
          clearOrCreateDir();
          Intent intent = new Intent();
          intent.putExtra(RATING, mRating);
          setResult(Activity.RESULT_OK, intent);
          finish();
          break;
        case SUBMIT:
          if (mReviewImagesList != null && mReviewImagesList.contains(CAMERA_ITEM)) {
            mReviewImagesList.remove(CAMERA_ITEM);
            EcomUtil.printLog("mReviewImagesList" + mReviewImagesList.size());
          }
          mReviewProductViewModel.reviewImgFiles(mReviewImagesList, mFiles, mParentProductId);
          break;
        default:
          break;
      }
    });
  }

  /**
   * initialize Values for rating
   */
  private void initializeRatingAdapter() {
    mRatingTypesAdapter = new RatingTypesAdapter(mRatingAttributesList, this, RATE_PRODUCT);
    mBinding.rvReviewProRatings.setHasFixedSize(TRUE);
    mBinding.rvReviewProRatings.setAdapter(mRatingTypesAdapter);
  }

  /**
   * initialize Values for rating
   */
  private void initializeSellerRatingAdapter() {
    mSellerRatingTypesAdapter = new RatingTypesAdapter(mSellerRatingAttributesList, this,
        RATE_SELLER);
    mBinding.rvRateSeller.setHasFixedSize(TRUE);
    mBinding.rvRateSeller.setAdapter(mSellerRatingTypesAdapter);
  }

  /**
   * initialize Values for reviews
   */
  private void initializeReviewAdapter() {
    mReviewImagesList.clear();
    mReviewImagesList.add(CAMERA_ITEM);
    mReviewProductsAdapter = new ReviewProductsAdapter(mReviewImagesList, this);
    mBinding.rvReviewProImages.setHasFixedSize(TRUE);
    mBinding.rvReviewProImages.setAdapter(mReviewProductsAdapter);
  }

  /**
   * subscribes for rating attribute mData
   */
  private void subscribeAttributesData() {
    mReviewProductViewModel.getAttributesList().observe(this,
        ratableAttributesData -> {
          mRatingAttributesList.clear();
          RatableAttributesData overallAttributesData = new RatableAttributesData(
              getResources().getString(R.string.reviewProductOverall), "", ZERO);
          mRatingAttributesList.add(ZERO, overallAttributesData);
          mRatingAttributesList.addAll(ratableAttributesData);
          mRatingTypesAdapter.notifyDataSetChanged();
        });
  }

  /**
   * subscribes for rating attribute mData
   */
  private void subscribeSellerRatingData() {
    mReviewProductViewModel.getSellerAttributesList().observe(this,
        ratableAttributesData -> {
          mSellerRatingAttributesList.clear();
          mSellerRatingAttributesList.addAll(ratableAttributesData);
          mSellerRatingTypesAdapter.notifyDataSetChanged();
        });
  }

  /**
   * subscribe for rating mData
   */
  private void subscribeRatingData() {
    mReviewProductViewModel.getUserReviewData().observe(this, userReviewData -> {
      EcomUtil.printLog("userReviewData" + userReviewData);
      if (userReviewData != null) {
        if (userReviewData.getImage() != null) {
          mReviewImagesList.clear();
          if (userReviewData.getImage().size() <= FOUR) {
            mReviewImagesList.addAll(userReviewData.getImage());
            mReviewImagesList.add(CAMERA_ITEM);
          } else {
            mReviewImagesList.addAll(userReviewData.getImage());
          }
          mReviewProductsAdapter.notifyDataSetChanged();
        }
        if (userReviewData.getReviewDescription() != null) {
          mBinding.etReviewProDesc.setText(userReviewData.getReviewDescription());
        }
        if (userReviewData.getReviewTitle() != null) {
          mBinding.etReviewProTitle.setText(userReviewData.getReviewTitle());
        }
        if (userReviewData.getRating() != null) {
          if (mRatingAttributesList.size() > ZERO) {
            RatableAttributesData ratableAttributesData = mRatingAttributesList.get(ZERO);
            ratableAttributesData.setAttributeRating(
                Double.parseDouble(userReviewData.getRating()));
            mReviewProductViewModel.attributeId = TRUE;
          }
          mReviewProductViewModel.btnEnabled.set(mReviewProductViewModel.attributeId
              && !Objects.requireNonNull(mBinding.etReviewProTitle.getText()).toString().isEmpty()
              && !Objects.requireNonNull(
              mBinding.etReviewProDesc.getText()).toString().isEmpty());
        }
      }
    });
  }

  /**
   * subscribe for shop now mData
   */
  private void subscribeShopNowData() {
    mReviewProductViewModel.onShopNow().observe(this, shopNow -> {
      if (shopNow) {
        mBinding.includeReviewProPurchase.clShopNow.setVisibility(View.VISIBLE);
      }
    });
  }

  /**
   * check permission camera,read external storage
   */
  private void checkPerMission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ((this.checkSelfPermission(CAMERA)
        != PackageManager.PERMISSION_GRANTED) || (
        checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        || (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))) {
      requestPermissions(new String[]{CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
          REQUEST_CODE_PERMISSION_MULTIPLE);
    } else {
      selectImagePopUp();
    }
  }

  /**
   * select the image source
   */
  private void selectImagePopUp() {
    final CharSequence[] options = {getString(R.string.allTakePhoto), getString(
        R.string.allChooseFromGallery),
        getString(R.string.allCancel)};
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(getString(R.string.allAddPhoto));
    builder.setItems(options, (dialog, item) -> {
      if (options[item].equals(getString(R.string.allTakePhoto))) {
        takePicFromCamera();
      } else if (options[item].equals(getString(R.string.allChooseFromGallery))) {
        try {
          Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
          photoPickerIntent.setType(IMAGE_SELECT);
          startActivityForResult(photoPickerIntent, GALLERY_PIC);
        } catch (ActivityNotFoundException e) {
          e.printStackTrace();
        }
      } else if (options[item].equals(getString(R.string.allCancel))) {
        dialog.dismiss();
      }
    });
    builder.show();
  }

  /**
   * clear all caches
   */
  private void clearOrCreateDir() {
    try {
      mState = Environment.getExternalStorageState();
      File cropImagesDir;
      File[] cropImagesDirectory;
      if (Environment.MEDIA_MOUNTED.equals(mState)) {
        cropImagesDir = new File(
            String.format("%s/%s%s", Environment.getExternalStorageDirectory(), PARENT_FOLDER,
                IMAGE_DATA));
      } else {
        cropImagesDir = new File(
            String.format("%s/%s%s", this.getFilesDir(), PARENT_FOLDER, IMAGE_DATA));
      }
      if (!cropImagesDir.isDirectory()) {
        cropImagesDir.mkdirs();
      } else {
        cropImagesDirectory = cropImagesDir.listFiles();
        if (cropImagesDirectory.length > ZERO) {
          for (File imagesDirectory : cropImagesDirectory) {
            imagesDirectory.delete();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * sued to open the camera
   */
  private void takePicFromCamera() {
    setPicPath();
    try {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, mNewProfileImageUri);
      intent.putExtra(RETURN_DATA, TRUE);
      startActivityForResult(intent, CAMERA_PIC);
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * create the folder for the image which we have selected.
   */
  private void setPicPath() {
    String takenNewImage;
    mState = Environment.getExternalStorageState();
    takenNewImage = IMAGE + System.nanoTime() + PNG;
    if (Environment.MEDIA_MOUNTED.equals(mState)) {
      mNewFile = new File(
          String.format("%s/%s%s", Environment.getExternalStorageDirectory(), PARENT_FOLDER,
              IMAGE_DATA), takenNewImage);
      mNewProfileImageUri = FileProvider.getUriForFile(
          this,
          getPackageName() + PROVIDER, mNewFile);
    } else {
      mNewFile = new File(
          String.format("%s/%s%s", this.getFilesDir(), PARENT_FOLDER, IMAGE_DATA), takenNewImage);
      mNewProfileImageUri = FileProvider.getUriForFile(
          this,
          getPackageName() + PROVIDER, mNewFile);
    }
  }

  /**
   * open the crop image activity
   */
  private void startCropImage() {
    try {
      Intent intent = new Intent(this, CropImage.class);
      intent.putExtra(CropImage.IMAGE_PATH, mNewFile.getPath());
      intent.putExtra(CropImage.SCALE, TRUE);
      intent.putExtra(CropImage.ASPECT_X, FOUR);
      intent.putExtra(CropImage.ASPECT_Y, FOUR);
      startActivityForResult(intent, CROP_IMAGE);
    } catch (Exception e) {
      e.printStackTrace();
      Toast.makeText(this, "Unable to load this image", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onCameraClick() {
    checkPerMission();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    EcomUtil.printLog("gotresult" + requestCode);
    if (resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case CAMERA_PIC:
          startCropImage();
          break;
        case GALLERY_PIC:
          setPicPath();
          InputStream inputStream;
          try {
            inputStream = this.getContentResolver().openInputStream(
                Objects.requireNonNull(data.getData()));
            FileOutputStream fileOutputStream = new FileOutputStream(mNewFile);
            assert inputStream != null;
            EcomUtil.copyStream(inputStream, fileOutputStream);
            fileOutputStream.close();
            inputStream.close();
            mNewProfileImageUri = FileProvider.getUriForFile(
                this,
                String.format("%s%s", getPackageName(), PROVIDER), mNewFile);
            startCropImage();
          } catch (IOException | NullPointerException e) {
            e.printStackTrace();
          }
          break;
        case CROP_IMAGE:
          if (data != null) {
            mFiles.add(mNewFile);
            mReviewImagesList.add(mReviewImagesList.size() - ONE, mNewFile.toString());
            mReviewProductsAdapter.notifyItemChanged(mReviewImagesList.size());
            if (mReviewImagesList.size() == SIX) {
              mReviewImagesList.remove(mReviewImagesList.size() - ONE);
              mReviewProductsAdapter.notifyItemChanged(mReviewImagesList.size() - ONE);
            }
          }
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
      @NotNull String[] permissions, @NotNull int[] grantResults) {
    switch (requestCode) {
      case REQUEST_CODE_PERMISSION_MULTIPLE: {
        checkPerMission();
      }
    }
  }

  @Override
  public void ratingOrReview(int ratingType, int reviewType, String attributeId, double rating) {
    if (attributeId.isEmpty()) {
      mReviewProductViewModel.attributeId = TRUE;
      mRating = rating;
      EcomUtil.printLog("exe" + "mRating" + mRating);
    }
    if (mReviewProductViewModel.attributeId
        && !Objects.requireNonNull(mBinding.etReviewProDesc.getText()).toString().isEmpty()
        && !Objects.requireNonNull(mBinding.etReviewProTitle.getText()).toString().isEmpty()) {
      mBinding.tvReviewProSubmit.setEnabled(TRUE);
    }
    mReviewProductViewModel.callRatingsOrReviewsApi(ratingType, reviewType, mParentProductId,
        attributeId, "",
        "",
        rating,
        null);
  }
}