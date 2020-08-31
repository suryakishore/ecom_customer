package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.UploadImageMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.UploadImageUseCase;
import com.customer.domain.repository.UploadImageRepository;
import com.customer.remote.http.model.response.uploadImage.UploadImageDetails;
import com.customer.remote.http.model.response.uploadImage.UploadImageItemDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.io.File;
import javax.inject.Inject;

public class UploadImageRepositoryImpl extends BaseRepository implements
    UploadImageRepository {
  private DataSource mDataSource;
  private UploadImageMapper mMapper = new UploadImageMapper();

  @Inject
  public UploadImageRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<UploadImageUseCase.ResponseValues> uploadImage(String folder, File file,
      String fileName) {
    return mDataSource.api().nodeApiHandler().uploadImage(folder, file, fileName).flatMap(
        new Function<UploadImageItemDetails, SingleSource<?
            extends UploadImageUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends UploadImageUseCase.ResponseValues> apply(
              UploadImageItemDetails details) {
            return Single.just(
                new UploadImageUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
