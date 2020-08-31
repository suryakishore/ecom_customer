package com.customer.remote.http.model.response.newHome;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ListDetails implements ValidItem, Parcelable {

  @SerializedName("offers")
  @Expose
  private ArrayList<HomeOfferDetails> offers;

  @SerializedName("penCount")
  @Expose
  private String penCount;

  @SerializedName("bannerImageUrl")
  @Expose
  private String bannerImageUrl;

  @SerializedName("catName")
  @Expose
  private String catName;

  @SerializedName("imageUrl")
  @Expose
  private String imageUrl;

  @SerializedName("categoryData")
  @Expose
  private ArrayList<CategoryDetails> categoryData;

  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("type")
  @Expose
  private int type;

  public ListDetails(ArrayList<HomeOfferDetails> offers, String penCount, String bannerImageUrl,
      String catName, String imageUrl, ArrayList<CategoryDetails> categoryData,
      String id, int type) {
    this.offers = offers;
    this.penCount = penCount;
    this.bannerImageUrl = bannerImageUrl;
    this.catName = catName;
    this.imageUrl = imageUrl;
    this.categoryData = categoryData;
    this.id = id;
    this.type = type;
  }

  public ListDetails() {
  }

  protected ListDetails(Parcel in) {
    offers = in.createTypedArrayList(HomeOfferDetails.CREATOR);
    penCount = in.readString();
    bannerImageUrl = in.readString();
    catName = in.readString();
    imageUrl = in.readString();
    categoryData = in.createTypedArrayList(CategoryDetails.CREATOR);
    id = in.readString();
    type = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(offers);
    dest.writeString(penCount);
    dest.writeString(bannerImageUrl);
    dest.writeString(catName);
    dest.writeString(imageUrl);
    dest.writeTypedList(categoryData);
    dest.writeString(id);
    dest.writeInt(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ListDetails> CREATOR = new Creator<ListDetails>() {
    @Override
    public ListDetails createFromParcel(Parcel in) {
      return new ListDetails(in);
    }

    @Override
    public ListDetails[] newArray(int size) {
      return new ListDetails[size];
    }
  };

  public ArrayList<HomeOfferDetails> getOffers() {
    return offers;
  }

  public void setOffers(ArrayList<HomeOfferDetails> offers) {
    this.offers = offers;
  }

  public String getPenCount() {
    return penCount;
  }

  public void setPenCount(String penCount) {
    this.penCount = penCount;
  }

  public String getBannerImageUrl() {
    return bannerImageUrl;
  }

  public void setBannerImageUrl(String bannerImageUrl) {
    this.bannerImageUrl = bannerImageUrl;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public ArrayList<CategoryDetails> getCategoryData() {
    return categoryData;
  }

  public void setCategoryData(ArrayList<CategoryDetails> categoryData) {
    this.categoryData = categoryData;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
