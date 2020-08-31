package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.PromoCodeMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.domain.interactor.GetAllPromoCodesUseCase;
import com.customer.domain.repository.AllPromoCodesRepository;
import com.customer.remote.http.model.response.promocode.PromoCodeListDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import javax.inject.Inject;

public class AllPromoCodesRepositoryImpl extends BaseRepository implements
    AllPromoCodesRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private PromoCodeMapper mMapper = new PromoCodeMapper();

  @Inject
  public AllPromoCodesRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<GetAllPromoCodesUseCase.ResponseValues> allPromoCodes(String countryId,
      String cityId,
      String cartId, String storeId) {
    return mDataSource.api().promocodeApiHandler().getPromoCodes(getHeader(), countryId, cityId,
        cartId, storeId).flatMap(
        new Function<PromoCodeListDetails, SingleSource<?
            extends GetAllPromoCodesUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends GetAllPromoCodesUseCase.ResponseValues> apply(
              PromoCodeListDetails details) {
            return Single.just(
                new GetAllPromoCodesUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }
}
