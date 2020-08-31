package com.customer.remote.http.model.response.getcart;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CartSellerDetails implements Parcelable {

  @Expose
  @SerializedName("targetAmtForFreeDelivery")
  private String targetAmtForFreeDelivery;

  @Expose
  @SerializedName("sellerCartValue")
  private String sellerCartValue;

  @Expose
  @SerializedName("freeDelivery")
  private String freeDelivery;

  @Expose
  @SerializedName("fullFillMentCenterId")
  private String fullFillMentCenterId;

  @Expose
  @SerializedName("minOrder")
  private String minOrder;

  @Expose
  @SerializedName("driverType")
  private String driverType;

  @Expose
  @SerializedName("products")
  private ArrayList<CartProductItemDetails> products;

  @Expose
  @SerializedName("fullfilledBy")
  private String fullfilledBy;

 /* @Expose
  @SerializedName("storeTax")
  private String storeTax;*/

 /* @Expose
  @SerializedName("phone")
  private String phone;*/

  @Expose
  @SerializedName("pickupAddress")
  private CartPickupAddressDetails pickupAddress;

  @Expose
  @SerializedName("autoDispatch")
  private String autoDispatch;

  @Expose
  @SerializedName("autoAcceptOrders")
  private String autoAcceptOrders;

  @Expose
  @SerializedName("isInventoryCheck")
  private String isInventoryCheck;

  @Expose
  @SerializedName("name")
  private String name;

  /*@Expose
  @SerializedName("logo")
  private String logo;*/

  public CartSellerDetails(String targetAmtForFreeDelivery, String sellerCartValue,
      String freeDelivery, String fullFillMentCenterId, String minOrder, String driverType,
      ArrayList<CartProductItemDetails> products, String fullfilledBy,

      CartPickupAddressDetails pickupAddress, String autoDispatch, String autoAcceptOrders,
      String isInventoryCheck, String name) {
    this.targetAmtForFreeDelivery = targetAmtForFreeDelivery;
    this.sellerCartValue = sellerCartValue;
    this.freeDelivery = freeDelivery;
    this.fullFillMentCenterId = fullFillMentCenterId;
    this.minOrder = minOrder;
    this.driverType = driverType;
    this.products = products;
    this.fullfilledBy = fullfilledBy;
/*
    this.storeTax = storeTax;
*/
/*
    this.phone = phone;
*/
    this.pickupAddress = pickupAddress;
    this.autoDispatch = autoDispatch;
    this.autoAcceptOrders = autoAcceptOrders;
    this.isInventoryCheck = isInventoryCheck;
    this.name = name;
/*
    this.logo = logo;
*/
  }

  protected CartSellerDetails(Parcel in) {
    targetAmtForFreeDelivery = in.readString();
    sellerCartValue = in.readString();
    freeDelivery = in.readString();
    fullFillMentCenterId = in.readString();
    minOrder = in.readString();
    driverType = in.readString();
    fullfilledBy = in.readString();
    autoDispatch = in.readString();
    autoAcceptOrders = in.readString();
    isInventoryCheck = in.readString();
    name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(targetAmtForFreeDelivery);
    dest.writeString(sellerCartValue);
    dest.writeString(freeDelivery);
    dest.writeString(fullFillMentCenterId);
    dest.writeString(minOrder);
    dest.writeString(driverType);
    dest.writeString(fullfilledBy);
    dest.writeString(autoDispatch);
    dest.writeString(autoAcceptOrders);
    dest.writeString(isInventoryCheck);
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CartSellerDetails> CREATOR = new Creator<CartSellerDetails>() {
    @Override
    public CartSellerDetails createFromParcel(Parcel in) {
      return new CartSellerDetails(in);
    }

    @Override
    public CartSellerDetails[] newArray(int size) {
      return new CartSellerDetails[size];
    }
  };

  public String getTargetAmtForFreeDelivery() {
    return targetAmtForFreeDelivery;
  }

  public void setTargetAmtForFreeDelivery(String targetAmtForFreeDelivery) {
    this.targetAmtForFreeDelivery = targetAmtForFreeDelivery;
  }

  public String getSellerCartValue() {
    return sellerCartValue;
  }

  public void setSellerCartValue(String sellerCartValue) {
    this.sellerCartValue = sellerCartValue;
  }

  public String getFreeDelivery() {
    return freeDelivery;
  }

  public void setFreeDelivery(String freeDelivery) {
    this.freeDelivery = freeDelivery;
  }

  public String getFullFillMentCenterId() {
    return fullFillMentCenterId;
  }

  public void setFullFillMentCenterId(String fullFillMentCenterId) {
    this.fullFillMentCenterId = fullFillMentCenterId;
  }

  public String getMinOrder() {
    return minOrder;
  }

  public void setMinOrder(String minOrder) {
    this.minOrder = minOrder;
  }

  public String getDriverType() {
    return driverType;
  }

  public void setDriverType(String driverType) {
    this.driverType = driverType;
  }

  public ArrayList<CartProductItemDetails> getProducts() {
    return products;
  }

  public void setProducts(
      ArrayList<CartProductItemDetails> products) {
    this.products = products;
  }

  public String getFullfilledBy() {
    return fullfilledBy;
  }

  public void setFullfilledBy(String fullfilledBy) {
    this.fullfilledBy = fullfilledBy;
  }

 /* public String getStoreTax() {
    return storeTax;
  }

  public void setStoreTax(String storeTax) {
    this.storeTax = storeTax;
  }
*/
 /* public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
*/
  public CartPickupAddressDetails getPickupAddress() {
    return pickupAddress;
  }

  public void setPickupAddress(
      CartPickupAddressDetails pickupAddress) {
    this.pickupAddress = pickupAddress;
  }

  public String getAutoDispatch() {
    return autoDispatch;
  }

  public void setAutoDispatch(String autoDispatch) {
    this.autoDispatch = autoDispatch;
  }

  public String getAutoAcceptOrders() {
    return autoAcceptOrders;
  }

  public void setAutoAcceptOrders(String autoAcceptOrders) {
    this.autoAcceptOrders = autoAcceptOrders;
  }

  public String getIsInventoryCheck() {
    return isInventoryCheck;
  }

  public void setIsInventoryCheck(String isInventoryCheck) {
    this.isInventoryCheck = isInventoryCheck;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

 /* public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }*/
}
