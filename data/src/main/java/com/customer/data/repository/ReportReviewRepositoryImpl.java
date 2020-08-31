package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.ReportReviewUseCase;
import com.customer.domain.repository.ReportReviewRepository;
import com.customer.remote.http.model.request.ReportReviewRequest;
import com.customer.remote.http.model.response.CommonModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ReportReviewRepositoryImpl extends BaseRepository implements ReportReviewRepository {

  private DataSource dataSource;
  private SuccessMapper mSuccessMapper = new SuccessMapper();

  @Inject
  public ReportReviewRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ReportReviewUseCase.ResponseValues> reportReview(String reviewId) {
    return dataSource.api().pythonApiHandler().reportReview(getHeader(),
        new ReportReviewRequest(reviewId)).flatMap(
        new Function<CommonModel, SingleSource<? extends ReportReviewUseCase.ResponseValues>>() {
            @Override
            public SingleSource<? extends ReportReviewUseCase.ResponseValues> apply(CommonModel model)
                throws Exception {
              return Single.just(
                  new ReportReviewUseCase.ResponseValues(mSuccessMapper.mapper(model)));
            }
          });
  }
}
