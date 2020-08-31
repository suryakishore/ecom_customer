package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.VersionMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.VersionUseCase;
import com.customer.domain.repository.VersionRepository;
import com.customer.remote.http.model.request.VersionRequest;
import com.customer.remote.http.model.response.version.VersionListData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class VersionRepositoryImpl extends BaseRepository implements VersionRepository {
  private DataSource dataSource;
  private VersionMapper mVersionMapper = new VersionMapper();
  private PreferenceManager preference;

  @Inject
  public VersionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<VersionUseCase.ResponseValues> versionUpdate(int type, String version) {
    return dataSource.api().nodeApiHandler().version(getHeader(), new VersionRequest(
        type, version)).flatMap(
        new Function<VersionListData, SingleSource<? extends VersionUseCase.ResponseValues>>() {
            @Override
          public SingleSource<? extends VersionUseCase.ResponseValues> apply(
                VersionListData model)
              throws Exception {
              return Single.just(new VersionUseCase.ResponseValues(mVersionMapper.mapper(model)));
            }
          });
  }
}
