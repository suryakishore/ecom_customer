package com.customer.fivecanale.mqtt_chat;

import static com.customer.fivecanale.util.EcomConstants.CAMERA_PERMISSION_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.CAMERA_PIC;
import static com.customer.fivecanale.util.EcomConstants.CROP_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.CUSTOMER_NAME;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GALLERY_PIC;
import static com.customer.fivecanale.util.EcomConstants.NOUGHT;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.PARENT_FOLDER;
import static com.customer.fivecanale.util.EcomConstants.PHOTO_PICKER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.PROVIDER_NAME;
import static com.customer.fivecanale.util.EcomConstants.STORAGE_PERMISSION_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.STORE_ID;
import static com.customer.fivecanale.util.EcomConstants.STORE_ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static eu.janmuller.android.simplecropimage.CropImage.RETURN_DATA;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.model.chat.GetChatData;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.manager.KeyboardDetector;
import com.customer.fivecanale.util.manager.KeyboardStatus;
import com.databinding.ActivityChattingBinding;
import dagger.android.support.DaggerAppCompatActivity;
import eu.janmuller.android.simplecropimage.CropImage;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;

/**
 * This class is used to show the chat data for a customer.
 */
public class ChattingActivity extends DaggerAppCompatActivity implements
    View.OnClickListener {
  static public boolean isOpen = false;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ArrayList<GetChatData> chatDataArry = new ArrayList<>();
  private ChattingAdapter mAdapter;
  private ActivityChattingBinding mChattingBinding;
  private Uri newProfileImageUri;
  private File newFile;
  private String state = Environment.getExternalStorageState();
  private ChatViewModel mChatViewModel;
  private String mStoreId, mStoreOrderId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mChattingBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatting);
    mChatViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        ChatViewModel.class);
    mChattingBinding.setViewmodel(mChatViewModel);
    mChattingBinding.ivChat.setOnClickListener(this::onClick);
    mChattingBinding.ivBackBtn.setOnClickListener(this::onClick);
    mChattingBinding.ivGallery.setOnClickListener(this::onClick);
    mChattingBinding.ivCamera.setOnClickListener(this::onClick);
    mStoreId = getIntent().getStringExtra(STORE_ID);
    mStoreOrderId = getIntent().getStringExtra(STORE_ORDER_ID);
    setActionBar(getIntent().getStringExtra(CUSTOMER_NAME));
    setRecyclerView();
    checkKeyboardStatus();
    subScribeChatImageData();
    subScribeChatData();
    mChatViewModel.callGetChatApi(getIntent().getStringExtra(ORDER_ID), String.valueOf(0),
        mStoreId);
  }

  /**
   * subscribe to chat data
   */
  private void subScribeChatData() {
    mChatViewModel.getChatData().observe(this, chatData -> {
      chatDataArry.clear();
      chatDataArry.addAll(chatData);
      mAdapter.notifyDataSetChanged();
      scrollToBottom();
    });
  }

  /**
   * <h2>checkKeyboardStatus</h2>
   * to check whether keyboard is open or close
   */
  private void checkKeyboardStatus() {
    new KeyboardDetector(this).observe().subscribe(new Observer<KeyboardStatus>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onNext(KeyboardStatus status) {
        boolean mKeyBoardStat = status == KeyboardStatus.OPENED;
        if (mKeyBoardStat) {
          scrollToBottom();
        }
        EcomUtil.printLog("exe" + "mKeyBoardStat" + mKeyBoardStat);
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
      }
    });
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ivChat:
        mChatViewModel.callPostMsgApi(mChattingBinding.etMsg.getText().toString(), mStoreId,
            mStoreOrderId, ONE);
        mChattingBinding.etMsg.setText("");
        break;
      case R.id.ivBackBtn:
        onBackPressed();
        break;
      case R.id.ivGallery:
        checkStoragePermission();
        break;
      case R.id.ivCamera:
        checkCameraPermission();
        break;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    isOpen = true;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    isOpen = false;
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  /**
   * Everytime when the screen opens it will show the bottom data
   */
  private void scrollToBottom() {
    mChattingBinding.rcvChatMsg.scrollToPosition(mAdapter.getItemCount() - 1);
  }

  public void setActionBar(String custName) {
    setSupportActionBar(mChattingBinding.toolbarChatting);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    mChattingBinding.tvchatproname.setText(custName);
  }

  public void setRecyclerView() {
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mAdapter = new ChattingAdapter(chatDataArry);
    mChattingBinding.rcvChatMsg.setLayoutManager(mLayoutManager);
    int resId = R.anim.layoutanimation_from_bottom;
    LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
    mChattingBinding.rcvChatMsg.setLayoutAnimation(animation);
    mChattingBinding.rcvChatMsg.setAdapter(mAdapter);
  }

  /**
   * subscribe to chat data
   */
  private void subScribeChatImageData() {
    mChatViewModel.getImageData().observe(this,
        imageUrl -> mChatViewModel.callPostMsgApi(imageUrl, mStoreId, mStoreOrderId, TWO));
  }

  private void checkCameraPermission() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(
              this, new String[]{Manifest.permission.CAMERA},
              CAMERA_PERMISSION_REQ_CODE);
    } else {
      takePicFromCamera();
    }
  }

  private void checkStoragePermission() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(
              this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
              STORAGE_PERMISSION_REQ_CODE);
    } else {
      openGallery();
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
          EcomUtil.printLog("exe" + " CROP_IMAGE newFile " + newFile);
          mChatViewModel.uploadImage(newFile);
        }
        break;
      default:
        break;
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
        ActivityCompat
            .requestPermissions(
                this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                CAMERA_PERMISSION_REQ_CODE);
      }
    }
  }
}
