package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.repository.observable.WishListObservable;
import com.customer.domain.interactor.AddProductToWishListUseCase;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.repository.AddProductToWishListRepository;
import com.customer.remote.http.model.request.AddToWishListRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class AddProductToWishListRepositoryImpl extends BaseRepository implements
    AddProductToWishListRepository {

  private DataSource dataSource;
  private SuccessMapper mSuccessMapper = new SuccessMapper();

  @Inject
  public AddProductToWishListRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<AddProductToWishListUseCase.ResponseValues> addProductWishList(String productId,
      String ipAddress, double latitude, double longitude) {

    WishListObservable.getInstance().emit(new ProductsData(productId, false));

    return dataSource.api().pythonApiHandler().addProductToWishList(getHeader(),
        new AddToWishListRequest(getUserId(), productId, ipAddress, latitude, longitude, "",
            "")).flatMap(
        new Function<CommonModel, SingleSource<?
            extends AddProductToWishListUseCase.ResponseValues>>() {
                @Override
                public SingleSource<? extends AddProductToWishListUseCase.ResponseValues> apply(
                    CommonModel model) throws Exception {
                  return Single.just(
                      new AddProductToWishListUseCase.ResponseValues(mSuccessMapper.mapper(model)));
                }
              });
  }
}
