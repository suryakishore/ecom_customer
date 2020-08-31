package com.customer.domain.model.tracking;

import java.util.ArrayList;

public class TrackingListData {
  private TrackingData data;
  private String message;

  public TrackingListData(TrackingData data, String message) {
    this.data = data;
    this.message = message;
  }

  public TrackingData getData() {
    return data;
  }

  public void setData(TrackingData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
