package com.customer.domain.model.generatetoken;

public class GenerateTokenListData {
  private GenerateTokenItemData data;
  private String message;

  public GenerateTokenListData(GenerateTokenItemData data, String message) {
    this.data = data;
    this.message = message;
  }

  public GenerateTokenItemData getData() {
    return data;
  }

  public void setData(GenerateTokenItemData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
