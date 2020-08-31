package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.common.FinalPriceListDetails;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CategoryProductsDetails implements ValidItem, Parcelable {

  @SerializedName("availableQuantity")
  @Expose
  private String availableQuantity;

  @SerializedName("brandName")
  @Expose
  private String brandName;

  @SerializedName("images")
  @Expose
  private ArrayList<ImagesDetails> images;

  @SerializedName("parentProductId")
  @Expose
  private String parentProductId;

  @SerializedName("childProductId")
  @Expose
  private String childProductId;

  @SerializedName("outOfStock")
  @Expose
  private String outOfStock;

  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;

  @SerializedName("currency")
  @Expose
  private String currency;

  @SerializedName("productName")
  @Expose
  private String productName;

  @SerializedName("finalPriceList")
  @Expose
  private FinalPriceListDetails finalPriceList;

  public CategoryProductsDetails(String availableQuantity, String brandName,
      ArrayList<ImagesDetails> images, String parentProductId,
      String childProductId, String outOfStock, String currencySymbol,
      String currency, String productName, FinalPriceListDetails finalPriceList) {
    this.availableQuantity = availableQuantity;
    this.brandName = brandName;
    this.images = images;
    this.parentProductId = parentProductId;
    this.childProductId = childProductId;
    this.outOfStock = outOfStock;
    this.currencySymbol = currencySymbol;
    this.currency = currency;
    this.productName = productName;
    this.finalPriceList = finalPriceList;
  }

  protected CategoryProductsDetails(Parcel in) {
    availableQuantity = in.readString();
    brandName = in.readString();
    images = in.createTypedArrayList(ImagesDetails.CREATOR);
    parentProductId = in.readString();
    childProductId = in.readString();
    outOfStock = in.readString();
    currencySymbol = in.readString();
    currency = in.readString();
    productName = in.readString();
    finalPriceList = in.readParcelable(FinalPriceListDetails.class.getClassLoader());
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(availableQuantity);
    dest.writeString(brandName);
    dest.writeTypedList(images);
    dest.writeString(parentProductId);
    dest.writeString(childProductId);
    dest.writeString(outOfStock);
    dest.writeString(currencySymbol);
    dest.writeString(currency);
    dest.writeString(productName);
    dest.writeParcelable(finalPriceList, flags);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CategoryProductsDetails> CREATOR =
      new Creator<CategoryProductsDetails>() {
        @Override
        public CategoryProductsDetails createFromParcel(Parcel in) {
          return new CategoryProductsDetails(in);
        }

        @Override
        public CategoryProductsDetails[] newArray(int size) {
          return new CategoryProductsDetails[size];
        }
      };

  public String getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(String availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public ArrayList<ImagesDetails> getImages() {
    return images;
  }

  public void setImages(ArrayList<ImagesDetails> images) {
    this.images = images;
  }

  public String getParentProductId() {
    return parentProductId;
  }

  public void setParentProductId(String parentProductId) {
    this.parentProductId = parentProductId;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }

  public String getOutOfStock() {
    return outOfStock;
  }

  public void setOutOfStock(String outOfStock) {
    this.outOfStock = outOfStock;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public FinalPriceListDetails getFinalPriceListDetails() {
    return finalPriceList;
  }

  public void setFinalPriceListDetails(FinalPriceListDetails finalPriceListDetails) {
    this.finalPriceList = finalPriceListDetails;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
