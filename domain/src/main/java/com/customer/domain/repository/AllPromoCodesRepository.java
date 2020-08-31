package com.customer.domain.repository;

import com.customer.domain.interactor.GetAllPromoCodesUseCase;
import io.reactivex.Single;
import java.util.ArrayList;

public interface AllPromoCodesRepository {
  Single<GetAllPromoCodesUseCase.ResponseValues> allPromoCodes(String countryId, String cityId,
      String cartId, String storeId);
}
