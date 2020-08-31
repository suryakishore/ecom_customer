package com.customer.domain.model.orderdetails;

import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.model.orderhistory.OrderHistoryAccountingData;
import com.customer.domain.model.orderhistory.OrderStatusData;
import com.customer.domain.model.tracking.TrackingItemData;
import java.util.ArrayList;

public class MasterOrderData {
  private String requestedFor;
  private String orderType;
  private String paymentTypeText;
  private String orderId;
  private String createdTimeStamp;
  private String orderTypeMsg;
  private OrderHistoryAccountingData accounting;
  private String paymentType;
  private String requestedForTimeStamp;
  private AddressListItemData deliveryAddress;
  private String customerId;
  private String payByWallet;
  private String storeType;
  private String coupon;
  private String cartId;
  private ArrayList<OrderHistProductData> products;
  private ArrayList<OrderData> storeOrders;
  private String storeName;
  private ArrayList<String> packingDetails;
  private String storeTypeMsg;
  private String createdDate;
  private String id;
  private AddressListItemData billingAddress;
  private OrderStatusData status;
  private ArrayList<TrackingItemData> orderStatus;

  public MasterOrderData(String requestedFor, String orderType, String paymentTypeText,
      String orderId, String createdTimeStamp, String orderTypeMsg,
      OrderHistoryAccountingData accounting, String paymentType,
      String requestedForTimeStamp,
      AddressListItemData deliveryAddress, String customerId, String payByWallet,
      String storeType, String coupon, String cartId,
      ArrayList<OrderData> storeOrders, ArrayList<String> packingDetails,
      String storeTypeMsg, String createdDate, String id,
      AddressListItemData billingAddress,
      OrderStatusData status, ArrayList<OrderHistProductData> products, String storeName,
      ArrayList<TrackingItemData> orderStatus) {
    this.requestedFor = requestedFor;
    this.orderType = orderType;
    this.paymentTypeText = paymentTypeText;
    this.orderId = orderId;
    this.createdTimeStamp = createdTimeStamp;
    this.orderTypeMsg = orderTypeMsg;
    this.accounting = accounting;
    this.paymentType = paymentType;
    this.requestedForTimeStamp = requestedForTimeStamp;
    this.deliveryAddress = deliveryAddress;
    this.customerId = customerId;
    this.payByWallet = payByWallet;
    this.storeType = storeType;
    this.coupon = coupon;
    this.cartId = cartId;
    this.storeOrders = storeOrders;
    this.packingDetails = packingDetails;
    this.storeTypeMsg = storeTypeMsg;
    this.createdDate = createdDate;
    this.id = id;
    this.billingAddress = billingAddress;
    this.status = status;
    this.products = products;
    this.storeName = storeName;
    this.orderStatus = orderStatus;
  }

  public String getRequestedFor() {
    return requestedFor;
  }

  public void setRequestedFor(String requestedFor) {
    this.requestedFor = requestedFor;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getPaymentTypeText() {
    return paymentTypeText;
  }

  public void setPaymentTypeText(String paymentTypeText) {
    this.paymentTypeText = paymentTypeText;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getCreatedTimeStamp() {
    return createdTimeStamp;
  }

  public void setCreatedTimeStamp(String createdTimeStamp) {
    this.createdTimeStamp = createdTimeStamp;
  }

  public String getOrderTypeMsg() {
    return orderTypeMsg;
  }

  public void setOrderTypeMsg(String orderTypeMsg) {
    this.orderTypeMsg = orderTypeMsg;
  }

  public OrderHistoryAccountingData getAccounting() {
    return accounting;
  }

  public void setAccounting(OrderHistoryAccountingData accounting) {
    this.accounting = accounting;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public String getRequestedForTimeStamp() {
    return requestedForTimeStamp;
  }

  public void setRequestedForTimeStamp(String requestedForTimeStamp) {
    this.requestedForTimeStamp = requestedForTimeStamp;
  }

  public AddressListItemData getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(AddressListItemData deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getPayByWallet() {
    return payByWallet;
  }

  public void setPayByWallet(String payByWallet) {
    this.payByWallet = payByWallet;
  }

  public String getStoreType() {
    return storeType;
  }

  public void setStoreType(String storeType) {
    this.storeType = storeType;
  }

  public String getCoupon() {
    return coupon;
  }

  public void setCoupon(String coupon) {
    this.coupon = coupon;
  }

  public String getCartId() {
    return cartId;
  }

  public void setCartId(String cartId) {
    this.cartId = cartId;
  }

  public ArrayList<OrderData> getStoreOrders() {
    return storeOrders;
  }

  public void setStoreOrders(
      ArrayList<OrderData> storeOrders) {
    this.storeOrders = storeOrders;
  }

  public ArrayList<String> getPackingDetails() {
    return packingDetails;
  }

  public void setPackingDetails(ArrayList<String> packingDetails) {
    this.packingDetails = packingDetails;
  }

  public String getStoreTypeMsg() {
    return storeTypeMsg;
  }

  public void setStoreTypeMsg(String storeTypeMsg) {
    this.storeTypeMsg = storeTypeMsg;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AddressListItemData getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(AddressListItemData billingAddress) {
    this.billingAddress = billingAddress;
  }

  public ArrayList<OrderHistProductData> getProducts() {
    return products;
  }

  public void setProducts(
      ArrayList<OrderHistProductData> products) {
    this.products = products;
  }

  public OrderStatusData getStatus() {
    return status;
  }

  public void setStatus(OrderStatusData status) {
    this.status = status;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public ArrayList<TrackingItemData> getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(
      ArrayList<TrackingItemData> orderStatus) {
    this.orderStatus = orderStatus;
  }
}
