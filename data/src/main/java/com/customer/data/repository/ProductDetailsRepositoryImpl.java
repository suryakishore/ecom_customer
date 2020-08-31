package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.PdpMapper;
import com.customer.domain.interactor.PdpUseCase;
import com.customer.domain.interactor.PdpUseCase.ResponseValues;
import com.customer.domain.repository.ProductDetailsRepository;
import com.customer.remote.http.model.request.Header;
import com.customer.remote.http.model.request.PdpRequest;
import com.customer.remote.http.model.response.pdp.ProductDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ProductDetailsRepositoryImpl extends BaseRepository implements
    ProductDetailsRepository {
  private DataSource dataSource;
  private PdpMapper mPdpMapper = new PdpMapper();

  @Inject
  public ProductDetailsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<PdpUseCase.ResponseValues> getPdp(String productId, String parentProductId,
      double lat, double longitude) {
    Header header = getHeader();
    header.setLatitude(lat);
    header.setLongitude(longitude);
    return dataSource.api().pythonApiHandler().getProductDetails(header,
        new PdpRequest(productId, parentProductId)).flatMap(
        new Function<ProductDetails, SingleSource<? extends ResponseValues>>() {
            @Override
          public SingleSource<? extends ResponseValues> apply(ProductDetails productDetails)
              throws Exception {
              return Single.just(new PdpUseCase.ResponseValues(
                mPdpMapper.map(productDetails)));
            }
          });
  }
}
