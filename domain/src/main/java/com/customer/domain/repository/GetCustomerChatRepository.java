package com.customer.domain.repository;

import com.customer.domain.interactor.CancelOrderUseCase;
import com.customer.domain.interactor.GetCustomerChatUseCase;
import io.reactivex.Single;

public interface GetCustomerChatRepository {

  Single<GetCustomerChatUseCase.ResponseValues> getChat(String bookingId,String pageNo);
}
