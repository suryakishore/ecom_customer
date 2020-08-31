package com.customer.domain.model.homesubcategory;

import java.util.ArrayList;

public class HomeSubCategoryData {

  private ArrayList<SubCategoryProductData> subCategory;

  private String subCategoryName;
  private String catName;

  public HomeSubCategoryData(ArrayList<SubCategoryProductData> subCategory, String subCategoryName,
      String catName) {
    this.subCategory = subCategory;
    this.subCategoryName = subCategoryName;
    this.catName = catName;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public ArrayList<SubCategoryProductData> getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(ArrayList<SubCategoryProductData> subCategory) {
    this.subCategory = subCategory;
  }

  public String getSubCategoryName() {
    return subCategoryName;
  }

  public void setSubCategoryName(String subCategoryName) {
    this.subCategoryName = subCategoryName;
  }
}
