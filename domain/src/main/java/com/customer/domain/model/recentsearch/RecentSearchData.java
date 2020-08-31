package com.customer.domain.model.recentsearch;

import java.util.ArrayList;

public class RecentSearchData {

  private String message;

  private int totalCount;

  private ArrayList<RecentSearchSuggestionData> data;

  public RecentSearchData(String message, int totalCount,
      ArrayList<RecentSearchSuggestionData> data) {
    this.message = message;
    this.totalCount = totalCount;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public ArrayList<RecentSearchSuggestionData> getData() {
    return data;
  }

  public void setData(
      ArrayList<RecentSearchSuggestionData> data) {
    this.data = data;
  }
}
