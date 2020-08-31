package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ProductsListDetails implements ValidItem, Parcelable {

  @SerializedName("categoryProducts")
  @Expose
  private ArrayList<CategoryProductsDetails> categoryProducts;

  @SerializedName("catName")
  @Expose
  private String catName;

  public ProductsListDetails(ArrayList<CategoryProductsDetails> categoryProducts, String catName) {
    this.categoryProducts = categoryProducts;
    this.catName = catName;
  }

  protected ProductsListDetails(Parcel in) {
    categoryProducts = in.createTypedArrayList(CategoryProductsDetails.CREATOR);
    catName = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(categoryProducts);
    dest.writeString(catName);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ProductsListDetails> CREATOR = new Creator<ProductsListDetails>() {
    @Override
    public ProductsListDetails createFromParcel(Parcel in) {
      return new ProductsListDetails(in);
    }

    @Override
    public ProductsListDetails[] newArray(int size) {
      return new ProductsListDetails[size];
    }
  };

  public ArrayList<CategoryProductsDetails> getCategoryProductDetails() {
    return categoryProducts;
  }

  public void setCategoryProductDetails(ArrayList<CategoryProductsDetails> categoryProductDetails) {
    this.categoryProducts = categoryProductDetails;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
