package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.chat.GetChatDetails;
import com.customer.domain.repository.GetCustomerChatRepository;
import com.customer.domain.repository.PostChatRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class PostChatUseCase extends
    UseCase<PostChatUseCase.RequestValues, PostChatUseCase.ResponseValues> {
  private PostChatRepository mRepository;

  @Inject
  public PostChatUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      PostChatRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.postChat(requestValues.msg, requestValues.type,requestValues.storeId,requestValues.storeOrderId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String msg;
    private String storeId;
    private String storeOrderId;
    private int type;

    public RequestValues(String msg, int type,String storeId, String storeOrderId) {
      this.msg = msg;
      this.type = type;
      this.storeId=storeId;
      this.storeOrderId=storeOrderId;
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
