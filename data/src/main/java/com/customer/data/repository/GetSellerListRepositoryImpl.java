package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SellerListMapper;
import com.customer.domain.interactor.GetSellerListUseCase;
import com.customer.domain.repository.GetSellerListRepository;
import com.customer.remote.http.model.request.GetSellerListRequest;
import com.customer.remote.http.model.response.sellerlist.SellerDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetSellerListRepositoryImpl extends BaseRepository implements GetSellerListRepository {

  private DataSource mDataSource;
  private SellerListMapper mMapper = new SellerListMapper();

  @Inject
  public GetSellerListRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<GetSellerListUseCase.ResponseValues> getSellerList(String productId,
      String parentProductId, String loginType) {
    return mDataSource.api().pythonApiHandler().getSellerList(getHeader(),
        new GetSellerListRequest(loginType, productId, parentProductId)).flatMap(
        new Function<SellerDetails, SingleSource<? extends GetSellerListUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends GetSellerListUseCase.ResponseValues> apply(
                SellerDetails sellerDetails) throws Exception {
              return Single.just(
                  new GetSellerListUseCase.ResponseValues(mMapper.mapper(sellerDetails)));
            }
          });
  }
}
