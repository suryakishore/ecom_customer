package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.BarCodeResponseData;
import com.customer.domain.repository.GetProductOnQrCodeRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetProductOnQrCodeUseCase extends
    UseCase<GetProductOnQrCodeUseCase.RequestValues, GetProductOnQrCodeUseCase.ResponseValues> {

  private GetProductOnQrCodeRepository mRepository;

  @Inject
  public GetProductOnQrCodeUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetProductOnQrCodeRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getProductOnQrCode(requestValues.mQrCode);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mQrCode;

    public RequestValues(String qrCode) {
      this.mQrCode = qrCode;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private BarCodeResponseData mData;

    public ResponseValues(BarCodeResponseData data) {
      this.mData = data;
    }

    public BarCodeResponseData getData() {
      return mData;
    }

    public void setData(BarCodeResponseData data) {
      this.mData = data;
    }
  }
}
