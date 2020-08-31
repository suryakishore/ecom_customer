package com.customer.domain.model.notification;

import java.util.ArrayList;

public class NotificationListDetails {
  private ArrayList<NotificationListData> data;
  private String totalCount;
  private String message;

  public NotificationListDetails(
      ArrayList<NotificationListData> data, String totalCount, String message) {
    this.data = data;
    this.totalCount = totalCount;
    this.message = message;
  }

  public ArrayList<NotificationListData> getData() {
    return data;
  }

  public void setData(ArrayList<NotificationListData> data) {
    this.data = data;
  }

  public String getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(String totalCount) {
    this.totalCount = totalCount;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
