package com.customer.domain.repository;

import com.customer.domain.interactor.GetNotificationsUseCase;
import com.customer.domain.interactor.GetSellerListUseCase;
import io.reactivex.Single;

public interface GetNotificationsRepository {

  Single<GetNotificationsUseCase.ResponseValues> getNotifications(String from, String to);
}
