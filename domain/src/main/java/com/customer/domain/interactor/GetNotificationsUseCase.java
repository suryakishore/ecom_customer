package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.notification.NotificationListDetails;
import com.customer.domain.model.sellerlist.SellerListData;
import com.customer.domain.repository.GetNotificationsRepository;
import com.customer.domain.repository.GetSellerListRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetNotificationsUseCase extends
    UseCase<GetNotificationsUseCase.RequestValues, GetNotificationsUseCase.ResponseValues> {

  private GetNotificationsRepository mRepository;

  @Inject
  public GetNotificationsUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, GetNotificationsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getNotifications(requestValues.mFrom, requestValues.mTo);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mFrom;
    private String mTo;

    public RequestValues(String from, String to) {
      this.mFrom = from;
      this.mTo = to;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {

    private NotificationListDetails mData;

    public ResponseValues(NotificationListDetails data) {
      this.mData = data;
    }

    public NotificationListDetails getData() {
      return mData;
    }

    public void setData(NotificationListDetails data) {
      this.mData = data;
    }
  }

}
