package com.customer.remote.http.model.response.pdp;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails implements ValidItem, Parcelable {
  @SerializedName("review")
  @Expose
  private ReviewDetails review;

  @SerializedName("productData")
  @Expose
  private ProductDataDetails productData;

  public ProductDetails(ReviewDetails review, ProductDataDetails productData) {
    this.review = review;
    this.productData = productData;
  }

  protected ProductDetails(Parcel in) {
    productData = in.readParcelable(ProductDataDetails.class.getClassLoader());
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(productData, flags);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
    @Override
    public ProductDetails createFromParcel(Parcel in) {
      return new ProductDetails(in);
    }

    @Override
    public ProductDetails[] newArray(int size) {
      return new ProductDetails[size];
    }
  };

  public ReviewDetails getReview() {
    return review;
  }

  public void setReview(ReviewDetails review) {
    this.review = review;
  }

  public ProductDataDetails getProductData() {
    return productData;
  }

  public void setProductData(ProductDataDetails productData) {
    this.productData = productData;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
