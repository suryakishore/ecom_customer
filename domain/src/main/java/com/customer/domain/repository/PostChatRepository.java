package com.customer.domain.repository;

import com.customer.domain.interactor.GetCustomerChatUseCase;
import com.customer.domain.interactor.PostChatUseCase;
import io.reactivex.Single;

public interface PostChatRepository {

  Single<PostChatUseCase.ResponseValues> postChat(String msg, int type,String storeId,String storeOrderId);
}
