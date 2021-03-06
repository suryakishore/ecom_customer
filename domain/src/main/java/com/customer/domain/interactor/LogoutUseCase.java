package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.LogoutRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class LogoutUseCase extends
    UseCase<LogoutUseCase.RequestValues, LogoutUseCase.ResponseValues> {

  private LogoutRepository mRepository;

  @Inject
  public LogoutUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      LogoutRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.logout(requestValues.mIpAddress, requestValues.mLatitude,
        requestValues.mLongitude);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mIpAddress;
    private double mLatitude;
    private double mLongitude;

    public RequestValues(String ipAddress, double latitude, double longitude) {
      this.mIpAddress = ipAddress;
      this.mLatitude = latitude;
      this.mLongitude = longitude;
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
