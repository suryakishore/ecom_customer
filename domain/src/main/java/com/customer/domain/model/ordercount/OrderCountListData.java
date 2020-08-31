package com.customer.domain.model.ordercount;

import com.customer.domain.model.tracking.TrackingItemData;
import java.util.ArrayList;

public class OrderCountListData {
  private ArrayList<OrderCountItemListData> data;
  private String message;

  public OrderCountListData(
      ArrayList<OrderCountItemListData> data, String message) {
    this.data = data;
    this.message = message;
  }

  public ArrayList<OrderCountItemListData> getData() {
    return data;
  }

  public void setData(ArrayList<OrderCountItemListData> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
