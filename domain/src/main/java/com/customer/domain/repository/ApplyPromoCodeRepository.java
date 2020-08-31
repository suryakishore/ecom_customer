package com.customer.domain.repository;

import com.customer.domain.interactor.ApplyPromoCodesUseCase;
import com.customer.domain.model.promocode.ProductPromoInput;
import io.reactivex.Single;
import java.util.ArrayList;

public interface ApplyPromoCodeRepository {

  Single<ApplyPromoCodesUseCase.ResponseValues> applyPromoCode(int paymentMethodType,
      String promoCode,String promoId,
      String currencySymbol,
      float totalPurchaseValue, String cartId, float deliveryFee, String currencyName,
      ArrayList<ProductPromoInput> productPromoInputArrayList);
}