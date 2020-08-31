package com.customer.domain.model.getcart;

import java.util.ArrayList;

public class CartSellerData {
  private String targetAmtForFreeDelivery;
  private String sellerCartValue;
  private String freeDelivery;
  private String fullFillMentCenterId;
  private String minOrder;
  private String driverType;
  private ArrayList<CartProductItemData> products;
  private String fullfilledBy;
  /*
    private String storeTax;
  */
  /*
    private String phone;
  */
  private CartPickupAddressData pickupAddress;
  private String autoDispatch;
  private String autoAcceptOrders;
  private String isInventoryCheck;
  private String name;

  public CartSellerData(String targetAmtForFreeDelivery, String sellerCartValue,
      String freeDelivery, String fullFillMentCenterId, String minOrder, String driverType,
      ArrayList<CartProductItemData> products, String fullfilledBy,
      CartPickupAddressData pickupAddress, String autoDispatch,
      String autoAcceptOrders, String isInventoryCheck, String name) {
    this.targetAmtForFreeDelivery = targetAmtForFreeDelivery;
    this.sellerCartValue = sellerCartValue;
    this.freeDelivery = freeDelivery;
    this.fullFillMentCenterId = fullFillMentCenterId;
    this.minOrder = minOrder;
    this.driverType = driverType;
    this.products = products;
    this.fullfilledBy = fullfilledBy;
    this.pickupAddress = pickupAddress;
    this.autoDispatch = autoDispatch;
    this.autoAcceptOrders = autoAcceptOrders;
    this.isInventoryCheck = isInventoryCheck;
    this.name = name;
  }

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

  public ArrayList<CartProductItemData> getProducts() {
    return products;
  }

  public void setProducts(
      ArrayList<CartProductItemData> products) {
    this.products = products;
  }

  public String getFullfilledBy() {
    return fullfilledBy;
  }

  public void setFullfilledBy(String fullfilledBy) {
    this.fullfilledBy = fullfilledBy;
  }

  public CartPickupAddressData getPickupAddress() {
    return pickupAddress;
  }

  public void setPickupAddress(CartPickupAddressData pickupAddress) {
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
}
