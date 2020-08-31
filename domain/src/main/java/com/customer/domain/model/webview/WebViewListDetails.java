package com.customer.domain.model.webview;

import com.customer.domain.model.notification.NotificationListData;
import java.util.ArrayList;

public class WebViewListDetails {
  private WebViewListData data;
  private String message;

  public WebViewListDetails(
      WebViewListData data ,String message) {
    this.data = data;
    this.message = message;
  }

  public WebViewListData getData() {
    return data;
  }

  public void setData(WebViewListData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
