package com.customer.data.mapper;

import com.customer.domain.model.promocode.ApplyPromoCodeData;
import com.customer.domain.model.version.VersionData;
import com.customer.remote.http.model.response.promocode.ApplyPromoCodeListData;
import com.customer.remote.http.model.response.version.VersionListData;

public class VersionMapper {
  public VersionData mapper(VersionListData versionListData) {
    return new VersionData(versionListData.getMandatory(),versionListData.getVersion());
  }
}
