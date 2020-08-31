package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.MakeAddressDefaultRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class MakeAddressDefaultUseCase extends
    UseCase<MakeAddressDefaultUseCase.RequestValues, MakeAddressDefaultUseCase.ResponseValues> {
  private MakeAddressDefaultRepository mRepository;

  @Inject
  public MakeAddressDefaultUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      MakeAddressDefaultRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.makeAddressDefault(requestValues.mAddressId);
  }

  public static class RequestValues implements UseCase.RequestValues {

    private String mAddressId;

    public RequestValues(String addressId) {
      this.mAddressId = addressId;
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
