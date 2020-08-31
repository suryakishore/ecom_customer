package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class FilterProductsRequest implements Parcelable {
  private String catName;
  private String subCatName;
  private String subSubCatName;
  private String searchQuery;
  private String brandName;

  public FilterProductsRequest(String catName, String subCatName, String subSubCatName) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
  }

  public FilterProductsRequest(String catName, String subCatName, String subSubCatName,
      String searchQuery, String brandName) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
    this.searchQuery = searchQuery;
    this.brandName = brandName;
  }

  public FilterProductsRequest(String searchQuery) {
    this.searchQuery = searchQuery;
  }

  protected FilterProductsRequest(Parcel in) {
    catName = in.readString();
    subCatName = in.readString();
    subSubCatName = in.readString();
    searchQuery = in.readString();
    brandName = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(catName);
    dest.writeString(subCatName);
    dest.writeString(subSubCatName);
    dest.writeString(searchQuery);
    dest.writeString(brandName);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<FilterProductsRequest> CREATOR =
      new Creator<FilterProductsRequest>() {
        @Override
        public FilterProductsRequest createFromParcel(Parcel in) {
          return new FilterProductsRequest(in);
        }

        @Override
        public FilterProductsRequest[] newArray(int size) {
          return new FilterProductsRequest[size];
        }
      };

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }

  public String getSubSubCatName() {
    return subSubCatName;
  }

  public void setSubSubCatName(String subSubCatName) {
    this.subSubCatName = subSubCatName;
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
  }
}
