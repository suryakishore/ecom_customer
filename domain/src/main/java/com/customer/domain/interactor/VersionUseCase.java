package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.SignInData;
import com.customer.domain.model.version.VersionData;
import com.customer.domain.repository.VersionRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class VersionUseCase
    extends UseCase<VersionUseCase.RequestValues, VersionUseCase.ResponseValues> {
  private VersionRepository mRepository;

  @Inject
  public VersionUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
      VersionRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<VersionUseCase.ResponseValues> buildUseCaseObservable(
      RequestValues requestValues) {
    return mRepository.versionUpdate(requestValues.mType,requestValues.mVersion);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int mType;
    private String mVersion;

    public RequestValues(int type, String version) {
      this.mType = type;
      this.mVersion = version;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private VersionData mData;

    public ResponseValues(VersionData data) {
      this.mData = data;
    }

    public VersionData getData() {
      return mData;
    }

    public void setData(VersionData data) {
      this.mData = data;
    }
  }
}
