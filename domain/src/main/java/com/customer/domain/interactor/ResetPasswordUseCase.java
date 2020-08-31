package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.ResetPasswordRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ResetPasswordUseCase extends
    UseCase<ResetPasswordUseCase.RequestValues, ResetPasswordUseCase.ResponseValues> {

  private ResetPasswordRepository mRepository;

  @Inject
  public ResetPasswordUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      ResetPasswordRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.resetPassword(requestValues.mNewPassword, requestValues.mOldPassword,
        requestValues.mResetType);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mNewPassword;
    private String mOldPassword;
    private int mResetType;

    public RequestValues(String newPassword, String oldPassword, int resetType) {
      this.mNewPassword = newPassword;
      this.mOldPassword = oldPassword;
      this.mResetType = resetType;
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
