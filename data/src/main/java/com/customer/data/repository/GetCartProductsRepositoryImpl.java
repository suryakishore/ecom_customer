package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.CartMapper;
import com.customer.domain.interactor.GetCartProductsUseCase;
import com.customer.domain.repository.GetCartProductsRepository;
import com.customer.remote.http.model.response.getcart.CartDetails;
import com.data.cache.DatabaseManager;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetCartProductsRepositoryImpl extends BaseRepository implements
    GetCartProductsRepository {
  private DataSource mDataSource;
  private CartMapper mCartMapper = new CartMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public GetCartProductsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<GetCartProductsUseCase.ResponseValues> getCartProducts() {
    return mDataSource.api().nodeApiHandler().getCartProducts(getHeader(), getUserId()).flatMap(
        new Function<CartDetails, SingleSource<? extends GetCartProductsUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetCartProductsUseCase.ResponseValues> apply(
              CartDetails cartDetails) throws Exception {
            return Single.just(
                new GetCartProductsUseCase.ResponseValues(
                    mCartMapper.mapper(cartDetails, mDatabaseManager)));
          }
        });
  }
}
