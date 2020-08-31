package com.customer.remote.http.model.response.suggestions;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SuggestionItemDetails implements ValidItem , Parcelable {

  @SerializedName("images")
  @Expose
  private ArrayList<ImagesDetails> images;

  @SerializedName("storeType")
  @Expose
  private String storeType;

  @SerializedName("productId")
  @Expose
  private String productId;


  @SerializedName("childProductId")
  @Expose
  private String childProductId;
  @SerializedName("subCatName")
  @Expose
  private String subCatName;

  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;

  @SerializedName("productName")
  @Expose
  private String productName;

  @SerializedName("subSubCatName")
  @Expose
  private String subSubCatName;

  @SerializedName("catName")
  @Expose
  private String catName;

  @SerializedName("currency")
  @Expose
  private String currency;

  @SerializedName("inSection")
  @Expose
  private String inSection;

  @SerializedName("isProduct")
  @Expose
  private boolean isProduct;

  @SerializedName("brandTitle")
  @Expose
  private String brandTitle;

  @SerializedName("seqId")
  @Expose
  private int seqId;

  public SuggestionItemDetails(
      ArrayList<ImagesDetails> images, String storeType, String productId,String childProductId,
      String subCatName, String currencySymbol, String productName, String subSubCatName,
      String catName, String currency, String inSection, boolean isProduct,
      String brandTitle, int seqId) {
    this.images = images;
    this.storeType = storeType;
    this.productId = productId;
    this.subCatName = subCatName;
    this.currencySymbol = currencySymbol;
    this.productName = productName;
    this.subSubCatName = subSubCatName;
    this.catName = catName;
    this.currency = currency;
    this.inSection = inSection;
    this.isProduct = isProduct;
    this.brandTitle = brandTitle;
    this.seqId = seqId;
  }

  protected SuggestionItemDetails(Parcel in) {
    images = in.createTypedArrayList(ImagesDetails.CREATOR);
    storeType = in.readString();
    productId = in.readString();
    childProductId = in.readString();
    subCatName = in.readString();
    currencySymbol = in.readString();
    productName = in.readString();
    subSubCatName = in.readString();
    catName = in.readString();
    currency = in.readString();
    inSection = in.readString();
    isProduct = in.readByte() != 0;
    brandTitle = in.readString();
    seqId = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(images);
    dest.writeString(storeType);
    dest.writeString(productId);
    dest.writeString(childProductId);
    dest.writeString(subCatName);
    dest.writeString(currencySymbol);
    dest.writeString(productName);
    dest.writeString(subSubCatName);
    dest.writeString(catName);
    dest.writeString(currency);
    dest.writeString(inSection);
    dest.writeByte((byte) (isProduct ? 1 : 0));
    dest.writeString(brandTitle);
    dest.writeInt(seqId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SuggestionItemDetails> CREATOR =
      new Creator<SuggestionItemDetails>() {
        @Override
        public SuggestionItemDetails createFromParcel(Parcel in) {
          return new SuggestionItemDetails(in);
        }

        @Override
        public SuggestionItemDetails[] newArray(int size) {
          return new SuggestionItemDetails[size];
        }
      };

  public ArrayList<ImagesDetails> getImages() {
    return images;
  }

  public void setImages(
      ArrayList<ImagesDetails> images) {
    this.images = images;
  }

  public String getStoreType() {
    return storeType;
  }

  public void setStoreType(String storeType) {
    this.storeType = storeType;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getSubSubCatName() {
    return subSubCatName;
  }

  public void setSubSubCatName(String subSubCatName) {
    this.subSubCatName = subSubCatName;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getInSection() {
    return inSection;
  }

  public void setInSection(String inSection) {
    this.inSection = inSection;
  }

  public boolean isProduct() {
    return isProduct;
  }

  public void setProduct(boolean product) {
    isProduct = product;
  }

  public String getBrandTitle() {
    return brandTitle;
  }

  public void setBrandTitle(String brandTitle) {
    this.brandTitle = brandTitle;
  }

  public int getSeqId() {
    return seqId;
  }

  public void setSeqId(int seqId) {
    this.seqId = seqId;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
