package com.customer.domain.model.pdp;

public class ProductDataModel {

  private ReviewData review;

  private ProductData productData;

  public ProductDataModel(ReviewData review, ProductData productData) {
    this.review = review;
    this.productData = productData;
  }

  public ReviewData getReview() {
    return review;
  }

  public void setReview(ReviewData review) {
    this.review = review;
  }

  public ProductData getProductData() {
    return productData;
  }

  public void setProductData(ProductData productData) {
    this.productData = productData;
  }
}
