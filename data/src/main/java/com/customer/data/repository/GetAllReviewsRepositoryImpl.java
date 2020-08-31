package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.PdpMapper;
import com.customer.domain.interactor.GetAllReviewsUseCase;
import com.customer.domain.repository.GetAllReviewsRepository;
import com.customer.remote.http.model.response.pdp.ProductDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetAllReviewsRepositoryImpl extends BaseRepository implements GetAllReviewsRepository {

  private DataSource dataSource;
  private PdpMapper mPdpMapper = new PdpMapper();

  @Inject
  public GetAllReviewsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<GetAllReviewsUseCase.ResponseValues> getAllReviews(String parentProductId,
      String skip, String limit) {
    return dataSource.api().pythonApiHandler().getAllReviews(getHeader(), parentProductId, skip,
        limit).flatMap(
        new Function<ProductDetails, SingleSource<? extends GetAllReviewsUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends GetAllReviewsUseCase.ResponseValues> apply(
                ProductDetails productDetails) throws Exception {
              return Single.just(
                  new GetAllReviewsUseCase.ResponseValues(mPdpMapper.map(productDetails)));
            }
          });
  }
}
