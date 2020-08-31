package com.customer.domain.repository;

import com.customer.domain.interactor.GetNotificationsUseCase;
import com.customer.domain.interactor.GetWebPageDataUseCase;
import io.reactivex.Single;

public interface GetWebPageDataRepository {

  Single<GetWebPageDataUseCase.ResponseValues> getWebPageData( );
}
