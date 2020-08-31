package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.BarCodeResponseMapper;
import com.customer.domain.interactor.GetProductOnQrCodeUseCase;
import com.customer.domain.repository.GetProductOnQrCodeRepository;
import com.customer.remote.http.model.response.BaoCodeResponseDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetProductOnQrCodeRepositoryImpl extends BaseRepository implements
    GetProductOnQrCodeRepository {

  private DataSource mDataSource;
  private BarCodeResponseMapper mMapper = new BarCodeResponseMapper();

  @Inject
  public GetProductOnQrCodeRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<GetProductOnQrCodeUseCase.ResponseValues> getProductOnQrCode(String qrCode) {
    return mDataSource.api().pythonApiHandler().getProductOnQrCode(getHeader(), qrCode).flatMap(
        new Function<BaoCodeResponseDetails, SingleSource<?
            extends GetProductOnQrCodeUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetProductOnQrCodeUseCase.ResponseValues> apply(
              BaoCodeResponseDetails details) throws Exception {
            return Single.just(
                new GetProductOnQrCodeUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
