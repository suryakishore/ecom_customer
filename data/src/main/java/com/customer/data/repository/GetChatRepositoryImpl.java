package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.GetChatMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.GetCustomerChatUseCase;
import com.customer.domain.repository.GetCustomerChatRepository;
import com.customer.remote.http.model.response.chat.GetChatListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetChatRepositoryImpl extends BaseRepository implements
    GetCustomerChatRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private GetChatMapper mMapper = new GetChatMapper();

  @Inject
  public GetChatRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<GetCustomerChatUseCase.ResponseValues> getChat(String bookingId, String pageNo) {
    return mDataSource.api().nodeApiHandler().getChatHistory(getHeader(), bookingId,
        pageNo).flatMap(
        new Function<GetChatListDetails, SingleSource<?
            extends GetCustomerChatUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetCustomerChatUseCase.ResponseValues> apply(
              GetChatListDetails details) {
            return Single.just(
                new GetCustomerChatUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
