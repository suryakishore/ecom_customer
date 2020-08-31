package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.profile.ProfileData;
import com.customer.domain.repository.GetProfileDetailsRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetProfileDetailsUseCase extends
    UseCase<GetProfileDetailsUseCase.RequestValues, GetProfileDetailsUseCase.ResponseValues> {
  private GetProfileDetailsRepository mRepository;

  @Inject
  public GetProfileDetailsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetProfileDetailsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getProfileData();
  }

  public static class RequestValues implements UseCase.RequestValues {

    public RequestValues() {
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ProfileData mData;

    public ResponseValues(ProfileData data) {
      mData = data;
    }

    public ProfileData getData() {
      return mData;
    }

    public void setData(ProfileData data) {
      mData = data;
    }
  }
}
