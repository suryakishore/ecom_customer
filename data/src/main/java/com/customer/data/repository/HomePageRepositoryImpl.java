package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.HomePageMapper;
import com.customer.domain.interactor.HomePageUseCase.ResponseValues;
import com.customer.domain.repository.HomePageRepository;
import com.customer.remote.http.model.response.newHome.HomeListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class HomePageRepositoryImpl extends BaseRepository implements HomePageRepository {

  private DataSource dataSource;
  private HomePageMapper homePageMapper = new HomePageMapper();

  @Inject
  public HomePageRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ResponseValues> homePage() {
    return dataSource.api()
        .pythonApiHandler().homeNewPageApi(getHeader()).flatMap(
            new Function<HomeListDetails, SingleSource<? extends ResponseValues>>() {
              @Override
              public SingleSource<? extends ResponseValues> apply(HomeListDetails homeListDetails)
                  throws Exception {
                return Single.just(new ResponseValues(homePageMapper.mapper(homeListDetails)));
              }
            });
  }
}
