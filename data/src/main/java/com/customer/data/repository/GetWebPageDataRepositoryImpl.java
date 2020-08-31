package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.NotificationMapper;
import com.customer.data.mapper.WebViewMapper;
import com.customer.domain.interactor.GetWebPageDataUseCase;
import com.customer.domain.repository.GetWebPageDataRepository;
import com.customer.remote.http.model.response.wallet.WalletTransactionsListDetails;
import com.customer.remote.http.model.response.webview.WebPageData;
import com.customer.remote.http.model.response.webview.WebPageDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetWebPageDataRepositoryImpl extends BaseRepository implements
    GetWebPageDataRepository {
  private DataSource mDataSource;
  private WebViewMapper mMapper = new WebViewMapper();

  @Inject
  public GetWebPageDataRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<GetWebPageDataUseCase.ResponseValues> getWebPageData() {
    return mDataSource.api().nodeApiHandler().getWebPageData(getHeader()).flatMap(
        new Function<WebPageData,
            SingleSource<? extends GetWebPageDataUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetWebPageDataUseCase.ResponseValues> apply(
              WebPageData notificationDetails) throws Exception {
            return Single.just(
                new GetWebPageDataUseCase.ResponseValues(mMapper.mapper(notificationDetails)));
          }
        });
  }
}
