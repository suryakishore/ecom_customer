package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.TWO_STAR;
import static com.customer.remote.http.RemoteConstants.ONE;

import com.customer.data.DataSource;
import com.customer.data.mapper.GetChatMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.PostChatUseCase;
import com.customer.domain.repository.PostChatRepository;
import com.customer.remote.http.model.request.PostMessage;
import com.customer.remote.http.model.response.chat.GetChatListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class PostMsgRepositoryImpl extends BaseRepository implements
    PostChatRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private GetChatMapper mMapper = new GetChatMapper();

  @Inject
  public PostMsgRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<PostChatUseCase.ResponseValues> postChat(String msg, int pageNo, String storeId,
      String storeOrderId) {
    return mDataSource.api().nodeApiHandler().postMessge(getHeader(),
        new PostMessage(TWO_STAR, pageNo, System.currentTimeMillis(), msg,
            preference.getUserId(), storeOrderId, storeId, ONE)).flatMap(
        new Function<GetChatListDetails, SingleSource<?
            extends PostChatUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends PostChatUseCase.ResponseValues> apply(
              GetChatListDetails details) {
            return Single.just(
                new PostChatUseCase.ResponseValues(null));
          }
        });
  }
}
