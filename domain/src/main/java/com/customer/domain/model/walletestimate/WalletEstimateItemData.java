package com.customer.domain.model.walletestimate;

public class WalletEstimateItemData {
  private String estimateAmount;

  public WalletEstimateItemData(String estimateAmount) {
    this.estimateAmount = estimateAmount;
  }

  public String getEstimateAmount() {
    return estimateAmount;
  }

  public void setEstimateAmount(String estimateAmount) {
    this.estimateAmount = estimateAmount;
  }
}
