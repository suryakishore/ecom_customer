package com.customer.domain.model.getcart;

import com.customer.domain.model.common.ImageData;
import java.util.ArrayList;

public class CartProductItemData {
  private String upcNumber;
  private String color;
  private String originalPrice;
/*
  private String noofunits;
*/
  private String discount;
  private String finalPrice;
  private String aisle;
  private String packageType;
  private String centralProductId;
  private String offerType;
  private CartDeliveryData deliveryDetails;
  private String unitId;
  private String storeName;
  private String inStock;
  private String mouData;
  private String offerTitle;
  private ImageData images;
  private String brandName;
  private QuantityData quantity;
  private String originalPriceTotal;
  private ArrayList<String> addOns;
  private String currencySymbol;
  /*
    private String packaging;
  */
  private ArrayList<CartTaxData> tax;
  private String storeId;
  private ArrayList<CartTaxData> productTaxArray;
  private String shelf;
  private String offerDiscount;
  private String isCentral;
  private String totalAppliedTaxOnProduct;
  private String discountedPrice;
  private String directions;
  private String name;
  private String offerId;
  private ArrayList<CartAttributesData> attributes;
  private String mId;
  private CartAccoutingData accounting;
  private CartOfferData offerDetails;
  private String currencyCode;
  private String offerValue;
  private int availableQuantity;
  private boolean isEnable = true;

  public CartProductItemData(String upcNumber, String color, String originalPrice,
       String discount, String finalPrice, String aisle,
      String packageType, String centralProductId, String offerType, String offerValue,
      CartDeliveryData deliveryDetails, String unitId, String storeName, String inStock,
      String offerTitle, ImageData images, String brandName,
      QuantityData quantity, String originalPriceTotal, ArrayList<String> addOns,
      String currencySymbol,
      ArrayList<CartTaxData> tax, String storeId,
      ArrayList<CartTaxData> productTaxArray, String shelf, String offerDiscount,
      String isCentral, String totalAppliedTaxOnProduct, String discountedPrice,
      String directions, String name, String offerId,
      ArrayList<CartAttributesData> attributes, String id, String currencyCode,
      CartAccoutingData accounting, CartOfferData offerDetails, int availableQuantity) {
    this.upcNumber = upcNumber;
    this.color = color;
    this.originalPrice = originalPrice;
/*
    this.noofunits = noofunits;
*/
    this.discount = discount;
    this.finalPrice = finalPrice;
    this.aisle = aisle;
    this.packageType = packageType;
    this.centralProductId = centralProductId;
    this.offerType = offerType;
    this.deliveryDetails = deliveryDetails;
    this.unitId = unitId;
    this.storeName = storeName;
    this.inStock = inStock;
    this.mouData = mouData;
    this.offerValue = offerValue;
    this.offerTitle = offerTitle;
    this.images = images;
    this.brandName = brandName;
    this.quantity = quantity;
    this.originalPriceTotal = originalPriceTotal;
    this.addOns = addOns;
    this.currencySymbol = currencySymbol;
    this.tax = tax;
    this.storeId = storeId;
    this.productTaxArray = productTaxArray;
    this.shelf = shelf;
    this.offerDiscount = offerDiscount;
    this.isCentral = isCentral;
    this.totalAppliedTaxOnProduct = totalAppliedTaxOnProduct;
    this.discountedPrice = discountedPrice;
    this.directions = directions;
    this.name = name;
    this.offerId = offerId;
    this.attributes = attributes;
    this.mId = id;
    this.currencyCode = currencyCode;
    this.accounting = accounting;
    this.offerDetails = offerDetails;
    this.availableQuantity = availableQuantity;
  }

  public CartAccoutingData getAccounting() {
    return accounting;
  }

  public void setAccounting(CartAccoutingData accounting) {
    this.accounting = accounting;
  }

  public CartOfferData getOfferDetails() {
    return offerDetails;
  }

  public void setOfferDetails(CartOfferData offerDetails) {
    this.offerDetails = offerDetails;
  }

  public String getUpcNumber() {
    return upcNumber;
  }

  public void setUpcNumber(String upcNumber) {
    this.upcNumber = upcNumber;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(String originalPrice) {
    this.originalPrice = originalPrice;
  }

  /*public String getNoofunits() {
    return noofunits;
  }

  public void setNoofunits(String noofunits) {
    this.noofunits = noofunits;
  }*/

  public String getDiscount() {
    return discount;
  }

  public void setDiscount(String discount) {
    this.discount = discount;
  }

  public String getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(String finalPrice) {
    this.finalPrice = finalPrice;
  }

  public String getOfferValue() {
    return offerValue;
  }

  public void setOfferValue(String offerValue) {
    this.offerValue = offerValue;
  }

  public String getAisle() {
    return aisle;
  }

  public void setAisle(String aisle) {
    this.aisle = aisle;
  }

  public String getPackageType() {
    return packageType;
  }

  public void setPackageType(String packageType) {
    this.packageType = packageType;
  }

  public String getCentralProductId() {
    return centralProductId;
  }

  public void setCentralProductId(String centralProductId) {
    this.centralProductId = centralProductId;
  }

  public String getOfferType() {
    return offerType;
  }

  public void setOfferType(String offerType) {
    this.offerType = offerType;
  }

  public CartDeliveryData getDeliveryDetails() {
    return deliveryDetails;
  }

  public void setDeliveryDetails(CartDeliveryData deliveryDetails) {
    this.deliveryDetails = deliveryDetails;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(int availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public String getInStock() {
    return inStock;
  }

  public void setInStock(String inStock) {
    this.inStock = inStock;
  }

  public String getMouData() {
    return mouData;
  }

  public void setMouData(String mouData) {
    this.mouData = mouData;
  }

  public String getOfferTitle() {
    return offerTitle;
  }

  public void setOfferTitle(String offerTitle) {
    this.offerTitle = offerTitle;
  }

  public ImageData getImages() {
    return images;
  }

  public void setImages(ImageData images) {
    this.images = images;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public QuantityData getQuantity() {
    return quantity;
  }

  public void setQuantity(QuantityData quantity) {
    this.quantity = quantity;
  }

  public String getOriginalPriceTotal() {
    return originalPriceTotal;
  }

  public void setOriginalPriceTotal(String originalPriceTotal) {
    this.originalPriceTotal = originalPriceTotal;
  }

  public ArrayList<String> getAddOns() {
    return addOns;
  }

  public void setAddOns(ArrayList<String> addOns) {
    this.addOns = addOns;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public ArrayList<CartTaxData> getTax() {
    return tax;
  }

  public void setTax(ArrayList<CartTaxData> tax) {
    this.tax = tax;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public ArrayList<CartTaxData> getProductTaxArray() {
    return productTaxArray;
  }

  public void setProductTaxArray(
      ArrayList<CartTaxData> productTaxArray) {
    this.productTaxArray = productTaxArray;
  }

  public String getShelf() {
    return shelf;
  }

  public void setShelf(String shelf) {
    this.shelf = shelf;
  }

  public String getOfferDiscount() {
    return offerDiscount;
  }

  public void setOfferDiscount(String offerDiscount) {
    this.offerDiscount = offerDiscount;
  }

  public String getIsCentral() {
    return isCentral;
  }

  public void setIsCentral(String isCentral) {
    this.isCentral = isCentral;
  }

  public String getTotalAppliedTaxOnProduct() {
    return totalAppliedTaxOnProduct;
  }

  public void setTotalAppliedTaxOnProduct(String totalAppliedTaxOnProduct) {
    this.totalAppliedTaxOnProduct = totalAppliedTaxOnProduct;
  }

  public String getDiscountedPrice() {
    return discountedPrice;
  }

  public void setDiscountedPrice(String discountedPrice) {
    this.discountedPrice = discountedPrice;
  }

  public String getDirections() {
    return directions;
  }

  public void setDirections(String directions) {
    this.directions = directions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public ArrayList<CartAttributesData> getAttributes() {
    return attributes;
  }

  public void setAttributes(
      ArrayList<CartAttributesData> attributes) {
    this.attributes = attributes;
  }

  public String getId() {
    return mId;
  }

  public void setId(String id) {
    this.mId = id;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public boolean isEnable() {
    return isEnable;
  }

  public void setEnable(boolean enable) {
    isEnable = enable;
  }
}
