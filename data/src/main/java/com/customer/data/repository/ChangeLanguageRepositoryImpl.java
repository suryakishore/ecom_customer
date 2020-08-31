package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.AllBrandsMapper;
import com.customer.data.mapper.ChangeLanguageMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.ChangeLanguageUseCase;
import com.customer.domain.repository.ChangeLanguageRepository;
import com.customer.remote.http.model.response.changelanguage.ChangeLanListDetails;
import com.customer.remote.http.model.response.newHome.HomeListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ChangeLanguageRepositoryImpl extends BaseRepository implements
    ChangeLanguageRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private ChangeLanguageMapper mMapper = new ChangeLanguageMapper();

  @Inject
  public ChangeLanguageRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<ChangeLanguageUseCase.ResponseValues> changeLanguage() {
    return mDataSource.api().nodeApiHandler().getLanguages().flatMap(
        new Function<ChangeLanListDetails, SingleSource<?
            extends ChangeLanguageUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends ChangeLanguageUseCase.ResponseValues> apply(
              ChangeLanListDetails details) {
            return Single.just(
                new ChangeLanguageUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
