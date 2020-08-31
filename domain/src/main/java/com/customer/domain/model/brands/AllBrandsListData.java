package com.customer.domain.model.brands;

import com.customer.domain.model.home.CategoryData;
import java.util.ArrayList;

public class AllBrandsListData {
  private ArrayList<CategoryData> data;
  private String message;
  private int penCount;

  public AllBrandsListData(int penCount, String message,ArrayList<CategoryData> data) {
    this.data = data;
    this.penCount = penCount;
    this.message = message;
  }

  public ArrayList<CategoryData> getData() {
    return data;
  }

  public void setData(ArrayList<CategoryData> data) {
    this.data = data;
  }

  public int getPenCount() {
    return penCount;
  }

  public void setPenCount(int penCount) {
    this.penCount = penCount;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
