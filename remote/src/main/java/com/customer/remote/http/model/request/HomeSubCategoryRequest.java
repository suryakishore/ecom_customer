package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeSubCategoryRequest implements Parcelable {

  private int size;
  private int limit;

  public HomeSubCategoryRequest(int size, int limit) {
    this.size = size;
    this.limit = limit;
  }

  protected HomeSubCategoryRequest(Parcel in) {
    size = in.readInt();
    limit = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(size);
    dest.writeInt(limit);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<HomeSubCategoryRequest> CREATOR =
      new Creator<HomeSubCategoryRequest>() {
        @Override
        public HomeSubCategoryRequest createFromParcel(Parcel in) {
          return new HomeSubCategoryRequest(in);
        }

        @Override
        public HomeSubCategoryRequest[] newArray(int size) {
          return new HomeSubCategoryRequest[size];
        }
      };

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
