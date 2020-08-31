package com.customer.data.repository;

import static com.customer.data.utils.DataConstants.NONE;

import android.text.TextUtils;
import com.customer.data.DataSource;
import com.customer.data.mapper.GetFilterParamsMapper;
import com.customer.domain.interactor.GetFilterParamsUseCase;
import com.customer.domain.interactor.GetFilterParamsUseCase.ResponseValues;
import com.customer.domain.repository.GetFilterParamsRepository;
import com.customer.remote.http.model.request.CategoryQueryParameterRequest;
import com.customer.remote.http.model.response.filter.FilterDetails;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class GetFilterParamsRepositoryImpl extends BaseRepository implements
    GetFilterParamsRepository {
  GetFilterParamsMapper mapper = new GetFilterParamsMapper();
  private DataSource dataSource;

  @Inject
  public GetFilterParamsRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.dataSource = dataSource;
  }

  @Override
  public Single<ResponseValues> getFilterParams(int level, String catName, String subCatName,
      String subSubCatName, String searchQuery, String brandName) {

    CategoryQueryParameterRequest request;
    if (!TextUtils.isEmpty(searchQuery)) {
      request = new CategoryQueryParameterRequest(searchQuery);
    } else if (!TextUtils.isEmpty(brandName)) {
      request = new CategoryQueryParameterRequest(level, brandName, "");
    } else {
      request = new CategoryQueryParameterRequest(level,
          catName, subCatName, subSubCatName, NONE);
    }
    return dataSource.api().pythonApiHandler().getFilterParams(getHeader(), request).flatMap(
        new Function<FilterDetails, SingleSource<? extends ResponseValues>>() {
          @Override
          public SingleSource<? extends ResponseValues> apply(FilterDetails details)
              throws Exception {
            return Single.just(new GetFilterParamsUseCase.ResponseValues(mapper.mapper(details)));
          }
        });
  }
}
