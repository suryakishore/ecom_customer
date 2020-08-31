package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.ProductRateAndReviewUseCase;
import com.customer.domain.repository.ProductRateAndReviewRepository;
import com.customer.remote.http.model.request.ProductRateAndReviewRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import javax.inject.Inject;

public class ProductRateAndReviewRepositoryImpl extends BaseRepository implements
    ProductRateAndReviewRepository {
  private DataSource dataSource;
  private SuccessMapper mSuccessMapper = new SuccessMapper();

  @Inject
  public ProductRateAndReviewRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ProductRateAndReviewUseCase.ResponseValues> rateAndReview(int type,int reviewType,
      String productId, String attributeId, String reviewTitle, String reviewDescription,
      double rating, ArrayList<String> images, String city, String country, String ipAddress,
      double latitude, double longitude,String sellerReview) {
    return dataSource.api().pythonApiHandler().rateAndReviewProduct(getHeaderWithIp(ipAddress,
        latitude, longitude), new ProductRateAndReviewRequest(type,reviewType, productId, attributeId,
        reviewTitle, reviewDescription, rating, images, city, country,sellerReview)).flatMap(
        new Function<CommonModel, SingleSource<?
            extends ProductRateAndReviewUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends ProductRateAndReviewUseCase.ResponseValues> apply(
                CommonModel model) throws Exception {
              return Single.just(
                  new ProductRateAndReviewUseCase.ResponseValues(mSuccessMapper.mapper(model)));
            }
          });
  }
}
