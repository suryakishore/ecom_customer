package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.common.CategoryDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomePageDetails implements ValidItem, Parcelable {

  @SerializedName("offerData")
  @Expose
  private OfferDetails offerData;

  @SerializedName("brandData")
  @Expose
  private BrandDetails brandData;

  @SerializedName("bannerData")
  @Expose
  private BannerDetails bannerData;

  @SerializedName("products")
  @Expose
  private ProductDetails products;

  @SerializedName("recentView")
  @Expose
  private RecentViewDetails recentView;

  @SerializedName("categoryData")
  @Expose
  private CategoryDetails categoryData;

  public HomePageDetails(OfferDetails offerData, BrandDetails brandData, BannerDetails bannerData,
      ProductDetails products, RecentViewDetails recentView,
      CategoryDetails categoryData) {
    this.offerData = offerData;
    this.brandData = brandData;
    this.bannerData = bannerData;
    this.products = products;
    this.recentView = recentView;
    this.categoryData = categoryData;
  }

  protected HomePageDetails(Parcel in) {
    brandData = in.readParcelable(BrandDetails.class.getClassLoader());
    bannerData = in.readParcelable(BannerDetails.class.getClassLoader());
    categoryData = in.readParcelable(CategoryDetails.class.getClassLoader());
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(brandData, flags);
    dest.writeParcelable(bannerData, flags);
    dest.writeParcelable(categoryData, flags);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<HomePageDetails> CREATOR = new Creator<HomePageDetails>() {
    @Override
    public HomePageDetails createFromParcel(Parcel in) {
      return new HomePageDetails(in);
    }

    @Override
    public HomePageDetails[] newArray(int size) {
      return new HomePageDetails[size];
    }
  };

  public CategoryDetails getCategoryData() {
    return categoryData;
  }

  public void setCategoryData(CategoryDetails categoryData) {
    this.categoryData = categoryData;
  }

  public RecentViewDetails getRecentViewDetails() {
    return recentView;
  }

  public void setRecentViewDetails(RecentViewDetails recentViewDetails) {
    this.recentView = recentViewDetails;
  }

  public OfferDetails getOfferDetails() {
    return offerData;
  }

  public void setOfferDetails(OfferDetails offerDetails) {
    this.offerData = offerDetails;
  }

  public BrandDetails getBrandDetails() {
    return brandData;
  }

  public void setBrandDetails(BrandDetails brandDetails) {
    this.brandData = brandDetails;
  }

  public BannerDetails getBannerDetails() {
    return bannerData;
  }

  public void setBannerDetails(BannerDetails bannerDetails) {
    this.bannerData = bannerDetails;
  }

  public ProductDetails getProducts() {
    return products;
  }

  public void setProducts(ProductDetails products) {
    this.products = products;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
