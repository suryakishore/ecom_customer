package com.customer.remote.http.model.response.recentlyviewed;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.common.ProductsDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class RecentlyViewedDetails implements Parcelable {
  @Expose
  @SerializedName("catName")
  private String catName;

  @Expose
  @SerializedName("categoryData")
  private ArrayList<ProductsDetails> categoryData;

  @Expose
  @SerializedName("penCount")
  private int penCount;

  @Expose
  @SerializedName("seqId")
  private int seqId;

  public RecentlyViewedDetails(String catName,
      ArrayList<ProductsDetails> categoryData, int penCount, int seqId) {
    this.catName = catName;
    this.categoryData = categoryData;
    this.penCount = penCount;
    this.seqId = seqId;
  }

  protected RecentlyViewedDetails(Parcel in) {
    catName = in.readString();
    categoryData = in.createTypedArrayList(ProductsDetails.CREATOR);
    penCount = in.readInt();
    seqId = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(catName);
    dest.writeTypedList(categoryData);
    dest.writeInt(penCount);
    dest.writeInt(seqId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<RecentlyViewedDetails> CREATOR =
      new Creator<RecentlyViewedDetails>() {
        @Override
        public RecentlyViewedDetails createFromParcel(Parcel in) {
          return new RecentlyViewedDetails(in);
        }

        @Override
        public RecentlyViewedDetails[] newArray(int size) {
          return new RecentlyViewedDetails[size];
        }
      };

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public ArrayList<ProductsDetails> getCategoryData() {
    return categoryData;
  }

  public void setCategoryData(
      ArrayList<ProductsDetails> categoryData) {
    this.categoryData = categoryData;
  }

  public int getPenCount() {
    return penCount;
  }

  public void setPenCount(int penCount) {
    this.penCount = penCount;
  }

  public int getSeqId() {
    return seqId;
  }

  public void setSeqId(int seqId) {
    this.seqId = seqId;
  }
}
