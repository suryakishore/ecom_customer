package com.customer.data.mapper;

import com.customer.domain.model.promocode.ApplyPromoCodeData;
import com.customer.remote.http.model.response.promocode.ApplyPromoCodeListData;

public class ApplyPromoCodeMapper {
  public ApplyPromoCodeData mapper(ApplyPromoCodeListData applyPromoCodeListData) {
    return new ApplyPromoCodeData(applyPromoCodeListData.getTotal_amt(),
        applyPromoCodeListData.getPrice(), applyPromoCodeListData.getDelivery_fee(),
        applyPromoCodeListData.getReduced_amt());
  }
}
