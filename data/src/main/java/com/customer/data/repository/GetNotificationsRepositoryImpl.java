package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.NotificationMapper;
import com.customer.domain.interactor.GetNotificationsUseCase;
import com.customer.domain.repository.GetNotificationsRepository;
import com.customer.remote.http.model.response.notification.NotificationDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetNotificationsRepositoryImpl extends BaseRepository implements
    GetNotificationsRepository {
  private DataSource mDataSource;
  private NotificationMapper mMapper = new NotificationMapper();

  @Inject
  public GetNotificationsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<GetNotificationsUseCase.ResponseValues> getNotifications(String from, String to) {
    return mDataSource.api().pythonApiHandler().getNotificationsList(getHeader(),
        from, to).flatMap(
        new Function<NotificationDetails,
            SingleSource<? extends GetNotificationsUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends GetNotificationsUseCase.ResponseValues> apply(
                NotificationDetails notificationDetails) throws Exception {
              return Single.just(
                new GetNotificationsUseCase.ResponseValues(mMapper.mapper(notificationDetails)));
            }
          });
  }
}
