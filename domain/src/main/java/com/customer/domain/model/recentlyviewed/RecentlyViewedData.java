package com.customer.domain.model.recentlyviewed;

import com.customer.domain.model.common.ProductsData;
import java.util.ArrayList;

public class RecentlyViewedData {

  private String catName;

  private ArrayList<ProductsData> categoryData;

  private int penCount;

  private int seqId;

  public RecentlyViewedData(String catName,
      ArrayList<ProductsData> categoryData, int penCount, int seqId) {
    this.catName = catName;
    this.categoryData = categoryData;
    this.penCount = penCount;
    this.seqId = seqId;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public ArrayList<ProductsData> getCategoryData() {
    return categoryData;
  }

  public void setCategoryData(
      ArrayList<ProductsData> categoryData) {
    this.categoryData = categoryData;
  }

  public int getPenCount() {
    return penCount;
  }

  public void setPenCount(int penCount) {
    this.penCount = penCount;
  }

  public int getSeqId() {
    return seqId;
  }

  public void setSeqId(int seqId) {
    this.seqId = seqId;
  }
}
