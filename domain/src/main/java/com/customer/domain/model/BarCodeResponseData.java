package com.customer.domain.model;

public class BarCodeResponseData {
  private String parentProductId;

  private String childProductId;

  private boolean isSuccess;

  public BarCodeResponseData() {
  }

  public BarCodeResponseData(boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public BarCodeResponseData(String parentProductId, String childProductId) {
    this.parentProductId = parentProductId;
    this.childProductId = childProductId;
  }

  public boolean getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public String getParentProductId() {
    return parentProductId;
  }

  public void setParentProductId(String parentProductId) {
    this.parentProductId = parentProductId;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }
}
