package com.customer.domain.model.walletestimate;

public class WalletEstimateListData {
  private WalletEstimateItemData data;
  private String message;

  public WalletEstimateListData(
      WalletEstimateItemData data, String message) {
    this.data = data;
    this.message = message;
  }

  public WalletEstimateItemData getData() {
    return data;
  }

  public void setData(WalletEstimateItemData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
