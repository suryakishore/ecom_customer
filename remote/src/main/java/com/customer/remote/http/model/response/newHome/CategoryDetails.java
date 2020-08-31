package com.customer.remote.http.model.response.newHome;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.common.FinalPriceListDetails;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.customer.remote.http.model.response.pdp.Supplier;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CategoryDetails implements ValidItem, Parcelable {

  @SerializedName("subCategoryName")
  @Expose
  private String subCategoryName;

  @SerializedName("penCount")
  @Expose
  private int penCount;

  @SerializedName("productCount")
  @Expose
  private int productCount;

  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("imageUrl")
  @Expose
  private String imageUrl;

  @SerializedName("logo")
  @Expose
  private String logo;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("bannerImage")
  @Expose
  private String bannerImage;

  @SerializedName("offerdescription")
  @Expose
  private String offerdescription;

  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;

  @SerializedName("currency")
  @Expose
  private String currency;

  @SerializedName("offerId")
  @Expose
  private String offerId;

  @SerializedName("offername")
  @Expose
  private String offername;
  @SerializedName("offerName")
  @Expose
  private String offerName;

  @SerializedName("catName")
  @Expose
  private String catName;

  @SerializedName("bannerImageUrl")
  @Expose
  private String bannerImageUrl;

  @SerializedName("images")
  @Expose
  private ArrayList<ImagesDetails> images;

  @SerializedName("mobimages")
  @Expose
  private ImagesDetails mobimages;

  @SerializedName("discountType")
  @Expose
  private int discountType;

  @SerializedName("parentProductId")
  @Expose
  private String parentProductId;

  @SerializedName("availableQuantity")
  @Expose
  private String availableQuantity;

  @SerializedName("childProductId")
  @Expose
  private String childProductId;

  @SerializedName("discountPrice")
  @Expose
  private double discountPrice;

  @SerializedName("offers")
  @Expose
  private CatOfferDetails offers;

  @SerializedName("supplier")
  @Expose
  private Supplier supplier;

  @SerializedName("unitId")
  @Expose
  private String unitId;

  @SerializedName("outOfStock")
  @Expose
  private boolean outOfStock;

  @SerializedName("finalPriceList")
  @Expose
  private FinalPriceListDetails finalPriceList;

  @SerializedName("productName")
  @Expose
  private String productName;

  @SerializedName("brandName")
  @Expose
  private String brandName;

  @SerializedName("imageMobile")
  @Expose
  private String imageMobile;

  @SerializedName("type")
  @Expose
  private int type;

  @SerializedName("subCatName")
  @Expose
  private String subCatName;

  @SerializedName("TotalStarRating")
  @Expose
  private int TotalStarRating;

  public CategoryDetails(String subCategoryName, int penCount, int productCount,
      String id, String imageUrl, String logo,
      String name, String bannerImage, String offerdescription,
      String currencySymbol, String currency, String offerId,
      String offername, String catName, String bannerImageUrl,
      ArrayList<ImagesDetails> images, int discountType,
      String parentProductId, String availableQuantity,
      String childProductId, double discountPrice, CatOfferDetails offers,
      Supplier supplier,
      String unitId, boolean outOfStock, FinalPriceListDetails finalPriceList,
      ImagesDetails mobimages, String productName,
      String brandName, String imageMobile, String subCatName, int type, int TotalStarRating,String offerName) {
    this.subCategoryName = subCategoryName;
    this.penCount = penCount;
    this.productCount = productCount;
    this.id = id;
    this.imageUrl = imageUrl;
    this.logo = logo;
    this.name = name;
    this.bannerImage = bannerImage;
    this.offerdescription = offerdescription;
    this.currencySymbol = currencySymbol;
    this.currency = currency;
    this.offerId = offerId;
    this.offername = offername;
    this.catName = catName;
    this.bannerImageUrl = bannerImageUrl;
    this.images = images;
    this.discountType = discountType;
    this.parentProductId = parentProductId;
    this.availableQuantity = availableQuantity;
    this.childProductId = childProductId;
    this.discountPrice = discountPrice;
    this.offers = offers;
    this.supplier = supplier;
    this.unitId = unitId;
    this.outOfStock = outOfStock;
    this.finalPriceList = finalPriceList;
    this.mobimages = mobimages;
    this.productName = productName;
    this.brandName = brandName;
    this.imageMobile = imageMobile;
    this.type = type;
    this.subCatName = subCatName;
    this.TotalStarRating = TotalStarRating;
    this.offerName=offerName;
  }

  protected CategoryDetails(Parcel in) {
    subCategoryName = in.readString();
    penCount = in.readInt();
    productCount = in.readInt();
    id = in.readString();
    imageUrl = in.readString();
    logo = in.readString();
    name = in.readString();
    bannerImage = in.readString();
    offerdescription = in.readString();
    currencySymbol = in.readString();
    currency = in.readString();
    offerId = in.readString();
    offername = in.readString();
    offerName = in.readString();
    catName = in.readString();
    bannerImageUrl = in.readString();
    images = in.createTypedArrayList(ImagesDetails.CREATOR);
    mobimages = in.readParcelable(ImagesDetails.class.getClassLoader());
    discountType = in.readInt();
    parentProductId = in.readString();
    availableQuantity = in.readString();
    childProductId = in.readString();
    discountPrice = in.readDouble();
    unitId = in.readString();
    outOfStock = in.readByte() != 0;
    finalPriceList = in.readParcelable(FinalPriceListDetails.class.getClassLoader());
    productName = in.readString();
    brandName = in.readString();
    imageMobile = in.readString();
    type = in.readInt();
    subCatName = in.readString();
    TotalStarRating = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(subCategoryName);
    dest.writeInt(penCount);
    dest.writeInt(productCount);
    dest.writeString(id);
    dest.writeString(imageUrl);
    dest.writeString(logo);
    dest.writeString(name);
    dest.writeString(bannerImage);
    dest.writeString(offerdescription);
    dest.writeString(currencySymbol);
    dest.writeString(currency);
    dest.writeString(offerId);
    dest.writeString(offername);
    dest.writeString(offerName);
    dest.writeString(catName);
    dest.writeString(bannerImageUrl);
    dest.writeTypedList(images);
    dest.writeParcelable(mobimages, flags);
    dest.writeInt(discountType);
    dest.writeString(parentProductId);
    dest.writeString(availableQuantity);
    dest.writeString(childProductId);
    dest.writeDouble(discountPrice);
    dest.writeString(unitId);
    dest.writeByte((byte) (outOfStock ? 1 : 0));
    dest.writeParcelable(finalPriceList, flags);
    dest.writeString(productName);
    dest.writeString(brandName);
    dest.writeString(imageMobile);
    dest.writeInt(type);
    dest.writeString(subCatName);
    dest.writeInt(TotalStarRating);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CategoryDetails> CREATOR = new Creator<CategoryDetails>() {
    @Override
    public CategoryDetails createFromParcel(Parcel in) {
      return new CategoryDetails(in);
    }

    @Override
    public CategoryDetails[] newArray(int size) {
      return new CategoryDetails[size];
    }
  };

  public int getTotalStarRating() {
    return TotalStarRating;
  }

  public void setTotalStarRating(int totalStarRating) {
    TotalStarRating = totalStarRating;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }

  public String getImageMobile() {
    return imageMobile;
  }

  public void setImageMobile(String imageMobile) {
    this.imageMobile = imageMobile;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public ImagesDetails getMobimages() {
    return mobimages;
  }

  public void setMobimages(ImagesDetails mobimages) {
    this.mobimages = mobimages;
  }

  public String getSubCategoryName() {
    return subCategoryName;
  }

  public void setSubCategoryName(String subCategoryName) {
    this.subCategoryName = subCategoryName;
  }

  public int getPenCount() {
    return penCount;
  }

  public void setPenCount(int penCount) {
    this.penCount = penCount;
  }

  public int getProductCount() {
    return productCount;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBannerImage() {
    return bannerImage;
  }

  public void setBannerImage(String bannerImage) {
    this.bannerImage = bannerImage;
  }

  public String getOfferdescription() {
    return offerdescription;
  }

  public void setOfferdescription(String offerdescription) {
    this.offerdescription = offerdescription;
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

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getOffername() {
    return offername;
  }

  public void setOffername(String offername) {
    this.offername = offername;
  }

  public String getOfferName() {
    return offerName;
  }

  public void setOfferName(String offerName) {
    this.offerName = offerName;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getBannerImageUrl() {
    return bannerImageUrl;
  }

  public void setBannerImageUrl(String bannerImageUrl) {
    this.bannerImageUrl = bannerImageUrl;
  }

  public ArrayList<ImagesDetails> getImages() {
    return images;
  }

  public void setImages(ArrayList<ImagesDetails> images) {
    this.images = images;
  }

  public int getDiscountType() {
    return discountType;
  }

  public void setDiscountType(int discountType) {
    this.discountType = discountType;
  }

  public String getParentProductId() {
    return parentProductId;
  }

  public void setParentProductId(String parentProductId) {
    this.parentProductId = parentProductId;
  }

  public String getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(String availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }

  public double getDiscountPrice() {
    return discountPrice;
  }

  public void setDiscountPrice(double discountPrice) {
    this.discountPrice = discountPrice;
  }

  public CatOfferDetails getOffers() {
    return offers;
  }

  public void setOffers(CatOfferDetails offers) {
    this.offers = offers;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public boolean isOutOfStock() {
    return outOfStock;
  }

  public void setOutOfStock(boolean outOfStock) {
    this.outOfStock = outOfStock;
  }

  public FinalPriceListDetails getFinalPriceList() {
    return finalPriceList;
  }

  public void setFinalPriceList(FinalPriceListDetails finalPriceList) {
    this.finalPriceList = finalPriceList;
  }

  @Override
  public Boolean isValid() {
    return true;
  }

}
