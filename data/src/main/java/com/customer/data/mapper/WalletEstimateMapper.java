package com.customer.data.mapper;

import com.customer.domain.model.walletestimate.WalletEstimateItemData;
import com.customer.remote.http.model.response.walletEstimate.EstimateItemDetails;

public class WalletEstimateMapper {
  public WalletEstimateItemData mapper(
      EstimateItemDetails trackingItemDetailsArrayList) {
    WalletEstimateItemData walletEstimateItemData = null;
    if (trackingItemDetailsArrayList != null) {
      walletEstimateItemData = new WalletEstimateItemData(
          trackingItemDetailsArrayList.getEstimateAmount());
    }
    return walletEstimateItemData;
  }
}
