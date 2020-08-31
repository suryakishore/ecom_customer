package com.customer.domain.model.pdp;

import com.customer.domain.model.common.FinalPriceListData;
import com.customer.domain.model.common.ImageData;
import java.util.ArrayList;

public class ProductModel {
  private PdpOfferData offers;
  private ArrayList<PdpOfferData> allOffers;
  private String brandName;
  private ArrayList<ImageData> images;
  private String parentProductId;
  private String childProductId;
  private String subCatName;
  private String currencySymbol;
  private String productName;
  private ArrayList<ColorsData> colors;
  private String subSubCatName;
  private String detailDesc;
  private ArrayList<String> highlight;
  private ArrayList<SizeData> sizes;
  private String isPrimary;
  private String catName;
  private SupplierData supplier;
  private String unitId;
  private String currency;
  private String shortDesc;
  private ArrayList<AttributesData> attributes;
  private boolean isFavorite;
  private FinalPriceListData finalPriceList;
  private ArrayList<LinkToUnitData> linkToUnit;
  private String mouUnit;
  private int discountType;
  private boolean outOfStock;
  private String mou;
  private String shareLink;
  private int availableQuantity;
  private ArrayList<VariantsData> variantsData;
  private ArrayList<SizeChartData> mSizeChartData;
  private int sellerCount;

  public ProductModel(ArrayList<VariantsData> variantsData, PdpOfferData offers,ArrayList<PdpOfferData> allOffers, String brandName,
      ArrayList<ImageData> images, String parentProductId, String childProductId, String subCatName,
      String currencySymbol, String productName, ArrayList<ColorsData> colors, String subSubCatName,
      String detailDesc, ArrayList<String> highlight, ArrayList<SizeData> sizes, String isPrimary,
      String catName, SupplierData supplier, String unitId, String currency, String shortDesc,
      ArrayList<AttributesData> attributes, boolean isFavorite, FinalPriceListData finalPriceList,
      ArrayList<LinkToUnitData> linkToUnit, String mouUnit, int discountType, boolean outOfStock,
      String mou, int availableQuantity, ArrayList<SizeChartData> sizeChartData, int sellerCount,
                      String shareLink) {
    this.offers = offers;
    this.allOffers=allOffers;
    this.brandName = brandName;
    this.images = images;
    this.parentProductId = parentProductId;
    this.childProductId = childProductId;
    this.subCatName = subCatName;
    this.currencySymbol = currencySymbol;
    this.productName = productName;
    this.colors = colors;
    this.subSubCatName = subSubCatName;
    this.detailDesc = detailDesc;
    this.highlight = highlight;
    this.sizes = sizes;
    this.mSizeChartData = sizeChartData;
    this.isPrimary = isPrimary;
    this.catName = catName;
    this.supplier = supplier;
    this.unitId = unitId;
    this.currency = currency;
    this.shortDesc = shortDesc;
    this.attributes = attributes;
    this.isFavorite = isFavorite;
    this.finalPriceList = finalPriceList;
    this.linkToUnit = linkToUnit;
    this.mouUnit = mouUnit;
    this.discountType = discountType;
    this.outOfStock = outOfStock;
    this.mou = mou;
    this.availableQuantity = availableQuantity;
    this.variantsData = variantsData;
    this.sellerCount = sellerCount;
    this.shareLink = shareLink;
  }

  public ArrayList<VariantsData> getVariantsData() {
    return variantsData;
  }

  public void setVariantsData(ArrayList<VariantsData> variantsData) {
    this.variantsData = variantsData;
  }

  public PdpOfferData getOffers() {
    return offers;
  }

  public void setOffers(PdpOfferData offers) {
    this.offers = offers;
  }

  public int getSellerCount() {
    return sellerCount;
  }

  public void setSellerCount(int sellerCount) {
    this.sellerCount = sellerCount;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public ArrayList<SizeChartData> getSizeChartData() {
    return mSizeChartData;
  }

  public void setSizeChartData(ArrayList<SizeChartData> sizeChartData) {
    this.mSizeChartData = sizeChartData;
  }

  public String getMouUnit() {
    return mouUnit;
  }

  public void setMouUnit(String mouUnit) {
    this.mouUnit = mouUnit;
  }

  public int getDiscountType() {
    return discountType;
  }

  public void setDiscountType(int discountType) {
    this.discountType = discountType;
  }

  public boolean isOutOfStock() {
    return outOfStock;
  }

  public void setOutOfStock(boolean outOfStock) {
    this.outOfStock = outOfStock;
  }

  public String getMou() {
    return mou;
  }

  public void setMou(String mou) {
    this.mou = mou;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(int availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public ArrayList<LinkToUnitData> getLinkToUnit() {
    return linkToUnit;
  }

  public void setLinkToUnit(ArrayList<LinkToUnitData> linkToUnit) {
    this.linkToUnit = linkToUnit;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public ArrayList<ImageData> getImages() {
    return images;
  }

  public void setImages(ArrayList<ImageData> images) {
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

  public ArrayList<ColorsData> getColors() {
    return colors;
  }

  public void setColors(ArrayList<ColorsData> colors) {
    this.colors = colors;
  }

  public String getSubSubCatName() {
    return subSubCatName;
  }

  public void setSubSubCatName(String subSubCatName) {
    this.subSubCatName = subSubCatName;
  }

  public String getDetailDesc() {
    return detailDesc;
  }

  public void setDetailDesc(String detailDesc) {
    this.detailDesc = detailDesc;
  }

  public ArrayList<String> getHighlight() {
    return highlight;
  }

  public void setHighlight(ArrayList<String> highlight) {
    this.highlight = highlight;
  }

  public ArrayList<SizeData> getSizes() {
    return sizes;
  }

  public void setSizes(ArrayList<SizeData> sizes) {
    this.sizes = sizes;
  }

  public String getIsPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(String isPrimary) {
    this.isPrimary = isPrimary;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public SupplierData getSupplier() {
    return supplier;
  }

  public void setSupplier(SupplierData supplier) {
    this.supplier = supplier;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getShortDesc() {
    return shortDesc;
  }

  public void setShortDesc(String shortDesc) {
    this.shortDesc = shortDesc;
  }

  public ArrayList<AttributesData> getAttributes() {
    return attributes;
  }

  public void setAttributes(ArrayList<AttributesData> attributes) {
    this.attributes = attributes;
  }

  public boolean getIsFavourite() {
    return isFavorite;
  }

  public void setIsFavourite(boolean isFavourite) {
    this.isFavorite = isFavourite;
  }

  public FinalPriceListData getFinalPriceList() {
    return finalPriceList;
  }

  public void setFinalPriceList(FinalPriceListData finalPriceList) {
    this.finalPriceList = finalPriceList;
  }

  public ArrayList<PdpOfferData> getAllOffers() {
    return allOffers;
  }

  public void setAllOffers(ArrayList<PdpOfferData> allOffers) {
    this.allOffers = allOffers;
  }

  public String getShareLink() {
    return shareLink;
  }

  public void setShareLink(String shareLink) {
    this.shareLink = shareLink;
  }
}
