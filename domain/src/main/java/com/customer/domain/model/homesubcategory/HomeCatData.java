package com.customer.domain.model.homesubcategory;

import java.util.ArrayList;

public class HomeCatData {

  private ArrayList<HomeSubCategoryData> data;
  private String message;

  public HomeCatData(ArrayList<HomeSubCategoryData> data, String message) {
    this.data = data;
    this.message = message;
  }

  public ArrayList<HomeSubCategoryData> getData() {
    return data;
  }

  public void setData(ArrayList<HomeSubCategoryData> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
