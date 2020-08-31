package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.uploadimage.UploadImageItemData;
import com.customer.domain.repository.UploadImageRepository;
import io.reactivex.Single;
import java.io.File;
import javax.inject.Inject;

public class UploadImageUseCase extends
    UseCase<UploadImageUseCase.RequestValues, UploadImageUseCase.ResponseValues> {
  private UploadImageRepository mRepository;

  @Inject
  public UploadImageUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      UploadImageRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.uploadImage(requestValues.folder, requestValues.file,
        requestValues.fileName);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String folder;
    private File file;
    private String fileName;

    public RequestValues(String folder, File file, String fileName) {
      this.folder = folder;
      this.file = file;
      this.fileName = fileName;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private UploadImageItemData mData;

    public ResponseValues(UploadImageItemData uploadImageData) {
      mData = uploadImageData;
    }

    public UploadImageItemData getData() {
      return mData;
    }

    public void setData(UploadImageItemData data) {
      mData = data;
    }
  }
}
