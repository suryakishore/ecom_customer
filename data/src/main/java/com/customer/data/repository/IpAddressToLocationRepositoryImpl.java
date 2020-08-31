package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.IpAddressToLocationMapper;
import com.customer.domain.interactor.IpAddressToLocationUseCase;
import com.customer.domain.repository.IpAddressToLocationRepository;
import com.customer.remote.http.model.request.Header;
import com.customer.remote.http.model.response.location.IpAddressToLocationDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class IpAddressToLocationRepositoryImpl extends BaseRepository implements
    IpAddressToLocationRepository {

  private DataSource mDataSource;
  private IpAddressToLocationMapper mMapper = new IpAddressToLocationMapper();

  @Inject
  public IpAddressToLocationRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
  }

  @Override
  public Single<IpAddressToLocationUseCase.ResponseValues> ipAddressToLocation(String ipAddress) {

    Header header = getHeader();
    header.setIpAddress(ipAddress);
    return mDataSource.api().nodeApiHandler().ipAddressToLocation(header).flatMap(
        new Function<IpAddressToLocationDetails, SingleSource<?
            extends IpAddressToLocationUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends IpAddressToLocationUseCase.ResponseValues> apply(
              IpAddressToLocationDetails ipAddressToLocationDetails) throws Exception {
            return Single.just(new IpAddressToLocationUseCase.ResponseValues(
                mMapper.mapper(ipAddressToLocationDetails)));
          }
        });
  }
}
