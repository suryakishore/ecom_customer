package com.customer.data.mapper;

import com.customer.domain.model.generatetoken.GenerateTokenItemData;
import com.customer.domain.model.generatetoken.GenerateTokenListData;
import com.customer.remote.http.model.response.generatetoken.GenerateTokenItemDetails;
import com.customer.remote.http.model.response.generatetoken.GenerateTokenListDetails;

public class GenerateTokenMapper {
  public GenerateTokenListData mapper(
      GenerateTokenListDetails trackingListData) {
    GenerateTokenListData trackingData = null;
    if (trackingListData != null) {
      trackingData = new GenerateTokenListData(
          convertToTrackingItemData(trackingListData.getData()), trackingListData.getMessage());
    }
    return trackingData;
  }

  private GenerateTokenItemData convertToTrackingItemData(
      GenerateTokenItemDetails trackingItemDetailsArrayList) {
    GenerateTokenItemData itemData = null;
    if (trackingItemDetailsArrayList != null) {
      itemData = new GenerateTokenItemData(trackingItemDetailsArrayList.getAccessExpireAt(),
          trackingItemDetailsArrayList.getAccessToken());
    }
    return itemData;
  }
}
