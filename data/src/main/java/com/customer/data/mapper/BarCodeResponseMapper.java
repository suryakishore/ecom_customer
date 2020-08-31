package com.customer.data.mapper;

import com.customer.domain.model.BarCodeResponseData;
import com.customer.remote.http.model.response.BaoCodeResponseDetails;

public class BarCodeResponseMapper {
  public BarCodeResponseData mapper(BaoCodeResponseDetails details) {

    return details != null ? new BarCodeResponseData(details.getParentProductId(),
        details.getChildProductId()) : new BarCodeResponseData();

  }
}
