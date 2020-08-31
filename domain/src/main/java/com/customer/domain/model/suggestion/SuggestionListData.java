package com.customer.domain.model.suggestion;

import java.util.ArrayList;

public class SuggestionListData {

  private ArrayList<SuggestionItemData> data;

  private String message;

  public SuggestionListData(
      ArrayList<SuggestionItemData> data, String message) {
    this.data = data;
    this.message = message;
  }

  public ArrayList<SuggestionItemData> getData() {
    return data;
  }

  public void setData(ArrayList<SuggestionItemData> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
