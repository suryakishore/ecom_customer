package com.customer.domain.model.help;

import java.util.ArrayList;

public class HelpListData {
  private ArrayList<HelpItemData> data;
  private String message;

  public HelpListData(
      ArrayList<HelpItemData> data, String message) {
    this.data = data;
    this.message = message;
  }

  public ArrayList<HelpItemData> getData() {
    return data;
  }

  public void setData(ArrayList<HelpItemData> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
