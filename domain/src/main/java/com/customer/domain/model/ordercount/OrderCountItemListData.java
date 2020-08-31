package com.customer.domain.model.ordercount;

public class OrderCountItemListData {
  private String statusText;
  private int count;
  private int status;

  public OrderCountItemListData(String statusText, int count, int status) {
    this.statusText = statusText;
    this.count = count;
    this.status = status;
  }

  public String getStatusText() {
    return statusText;
  }

  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
