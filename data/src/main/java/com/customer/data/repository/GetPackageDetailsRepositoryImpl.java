package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.OrderDetailsMapper;
import com.customer.domain.interactor.GetPackageDetailsUseCase;
import com.customer.domain.repository.GetPackageDetailsRepository;
import com.customer.remote.http.model.response.orderdetails.OrderDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetPackageDetailsRepositoryImpl extends BaseRepository implements
    GetPackageDetailsRepository {
  private DataSource mDataSource;
  private OrderDetailsMapper mMapper = new OrderDetailsMapper();

  @Inject
  public GetPackageDetailsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
  }

  @Override
  public Single<GetPackageDetailsUseCase.ResponseValues> getPackageDetails(String productOrderId,
      String packageID) {
    return mDataSource.api().nodeApiHandler().getPackageDetails(getHeader(), packageID,
        productOrderId).flatMap(
        new Function<OrderDetails,
            SingleSource<? extends GetPackageDetailsUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends GetPackageDetailsUseCase.ResponseValues> apply(
                OrderDetails orderDetails) throws Exception {
              return Single.just(new GetPackageDetailsUseCase.ResponseValues(
                mMapper.convertToOrderData(orderDetails)));
            }
          });
  }
}
