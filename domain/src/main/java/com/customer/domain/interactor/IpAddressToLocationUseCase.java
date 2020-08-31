package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.location.IpAddressToLocationData;
import com.customer.domain.repository.IpAddressToLocationRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class IpAddressToLocationUseCase extends
    UseCase<IpAddressToLocationUseCase.RequestValues, IpAddressToLocationUseCase.ResponseValues> {

  private IpAddressToLocationRepository mRepository;

  @Inject
  public IpAddressToLocationUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, IpAddressToLocationRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.ipAddressToLocation(requestValues.mIpAddress);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mIpAddress;

    public RequestValues(String ipAddress) {
      mIpAddress = ipAddress;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private IpAddressToLocationData mData;

    public ResponseValues(IpAddressToLocationData data) {
      mData = data;
    }

    public IpAddressToLocationData getData() {
      return mData;
    }

    public void setData(IpAddressToLocationData data) {
      mData = data;
    }
  }
}
