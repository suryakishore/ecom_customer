package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.LikeDisLikeReviewUseCase;
import com.customer.domain.repository.LikeDisLikeReviewRepository;
import com.customer.remote.http.model.request.ReviewLikeDisLikeRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class LikeDisLikeReviewRepositoryImpl extends BaseRepository implements
    LikeDisLikeReviewRepository {

  private DataSource dataSource;
  private SuccessMapper mSuccessMapper = new SuccessMapper();

  @Inject
  public LikeDisLikeReviewRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<LikeDisLikeReviewUseCase.ResponseValues> likeReview(String reviewId, boolean like) {
    return dataSource.api().pythonApiHandler().reviewLikeDisLike(getHeader(),
        new ReviewLikeDisLikeRequest(reviewId, like)).flatMap(
        new Function<CommonModel,
            SingleSource<? extends LikeDisLikeReviewUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends LikeDisLikeReviewUseCase.ResponseValues> apply(
                CommonModel model) throws Exception {
              return Single.just(
                  new LikeDisLikeReviewUseCase.ResponseValues(mSuccessMapper.mapper(model)));
            }
          });
  }

  @Override
  public Single<LikeDisLikeReviewUseCase.ResponseValues> disLikeReview(boolean disLike,
      String reviewId) {
    return dataSource.api().pythonApiHandler().reviewLikeDisLike(getHeader(),
        new ReviewLikeDisLikeRequest(disLike, reviewId)).flatMap(
        new Function<CommonModel,
            SingleSource<? extends LikeDisLikeReviewUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends LikeDisLikeReviewUseCase.ResponseValues> apply(
                CommonModel model) throws Exception {
              return Single.just(
                  new LikeDisLikeReviewUseCase.ResponseValues(mSuccessMapper.mapper(model)));
            }
          });
  }
}
