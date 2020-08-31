package com.customer.domain.model.home;

import java.util.ArrayList;

public class ListData {

  private ArrayList<HomeOfferData> offers;

  private String penCount;

  private String bannerImageUrl;

  private String catName;

  private String subCatName;

  private String imageUrl;

  private ArrayList<CategoryData> categoryData;

  private String id;

  private int type;

  public ListData(ArrayList<HomeOfferData> offers, String penCount, String bannerImageUrl,
      String catName, String imageUrl, ArrayList<CategoryData> categoryData,
      String id, int type) {
    this.offers = offers;
    this.penCount = penCount;
    this.bannerImageUrl = bannerImageUrl;
    this.catName = catName;
    this.imageUrl = imageUrl;
    this.categoryData = categoryData;
    this.id = id;
    this.type = type;
  }

  public ListData(String catName, String subCatName,
      ArrayList<CategoryData> categoryData, int type) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.categoryData = categoryData;
    this.type = type;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }

  public ArrayList<HomeOfferData> getOffers() {
    return offers;
  }

  public void setOffers(ArrayList<HomeOfferData> offers) {
    this.offers = offers;
  }

  public String getPenCount() {
    return penCount;
  }

  public void setPenCount(String penCount) {
    this.penCount = penCount;
  }

  public String getBannerImageUrl() {
    return bannerImageUrl;
  }

  public void setBannerImageUrl(String bannerImageUrl) {
    this.bannerImageUrl = bannerImageUrl;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public ArrayList<CategoryData> getCategoryData() {
    return categoryData;
  }

  public void setCategoryData(ArrayList<CategoryData> categoryData) {
    this.categoryData = categoryData;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
