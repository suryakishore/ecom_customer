package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.promocode.PromoCodeDetails;
import com.customer.domain.repository.AllPromoCodesRepository;
import io.reactivex.Single;
import java.util.ArrayList;
import javax.inject.Inject;

public class GetAllPromoCodesUseCase extends
    UseCase<GetAllPromoCodesUseCase.RequestValues, GetAllPromoCodesUseCase.ResponseValues> {
  private AllPromoCodesRepository mRepository;

  @Inject
  public GetAllPromoCodesUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      AllPromoCodesRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.allPromoCodes(requestValues.countryId, requestValues.cityId,
        requestValues.cartId, requestValues.storeId);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String countryId;
    private String cityId;
    private String cartId;
    private String storeId;

    public RequestValues(String countryId, String cityId, String cartId,
        String storeId) {
      this.countryId = countryId;
      this.cityId = cityId;
      this.cartId = cartId;
      this.storeId = storeId;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private PromoCodeDetails mData;

    public ResponseValues(PromoCodeDetails data) {
      this.mData = data;
    }

    public PromoCodeDetails getData() {
      return mData;
    }

    public void setData(PromoCodeDetails data) {
      this.mData = data;
    }
  }
}
