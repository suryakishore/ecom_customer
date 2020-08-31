package com.customer.remote.http.model.response.productListing;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.common.ProductsDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ProductListingDetails implements ValidItem, Parcelable {

  @SerializedName("penCount")
  @Expose
  private String penCount;

  @SerializedName("products")
  @Expose
  private ArrayList<ProductsDetails> products;

  public ProductListingDetails(String penCount, ArrayList<ProductsDetails> products) {
    this.penCount = penCount;
    this.products = products;
  }

  protected ProductListingDetails(Parcel in) {
    penCount = in.readString();
    products = in.createTypedArrayList(ProductsDetails.CREATOR);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(penCount);
    dest.writeTypedList(products);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ProductListingDetails> CREATOR =
      new Creator<ProductListingDetails>() {
        @Override
        public ProductListingDetails createFromParcel(Parcel in) {
          return new ProductListingDetails(in);
        }

        @Override
        public ProductListingDetails[] newArray(int size) {
          return new ProductListingDetails[size];
        }
      };

  public String getPenCount() {
    return penCount;
  }

  public void setPenCount(String penCount) {
    this.penCount = penCount;
  }

  public ArrayList<ProductsDetails> getProducts() {
    return products;
  }

  public void setProducts(ArrayList<ProductsDetails> products) {
    this.products = products;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
