package com.customer.domain.model.suggestion;

import com.customer.domain.model.common.ImageData;
import java.util.ArrayList;

public class SuggestionItemData {
  private ArrayList<ImageData> images;
  private String storeType;
  private String productId;
  private String childProductId;
  private String subCatName;
  private String currencySymbol;
  private String productName;
  private String subSubCatName;
  private String catName;
  private String currency;
  private String inSection;
  private boolean isProduct;
  private String brandTitle;
  private int seqId;

  public SuggestionItemData(ArrayList<ImageData> images, String storeType, String productId,
      String childProductId,
      String subCatName, String currencySymbol, String productName, String subSubCatName,
      String catName, String currency, String inSection, boolean isProduct,
      String brandTitle, int seqId) {
    this.images = images;
    this.storeType = storeType;
    this.productId = productId;
    this.childProductId = childProductId;
    this.subCatName = subCatName;
    this.currencySymbol = currencySymbol;
    this.productName = productName;
    this.subSubCatName = subSubCatName;
    this.catName = catName;
    this.currency = currency;
    this.inSection = inSection;
    this.isProduct = isProduct;
    this.brandTitle = brandTitle;
    this.seqId = seqId;
  }

  public ArrayList<ImageData> getImages() {
    return images;
  }

  public void setImages(ArrayList<ImageData> images) {
    this.images = images;
  }

  public String getStoreType() {
    return storeType;
  }

  public void setStoreType(String storeType) {
    this.storeType = storeType;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }

  public String getSubSubCatName() {
    return subSubCatName;
  }

  public void setSubSubCatName(String subSubCatName) {
    this.subSubCatName = subSubCatName;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getInSection() {
    return inSection;
  }

  public void setInSection(String inSection) {
    this.inSection = inSection;
  }

  public boolean isProduct() {
    return isProduct;
  }

  public void setProduct(boolean product) {
    isProduct = product;
  }

  public String getBrandTitle() {
    return brandTitle;
  }

  public void setBrandTitle(String brandTitle) {
    this.brandTitle = brandTitle;
  }

  public int getSeqId() {
    return seqId;
  }

  public void setSeqId(int seqId) {
    this.seqId = seqId;
  }
}
