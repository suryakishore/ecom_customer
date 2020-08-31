package com.customer.domain.model.recentsearch;

public class RecentSearchSuggestionData {

  private String timestamp;

  private String searchText;

  private String searchIn;

  public RecentSearchSuggestionData(String timestamp, String searchText, String searchIn) {
    this.timestamp = timestamp;
    this.searchText = searchText;
    this.searchIn = searchIn;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getSearchText() {
    return searchText;
  }

  public void setSearchText(String searchText) {
    this.searchText = searchText;
  }

  public String getSearchIn() {
    return searchIn;
  }

  public void setSearchIn(String searchIn) {
    this.searchIn = searchIn;
  }
}
