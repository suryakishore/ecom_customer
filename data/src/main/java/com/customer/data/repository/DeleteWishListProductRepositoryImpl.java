package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.WishListObservable;
import com.customer.domain.interactor.DeleteWishListProductUseCase;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.repository.DeleteWishListProductRepository;
import com.customer.remote.http.model.request.AddToWishListRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class DeleteWishListProductRepositoryImpl extends BaseRepository
    implements DeleteWishListProductRepository {
  private DataSource dataSource;
  private SuccessMapper mapper = new SuccessMapper();
  private PreferenceManager preference;

  @Inject
  public DeleteWishListProductRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<DeleteWishListProductUseCase.ResponseValues> deleteWishListProduct(String productId,
      String ipAddress, double latitude, double longitude) {

    WishListObservable.getInstance().emit(new ProductsData(productId, true));
    return dataSource.api().pythonApiHandler().deleteWishListProduct(getHeader(),
        new AddToWishListRequest(preference.getUserId(), productId, ipAddress, latitude,
            longitude, "", "")).flatMap(
        new Function<CommonModel, SingleSource<?
            extends DeleteWishListProductUseCase.ResponseValues>>() {
                @Override
                public SingleSource<? extends DeleteWishListProductUseCase.ResponseValues> apply(
                    CommonModel model) throws Exception {
                  return Single.just(
                      new DeleteWishListProductUseCase.ResponseValues(mapper.mapper(model)));
                }
              });
  }
}
