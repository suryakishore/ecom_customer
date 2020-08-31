package com.customer.domain.model.uploadimage;

public class UploadImageData {
  private UploadImageItemData data;
  private String message;

  public UploadImageData(UploadImageItemData data, String message) {
    this.data = data;
    this.message = message;
  }

  public UploadImageItemData getData() {
    return data;
  }

  public void setData(UploadImageItemData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
