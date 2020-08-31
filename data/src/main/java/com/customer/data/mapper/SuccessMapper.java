package com.customer.data.mapper;

import com.customer.domain.model.CommonData;
import com.customer.remote.http.model.response.CommonModel;

public class SuccessMapper {
  public CommonData mapper(CommonModel model) {
    return new CommonData(model.getMessage());
  }
}
