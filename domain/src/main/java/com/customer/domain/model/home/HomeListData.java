package com.customer.domain.model.home;

import java.util.ArrayList;

public class HomeListData {

  private ArrayList<ListData> list;

  private int totalCatCount;

  public HomeListData(ArrayList<ListData> list) {
    this.list = list;
  }

  public HomeListData(ArrayList<ListData> list, int totalCatCount) {
    this.list = list;
    this.totalCatCount = totalCatCount;
  }

  public int getTotalCatCount() {
    return totalCatCount;
  }

  public void setTotalCatCount(int totalCatCount) {
    this.totalCatCount = totalCatCount;
  }

  public ArrayList<ListData> getList() {
    return list;
  }

  public void setList(ArrayList<ListData> list) {
    this.list = list;
  }
}
