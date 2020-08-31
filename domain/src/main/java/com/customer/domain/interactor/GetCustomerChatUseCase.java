package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.chat.GetChatDetails;
import com.customer.domain.repository.GetCustomerChatRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetCustomerChatUseCase extends
    UseCase<GetCustomerChatUseCase.RequestValues, GetCustomerChatUseCase.ResponseValues> {
  private GetCustomerChatRepository mRepository;

  @Inject
  public GetCustomerChatUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      GetCustomerChatRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.getChat(requestValues.bookingId, requestValues.pageNo);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String bookingId;
    private String pageNo;

    public RequestValues(String bookingId, String pageNo) {
      this.bookingId = bookingId;
      this.pageNo = pageNo;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private GetChatDetails mData;

    public ResponseValues(GetChatDetails data) {
      this.mData = data;
    }

    public GetChatDetails getData() {
      return mData;
    }

    public void setData(GetChatDetails data) {
      this.mData = data;
    }
  }
}
