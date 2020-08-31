package com.customer.domain.interactor;

import android.text.TextUtils;
import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.UpdateProfileRepository;
import io.reactivex.Single;
import java.io.File;
import javax.inject.Inject;

public class UpdateProfileUseCase extends
    UseCase<UpdateProfileUseCase.RequestValues, UpdateProfileUseCase.ResponseValues> {

  private UpdateProfileRepository mRepository;

  @Inject
  public UpdateProfileUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      UpdateProfileRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    if (!TextUtils.isEmpty(requestValues.mProfilePic)) {
      return mRepository.updateProfilePic(requestValues.mProfilePic);
    }
    return mRepository.updateProfile(
        requestValues.mFirstName, requestValues.mLastName,
        requestValues.mCountryCode, requestValues.mMobile, requestValues.mEmail
    );
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mFirstName;
    private String mLastName;
    private String mMobile;
    private String mEmail;
    private String mCountryCode;
    private String mProfilePic;


    public RequestValues(String firstName, String lastName, String countryCode, String mobile,
        String email) {
      this.mFirstName = firstName;
      this.mLastName = lastName;
      this.mMobile = mobile;
      this.mEmail = email;
      this.mCountryCode = countryCode;
    }


    public RequestValues(String profilePic) {
      this.mProfilePic = profilePic;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CommonData mData;

    public ResponseValues(CommonData data) {
      this.mData = data;
    }

    public CommonData getData() {
      return mData;
    }

    public void setData(CommonData data) {
      this.mData = data;
    }
  }
}
