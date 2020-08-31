package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuggestionMapper;
import com.customer.domain.interactor.GetSuggestionUseCase;
import com.customer.domain.repository.SuggestionRepository;
import com.customer.remote.http.model.response.suggestions.SuggestionListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class SuggestionRepositoryImpl extends BaseRepository implements SuggestionRepository {

  private SuggestionMapper mMapper = new SuggestionMapper();
  private DataSource mDataSource;

  @Inject
  public SuggestionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
  }

  @Override
  public Single<GetSuggestionUseCase.ResponseValues> suggestion(String searchItem) {
    return mDataSource.api().pythonApiHandler().getSuggestions(getHeader(), searchItem).flatMap(
        new Function<SuggestionListDetails, SingleSource<?
            extends GetSuggestionUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetSuggestionUseCase.ResponseValues> apply(
              SuggestionListDetails suggestionListDetails) throws Exception {
            return Single.just(
                new GetSuggestionUseCase.ResponseValues(mMapper.mapper(suggestionListDetails)));
          }
        });
  }
}
