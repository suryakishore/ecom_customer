package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.notification.NotificationListDetails;
import com.customer.domain.model.webview.WebViewListData;
import com.customer.domain.model.webview.WebViewListDetails;
import com.customer.domain.repository.GetWebPageDataRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetWebPageDataUseCase extends
    UseCase<GetWebPageDataUseCase.RequestValues, GetWebPageDataUseCase.ResponseValues> {
  private GetWebPageDataRepository mRepository;

  @Inject
  public GetWebPageDataUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetWebPageDataRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getWebPageData();
  }

  public static class RequestValues implements UseCase.RequestValues {
    public RequestValues() {
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private WebViewListData mData;

    public ResponseValues(WebViewListData data) {
      this.mData = data;
    }

    public WebViewListData getData() {
      return mData;
    }

    public void setData(WebViewListData data) {
      this.mData = data;
    }
  }
}
