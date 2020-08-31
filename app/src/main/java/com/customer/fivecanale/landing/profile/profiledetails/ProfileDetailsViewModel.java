package com.customer.fivecanale.landing.profile.profiledetails;

import static com.customer.fivecanale.landing.profile.profiledetails.ProfileClickActions.CHANGE_PASSWORD;
import static com.customer.fivecanale.util.EcomConstants.FALSE;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.BuildConfig;
import com.R;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetProfileDetailsUseCase;
import com.customer.domain.interactor.UpdateProfileUseCase;
import com.customer.domain.interactor.UploadImageUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.UserData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import javax.inject.Inject;

/** Adds the login for profile details screen */
public class ProfileDetailsViewModel extends ViewModel implements BackButtonClickListener {
  @Inject
  public UserInfoHandler userInfoHandler;
  public ObservableField<Boolean> mIsShowProgress = new ObservableField<>(false);
  private UseCaseHandler mHandler;
  private GetProfileDetailsUseCase mDetailsUseCase;
  private UpdateProfileUseCase mProfileUseCase;
  private MutableLiveData<UserData> mUserLiveData = new MutableLiveData<>();
  private MutableLiveData<String> mImageUpload = new MutableLiveData<>();
  private MutableLiveData<ProfileClickActions> mutableLiveData = new MutableLiveData<>();
  private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
  private UploadImageUseCase mUploadImageUseCase;

  @Inject
  public ProfileDetailsViewModel(GetProfileDetailsUseCase detailsUseCase,
      UpdateProfileUseCase updateProfileUseCase, UseCaseHandler handler,
      UploadImageUseCase uploadImageUseCase) {
    this.mHandler = handler;
    mDetailsUseCase = detailsUseCase;
    this.mProfileUseCase = updateProfileUseCase;
    this.mUploadImageUseCase = uploadImageUseCase;
  }

  public void onHandleClick(View view) {
    switch (view.getId()) {
      case R.id.etProfileDetailsName:
      case R.id.tilProfileDetailsName:
        mutableLiveData.setValue(ProfileClickActions.NAME_CLICK);
        break;
      case R.id.etProfileDetailsMob:
      case R.id.tilProfileDetailsMob:
        mutableLiveData.setValue(ProfileClickActions.MOBILE_CLICK);
        break;
      case R.id.tilProfileDetailsEMail:
      case R.id.etProfileDetailsEMail:
        mutableLiveData.setValue(ProfileClickActions.EMAIL_CLICK);
        break;
      case R.id.tvProfileDetailsChangePass:
        mutableLiveData.setValue(CHANGE_PASSWORD);
        break;
    }
  }

  /** subscribe the profile mData changes */
  void subscribeProfileData() {
    mCompositeDisposable.add(
        userInfoHandler
            .getUserDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                userData -> {
                  mUserLiveData.postValue(userData);
                }));
    userInfoHandler.getUserData();
  }

  /**
   * Returns the user profile mData live mData object
   *
   * @return live mData object
   */
  MutableLiveData<UserData> getUserHandleLiveData() {
    return mUserLiveData;
  }

  /**
   * Returns the user profile mData click event live mData object
   *
   * @return live mData object
   */
  MutableLiveData<ProfileClickActions> getProfileUiActionLiveData() {
    return mutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mutableLiveData.setValue(ProfileClickActions.BACK_BUTTON);
  }

  /**
   * This method is using to get Profile Details
   */
  void getProfileDetails() {
    DisposableSingleObserver<GetProfileDetailsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetProfileDetailsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetProfileDetailsUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("ProfileDet Success");
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("ProfileDet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mDetailsUseCase, new GetProfileDetailsUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * This method is using to upload image
   */
  private void uploadImage(File file, String fileName) {
    DisposableSingleObserver<UploadImageUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UploadImageUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UploadImageUseCase.ResponseValues responseValues) {
            EcomUtil.printLog(
                "ProfileDet Success" + responseValues.getData().getImageUrl());
            updateProfilePick(responseValues.getData().getImageUrl());
          }

          @Override
          public void onError(Throwable e) {
            mIsShowProgress.set(FALSE);
            EcomUtil.printLog("ProfileDet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mUploadImageUseCase,
        new UploadImageUseCase.RequestValues(BuildConfig.BUCKET_NAME, file, fileName),
        disposableSingleObserver);
  }

  /**
   * this method is using to get Image url Mutable live data
   *
   * @return Image URL provider live data object
   */
  MutableLiveData<String> getImageUploadUrl() {
    return mImageUpload;
  }

  /**
   * Making APi call to update the user profile pick
   *
   * @param profilePick profile pick url
   */
  private void updateProfilePick(String profilePick) {
    mIsShowProgress.set(true);
    DisposableSingleObserver<UpdateProfileUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UpdateProfileUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UpdateProfileUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("UpdateProfile Success");
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("UpdateProfile Fail" + e.getMessage());
            mIsShowProgress.set(FALSE);
          }
        };
    mHandler.execute(
        mProfileUseCase,
        new UpdateProfileUseCase.RequestValues(profilePick),
        disposableSingleObserver);
  }

  /**
   * This method is using to upload image to AWS
   *
   * @param file file path to upload
   */
  void updateImageToAws(File file) {
    EcomUtil.printLog("exe" + "file" + file);
    mIsShowProgress.set(true);
    uploadImage(file, "" + System.currentTimeMillis());
  }
}