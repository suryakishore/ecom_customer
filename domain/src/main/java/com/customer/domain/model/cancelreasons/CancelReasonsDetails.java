package com.customer.domain.model.cancelreasons;

public class CancelReasonsDetails {
  private CancelReasonsData data;
  private String message;

  public CancelReasonsDetails(CancelReasonsData data, String message) {
    this.data = data;
    this.message = message;
  }

  public CancelReasonsData getData() {
    return data;
  }

  public void setData(CancelReasonsData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
