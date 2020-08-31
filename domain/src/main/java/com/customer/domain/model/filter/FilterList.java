package com.customer.domain.model.filter;

import java.util.ArrayList;

public class FilterList {
  private String name;
  private String currency;
  private String currencySymbol;
  private int selType;
  private int level;
  private int filterType;
  private ArrayList<FilterListData> data;

  public FilterList(int filterType) {
    this.filterType = filterType;
  }

  public FilterList(String name, ArrayList<FilterListData> data, int filterType) {
    this.name = name;
    this.data = data;
    this.filterType = filterType;
  }

  public FilterList(String name, String currency,
      String currencySymbol, int selType, int level, ArrayList<FilterListData> data,
      int filterType) {
    this.name = name;
    this.currency = currency;
    this.currencySymbol = currencySymbol;
    this.selType = selType;
    this.level = level;
    this.data = data;
    this.filterType = filterType;
  }

  public int getFilterType() {
    return filterType;
  }

  public void setFilterType(int filterType) {
    this.filterType = filterType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public int getSelType() {
    return selType;
  }

  public void setSelType(int selType) {
    this.selType = selType;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public ArrayList<FilterListData> getData() {
    return data;
  }

  public void setData(ArrayList<FilterListData> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (o != null) {
      FilterList that = (FilterList) o;
      return getFilterType() == that.getFilterType();
    } else {
      return false;
    }
  }
}
