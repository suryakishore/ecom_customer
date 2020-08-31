package com.customer.data.mapper;

import com.customer.domain.model.recentlyviewed.RecentlyViewedData;
import com.customer.remote.http.model.response.recentlyviewed.RecentlyViewedDetails;

public class RecentlyViewedProductListMapper {

  public RecentlyViewedData mapper(RecentlyViewedDetails recentlyViewedDetails) {
    return new RecentlyViewedData(recentlyViewedDetails.getCatName(),
        CommonMapper.convertToProductData(recentlyViewedDetails.getCategoryData()),
        recentlyViewedDetails.getPenCount(),
        recentlyViewedDetails.getSeqId());
  }
}
