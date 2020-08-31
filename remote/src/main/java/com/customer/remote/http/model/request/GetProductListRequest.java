package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class GetProductListRequest implements Parcelable {
  private String catName;
  private String subCatName;
  private String subSubCatName;
  private String brandName;

  public GetProductListRequest(String catName, String subCatName, String subSubCatName,
      String brandName) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
    this.brandName = brandName;
  }

  protected GetProductListRequest(Parcel in) {
    catName = in.readString();
    subCatName = in.readString();
    subSubCatName = in.readString();
    brandName = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(catName);
    dest.writeString(subCatName);
    dest.writeString(subSubCatName);
    dest.writeString(brandName);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GetProductListRequest> CREATOR =
      new Creator<GetProductListRequest>() {
        @Override
        public GetProductListRequest createFromParcel(Parcel in) {
          return new GetProductListRequest(in);
        }

        @Override
        public GetProductListRequest[] newArray(int size) {
          return new GetProductListRequest[size];
        }
      };

  public String getSubSubCatName() {
    return subSubCatName;
  }

  public void setSubSubCatName(String subSubCatName) {
    this.subSubCatName = subSubCatName;
  }

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
}
