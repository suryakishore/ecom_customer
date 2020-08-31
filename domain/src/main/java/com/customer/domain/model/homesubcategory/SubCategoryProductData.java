package com.customer.domain.model.homesubcategory;

import com.customer.domain.model.common.FinalPriceListData;
import com.customer.domain.model.common.ImageData;
import com.customer.domain.model.pdp.SupplierData;
import java.util.ArrayList;

public class SubCategoryProductData {

  private ProductOfferData offers;

  private String availableQuantity;

  private String brandName;

  private ArrayList<ImageData> images;

  private String parentProductId;

  private String childProductId;

  private String currencySymbol;

  private int mTotalStarRating;

  private String productName;

  private SupplierData supplier;

  private boolean outOfStock;

  private String unitId;

  private String currency;

  private String discountType;

  private FinalPriceListData finalPriceList;

  public SubCategoryProductData(ProductOfferData offers, String availableQuantity,
      String brandName, ArrayList<ImageData> images,
      String parentProductId, String childProductId, String currencySymbol,
      int totalStarRating, String productName, SupplierData supplier,
      boolean outOfStock, String unitId, String currency,
      int discountType, FinalPriceListData finalPriceList) {
    this.offers = offers;
    this.availableQuantity = availableQuantity;
    this.brandName = brandName;
    this.images = images;
    this.parentProductId = parentProductId;
    this.childProductId = childProductId;
    this.currencySymbol = currencySymbol;
    mTotalStarRating = totalStarRating;
    this.productName = productName;
    this.supplier = supplier;
    this.outOfStock = outOfStock;
    this.unitId = unitId;
    this.currency = currency;
    this.discountType = discountType + "";
    this.finalPriceList = finalPriceList;
  }

  public ProductOfferData getOffers() {
    return offers;
  }

  public void setOffers(ProductOfferData offers) {
    this.offers = offers;
  }

  public String getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(String availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public ArrayList<ImageData> getImages() {
    return images;
  }

  public void setImages(ArrayList<ImageData> images) {
    this.images = images;
  }

  public String getParentProductId() {
    return parentProductId;
  }

  public void setParentProductId(String parentProductId) {
    this.parentProductId = parentProductId;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public int getTotalStarRating() {
    return mTotalStarRating;
  }

  public void setTotalStarRating(int totalStarRating) {
    mTotalStarRating = totalStarRating;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public SupplierData getSupplier() {
    return supplier;
  }

  public void setSupplier(SupplierData supplier) {
    this.supplier = supplier;
  }

  public boolean getOutOfStock() {
    return outOfStock;
  }

  public void setOutOfStock(boolean outOfStock) {
    this.outOfStock = outOfStock;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDiscountType() {
    return discountType;
  }

  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  public FinalPriceListData getFinalPriceList() {
    return finalPriceList;
  }

  public void setFinalPriceList(FinalPriceListData finalPriceList) {
    this.finalPriceList = finalPriceList;
  }
}
