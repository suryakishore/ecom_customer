package com.customer.fivecanale.landing.profile.profiledetails;

import static com.customer.fivecanale.util.EcomConstants.CAMERA_PERMISSION_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.CAMERA_PIC;
import static com.customer.fivecanale.util.EcomConstants.COUNTRY_CODE;
import static com.customer.fivecanale.util.EcomConstants.CROP_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GALLERY_PIC;
import static com.customer.fivecanale.util.EcomConstants.IMAGE_DATA;
import static com.customer.fivecanale.util.EcomConstants.NOUGHT;
import static com.customer.fivecanale.util.EcomConstants.PARENT_FOLDER;
import static com.customer.fivecanale.util.EcomConstants.PHOTO_PICKER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.PROVIDER_NAME;
import static com.customer.fivecanale.util.EcomConstants.RESETPASS;
import static com.customer.fivecanale.util.EcomConstants.RETURN_DATA;
import static com.customer.fivecanale.util.EcomConstants.STORAGE_PERMISSION_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_MOBILE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_NAME;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_VALUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.customer.domain.interactor.handler.PermissionHandler;
import com.customer.fivecanale.boarding.forgotpassword.EcomForgotPasswordActivity;
import com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateProfileActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.PermissionUtil;
import com.databinding.ActivityEcomProfileDetailsBinding;
import dagger.android.support.DaggerAppCompatActivity;
import eu.janmuller.android.simplecropimage.CropImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.inject.Inject;

/** Shows the user profile details */
public class ProfileDetailsActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  @Inject
  PermissionHandler mPermissionHandler;
  private ActivityEcomProfileDetailsBinding mBinding;
  private int mCountryCode;
  private Uri newProfileImageUri;
  private File newFile;
  private String state = Environment.getExternalStorageState();
  private ProfileDetailsViewModel profileViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initialize();
  }

  /** Initializes the profile details screen */
  private void initialize() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_ecom_profile_details);
    profileViewModel =
        ViewModelProviders.of(this, viewModelFactory).get(ProfileDetailsViewModel.class);
    mBinding.setViewmodel(profileViewModel);
    profileViewModel.subscribeProfileData();
    profileViewModel.getProfileDetails();
    profileViewModel.getImageUploadUrl().observe(this,
        url -> {
          RequestOptions requestOptions = new RequestOptions();
          requestOptions.placeholder(R.drawable.ecom_profile_holder);
          requestOptions.error(R.drawable.ecom_profile_holder);
          Glide.with(getApplicationContext()).load(url)
              .apply(RequestOptions.circleCropTransform().apply(requestOptions)).into(
              mBinding.ivProfileDetailsPic);
        });
    profileViewModel
        .getUserHandleLiveData()
        .observe(
            this,
            userData -> {
              if (!TextUtils.isEmpty(userData.getProfilePic())) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.ecom_profile_holder);
                requestOptions.error(R.drawable.ecom_profile_holder);
                Glide.with(this)
                    .load(userData.getProfilePic().replace(" ", "%20"))
                    .apply(RequestOptions.circleCropTransform().apply(requestOptions))
                    .listener(new RequestListener<Drawable>() {
                      @Override
                      public boolean onLoadFailed(@Nullable GlideException e, Object model,
                          Target<Drawable> target, boolean isFirstResource) {
                        profileViewModel.mIsShowProgress.set(FALSE);
                        return false;
                      }

                      @Override
                      public boolean onResourceReady(Drawable resource, Object model,
                          Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mBinding.pbProfilePick.setVisibility(View.GONE);
                        return false;
                      }
                    })
                    .into(mBinding.ivProfileDetailsPic)
                ;
              }
              mBinding.etProfileDetailsName.setText(userData.getName());
              mBinding.etProfileDetailsEMail.setText(userData.getMail());
              mBinding.etProfileDetailsMob.setText(userData.getPhoneNumber());
              if (!TextUtils.isEmpty(userData.getCountryCode())) {
                String countryCode = userData.getCountryCode().replace("+", "");
                countryCode = countryCode.replace(" ", "");
                if (!TextUtils.isEmpty(countryCode) && TextUtils.isDigitsOnly(countryCode)) {
                  mCountryCode = Integer.parseInt(countryCode);
                  mBinding.ccpGetNumber.setCountryForPhoneCode(mCountryCode);
                }
              }
            });
    mBinding.header.tvCenterCategoryName.setText(getResources().getString(R.string.myProfile));
    profileViewModel
        .getProfileUiActionLiveData()
        .observe(
            this,
            profileUiAction -> {
              Intent intent = new Intent(this, UpdateProfileActivity.class);
              switch (profileUiAction) {
                case NAME_CLICK:
                  intent.putExtra(
                      UPDATE_VALUE,
                      Objects.requireNonNull(mBinding.etProfileDetailsName.getText()).toString());
                  intent.putExtra(UPDATE_TYPE, UPDATE_NAME);
                  startActivity(intent);
                  break;
                case EMAIL_CLICK:
                  break;
                case MOBILE_CLICK:
                  intent.putExtra(
                      UPDATE_VALUE,
                      Objects.requireNonNull(mBinding.etProfileDetailsMob.getText()).toString());
                  intent.putExtra(UPDATE_TYPE, UPDATE_MOBILE);
                  intent.putExtra(COUNTRY_CODE, mCountryCode);
                  startActivity(intent);
                  break;
                case BACK_BUTTON:
                  finish();
                  break;
                case CHANGE_PASSWORD:
                  Intent forGotPassIntent = new Intent(this, EcomForgotPasswordActivity.class);
                  forGotPassIntent.putExtra(RESETPASS, TRUE);
                  startActivity(forGotPassIntent);
                  break;
              }
            });
  }

  public void onClick(View view) {
    selectImagePopUp();
  }

  /**
   * This method is using to open alert dialog which will give option for mode of photo selection
   */
  private void selectImagePopUp() {
    clearOrCreateDir();
    final CharSequence[] options = {getString(R.string.allTakePhoto), getString(
        R.string.allChooseFromGallery),
        getString(R.string.allCancel)};
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(getString(R.string.allAddPhoto));
    builder.setItems(options, (dialog, item) -> {
      if (options[item].equals(getString(R.string.allTakePhoto))) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
          PermissionUtil.requestPermission(this, CAMERA_PERMISSION_REQ_CODE, mPermissionHandler,
              Manifest.permission.CAMERA);
        } else {
          takePicFromCamera();
        }
      } else if (options[item].equals(getString(R.string.allChooseFromGallery))) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
          openGallery();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          PermissionUtil.requestPermission(this, STORAGE_PERMISSION_REQ_CODE, mPermissionHandler,
              Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
      } else if (options[item].equals(getString(R.string.allCancel))) {
        dialog.dismiss();
      }
    });
    builder.show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == STORAGE_PERMISSION_REQ_CODE
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      openGallery();
    } else if (requestCode == CAMERA_PERMISSION_REQ_CODE
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
          == PackageManager.PERMISSION_GRANTED) {
        takePicFromCamera();
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PermissionUtil.requestPermission(this, CAMERA_PERMISSION_REQ_CODE, mPermissionHandler,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);
      }
    }
  }

  /**
   * This method is using to open Gallery
   */
  private void openGallery() {
    try {
      Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
      photoPickerIntent.setType(PHOTO_PICKER_TYPE);
      startActivityForResult(photoPickerIntent, GALLERY_PIC);
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * creating new directory for storing the images
   */
  private void clearOrCreateDir() {
    try {
      state = Environment.getExternalStorageState();
      File cropImagesDir;
      File[] cropImagesDirectory;
      if (Environment.MEDIA_MOUNTED.equals(state)) {
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
          for (File directory : cropImagesDirectory) {
            directory.delete();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * calling camera action in this method
   */
  private void takePicFromCamera() {
    setPicPath();
    try {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, newProfileImageUri);
      intent.putExtra(RETURN_DATA, true);
      startActivityForResult(intent, CAMERA_PIC);
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    EcomUtil.printLog("got result");
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
          FileOutputStream fileOutputStream = new FileOutputStream(newFile);
          assert inputStream != null;
          EcomUtil.copyStream(inputStream, fileOutputStream);
          fileOutputStream.close();
          inputStream.close();
          newProfileImageUri = FileProvider.getUriForFile(
              this,
              this
                  .getPackageName() + PROVIDER_NAME, newFile);
          startCropImage();
        } catch (IOException | NullPointerException e) {
          e.printStackTrace();
          EcomUtil.printLog("exe" + "Exception First" + e.getMessage());
        }
        break;
      case CROP_IMAGE:
        if (data != null) {
          profileViewModel.updateImageToAws(newFile);
        }
        break;
      default:
        break;
    }
  }

  /**
   * Storing the Image in local file
   */
  private void setPicPath() {
    /**
     * Storing the Image in local file
     */
    EcomUtil.printLog("RegistrationAct Inside takePicFromCamera():");
    try {
      String takenNewImage = "";
      state = Environment.getExternalStorageState();
      takenNewImage = String.format("%s%d.png", PARENT_FOLDER, System.nanoTime());
      File newFile1;
      if (Environment.MEDIA_MOUNTED.equals(state)) {
        newFile1 = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            takenNewImage);
        newFile = newFile1;
      } else {
        newFile1 = new File(this.getFilesDir(), takenNewImage);
        newFile = newFile1;
      }
      Uri newProfileImageUri1;
      if (Build.VERSION.SDK_INT >= NOUGHT) {
        newProfileImageUri1 = FileProvider.getUriForFile(this,
            String.format("%s.provider", this.getApplicationContext().getPackageName()),
            newFile);
      } else {
        newProfileImageUri1 = Uri.fromFile(newFile);
      }
      newProfileImageUri = newProfileImageUri1;
      EcomUtil.printLog("RegistrationAct FilePAth in takePicFromCamera()  new: "
          + newFile.getPath() + " new profileUri = " + newProfileImageUri);
    } catch (ActivityNotFoundException e) {
      EcomUtil.printLog("RegistrationAct cannot take picture: " + e);
    }
  }

  /**
   * after getting Image from Camera starting crop activity
   */
  private void startCropImage() {
    Intent intent = new Intent(this, CropImage.class);
    intent.putExtra(CropImage.IMAGE_PATH, newFile.getPath());
    intent.putExtra(CropImage.SCALE, true);
    intent.putExtra(CropImage.ASPECT_X, FOUR);
    intent.putExtra(CropImage.ASPECT_Y, FOUR);
    startActivityForResult(intent, CROP_IMAGE);
  }
}