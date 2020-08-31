package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.ClearWishListUseCase;
import com.customer.domain.repository.ClearWishListRepository;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ClearWishListRepositoryImpl extends BaseRepository implements ClearWishListRepository {

  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();

  @Inject
  public ClearWishListRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
  }

  @Override
  public Single<ClearWishListUseCase.ResponseValues> clearWishList() {
    return mDataSource.api().pythonApiHandler().clearAllWishListItems(getHeader()).flatMap(
        new Function<CommonModel, SingleSource<? extends ClearWishListUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends ClearWishListUseCase.ResponseValues> apply(
              CommonModel model) throws Exception {
            return Single.just(new ClearWishListUseCase.ResponseValues(mMapper.mapper(model)));
          }
        });
  }
}
