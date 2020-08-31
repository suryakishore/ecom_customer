package com.customer.domain.model.getratable;

import java.util.ArrayList;

public class RatableData {
  private ArrayList<RatableProductData> reviewData;
  private SellerData sellerData;
  private String message;

  public RatableData(
      ArrayList<RatableProductData> reviewData, SellerData sellerData, String message) {
    this.reviewData = reviewData;
    this.sellerData = sellerData;
    this.message = message;
  }

  public ArrayList<RatableProductData> getReviewData() {
    return reviewData;
  }

  public void setReviewData(
      ArrayList<RatableProductData> reviewData) {
    this.reviewData = reviewData;
  }

  public SellerData getSellerData() {
    return sellerData;
  }

  public void setSellerData(SellerData sellerData) {
    this.sellerData = sellerData;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
