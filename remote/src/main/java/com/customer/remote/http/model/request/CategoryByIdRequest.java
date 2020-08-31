package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryByIdRequest implements Parcelable {

  private String categoryId;
  private String subCategoryId;
  private int from;
  private int to;

  public CategoryByIdRequest(String categoryId, String subCategoryId) {
    this.categoryId = categoryId;
    this.subCategoryId = subCategoryId;
  }

  public CategoryByIdRequest(int from, int to) {
    this.from = from;
    this.to = to;
  }

  protected CategoryByIdRequest(Parcel in) {
    categoryId = in.readString();
    subCategoryId = in.readString();
    from = in.readInt();
    to = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(categoryId);
    dest.writeString(subCategoryId);
    dest.writeInt(from);
    dest.writeInt(to);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CategoryByIdRequest> CREATOR = new Creator<CategoryByIdRequest>() {
    @Override
    public CategoryByIdRequest createFromParcel(Parcel in) {
      return new CategoryByIdRequest(in);
    }

    @Override
    public CategoryByIdRequest[] newArray(int size) {
      return new CategoryByIdRequest[size];
    }
  };

  public int getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getSubCategoryId() {
    return subCategoryId;
  }

  public void setSubCategoryId(String subCategoryId) {
    this.subCategoryId = subCategoryId;
  }
}
