package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.RecentSearchSugMapper;
import com.customer.domain.interactor.GetRecentSearchSuggestionUseCase;
import com.customer.domain.repository.GetRecentSearchSuggestionRepository;
import com.customer.remote.http.model.response.recentsearch.RecentSearchDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetRecentSearchSuggestionRepositoryImpl extends BaseRepository implements
    GetRecentSearchSuggestionRepository {

  private DataSource mDataSource;
  private RecentSearchSugMapper mMapper = new RecentSearchSugMapper();

  @Inject
  public GetRecentSearchSuggestionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
  }

  @Override
  public Single<GetRecentSearchSuggestionUseCase.ResponseValues> getRecentSearchSuggestion() {
    return mDataSource.api().pythonApiHandler().getRecentSearchSuggestion(getHeader()).flatMap(
        new Function<RecentSearchDetails, SingleSource<?
            extends GetRecentSearchSuggestionUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetRecentSearchSuggestionUseCase.ResponseValues> apply(
              RecentSearchDetails details) throws Exception {
            return Single.just(
                new GetRecentSearchSuggestionUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
