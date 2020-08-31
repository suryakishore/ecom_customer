package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.WishListMapper;
import com.customer.domain.interactor.GetWishListUseCase;
import com.customer.domain.repository.GetWishListRepository;
import com.customer.remote.http.model.response.wishlist.WishListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetWishListRepositoryImpl extends BaseRepository implements GetWishListRepository {

  private DataSource dataSource;
  private WishListMapper mMapper = new WishListMapper();

  @Inject
  public GetWishListRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<GetWishListUseCase.ResponseValues> getWishListProducts(int sortType,
      String searchQuery) {
    return dataSource.api().pythonApiHandler().getWishList(getHeader(), sortType,
        searchQuery).flatMap(
        new Function<WishListDetails, SingleSource<? extends GetWishListUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends GetWishListUseCase.ResponseValues> apply(
                WishListDetails wishListDetails) throws Exception {
              return Single.just(
                  new GetWishListUseCase.ResponseValues(mMapper.mapper(wishListDetails)));
            }
          });
  }
}
