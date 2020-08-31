package com.customer.domain.model.filter;

import java.util.ArrayList;

public class FilterListData {
  private boolean isSelected = false;
  private String name;
  private String rgb;
  private int selType;
  private ArrayList<String> data;
  private int maxPrice;
  private String timeStamp;
  private int minPrice;

  public FilterListData(String name, double maxPrice, double minPrice) {
    this.name = name;
    this.maxPrice = (int) maxPrice;
    this.minPrice = (int) minPrice;
  }

  public FilterListData(String name) {
    this.name = name;
  }

  public FilterListData(String name, String timeStamp) {
    this.name = name;
    this.timeStamp = timeStamp;
  }

  public FilterListData(String name, String rgb, int selType, ArrayList<String> data) {
    this.name = name;
    this.rgb = rgb;
    this.selType = selType;
    this.data = data;
  }

  public int getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(int maxPrice) {
    this.maxPrice = maxPrice;
  }

  public int getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(int minPrice) {
    this.minPrice = minPrice;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getRgb() {
    return rgb;
  }

  public void setRgb(String rgb) {
    this.rgb = rgb;
  }

  public int getSelType() {
    return selType;
  }

  public void setSelType(int selType) {
    this.selType = selType;
  }

  public ArrayList<String> getData() {
    return data;
  }

  public void setData(ArrayList<String> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FilterListData)) {
      return false;
    }
    FilterListData that = (FilterListData) o;
    return getName().equalsIgnoreCase(that.getName());
  }
}
