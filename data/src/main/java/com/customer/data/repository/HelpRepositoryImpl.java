package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.ChangeLanguageMapper;
import com.customer.data.mapper.HelpMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.ChangeLanguageUseCase;
import com.customer.domain.interactor.HelpUseCase;
import com.customer.domain.repository.ChangeLanguageRepository;
import com.customer.domain.repository.HelpRepository;
import com.customer.remote.http.model.response.changelanguage.ChangeLanListDetails;
import com.customer.remote.http.model.response.help.HelpListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class HelpRepositoryImpl extends BaseRepository implements
    HelpRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private HelpMapper mMapper = new HelpMapper();

  @Inject
  public HelpRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<HelpUseCase.ResponseValues> help() {
    return mDataSource.api().nodeApiHandler().getHelp(getHeader()).flatMap(
        new Function<HelpListDetails, SingleSource<?
            extends HelpUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends HelpUseCase.ResponseValues> apply(
              HelpListDetails details) {
            return Single.just(
                new HelpUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
