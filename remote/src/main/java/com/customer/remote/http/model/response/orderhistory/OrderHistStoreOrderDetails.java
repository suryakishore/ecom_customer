package com.customer.remote.http.model.response.orderhistory;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.getaddress.AddressListItemDetails;
import com.customer.remote.http.model.response.getcart.CartPickupAddressDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class OrderHistStoreOrderDetails implements Parcelable {

  @Expose
  @SerializedName("requestedFor")
  private String requestedFor;
  @Expose
  @SerializedName("orderType")
  private String orderType;
  @Expose
  @SerializedName("paymentTypeText")
  private String paymentTypeText;
  @Expose
  @SerializedName("driverTypeId")
  private String driverTypeId;
  @Expose
  @SerializedName("timestamps")
  private OrderTimeStampDetails timestamps;
  @Expose
  @SerializedName("createdTimeStamp")
  private String createdTimeStamp;
  @Expose
  @SerializedName("orderTypeMsg")
  private String orderTypeMsg;
  @Expose
  @SerializedName("accounting")
  private OrderHistoryAccounting accounting;
  @Expose
  @SerializedName("paymentType")
  private String paymentType;
  @Expose
  @SerializedName("products")
  private ArrayList<OrderHistProductDetails> products;
  @Expose
  @SerializedName("requestedForTimeStamp")
  private String requestedForTimeStamp;
  @Expose
  @SerializedName("autoDispatch")
  private String autoDispatch;
  @Expose
  @SerializedName("deliveryAddress")
  private CartPickupAddressDetails deliveryAddress;
  @Expose
  @SerializedName("storePhone")
  private String storePhone;
  @Expose
  @SerializedName("customerId")
  private String customerId;
  @Expose
  @SerializedName("storeName")
  private String storeName;
  @Expose
  @SerializedName("customerDetails")
  private OrderHistoryCustomerDetails customerDetails;
  @Expose
  @SerializedName("freeDeliveryLimitPerStore")
  private String freeDeliveryLimitPerStore;
  @Expose
  @SerializedName("payByWallet")
  private String payByWallet;
  @Expose
  @SerializedName("storeType")
  private String storeType;
  @Expose
  @SerializedName("cartId")
  private String cartId;
  @Expose
  @SerializedName("storeId")
  private String storeId;
  @Expose
  @SerializedName("driverType")
  private String driverType;
  @Expose
  @SerializedName("masterOrderId")
  private String masterOrderId;
  @Expose
  @SerializedName("storeTypeMsg")
  private String storeTypeMsg;
  @Expose
  @SerializedName("createdDate")
  private String createdDate;
  @Expose
  @SerializedName("storeOrderId")
  private String storeOrderId;
  @Expose
  @SerializedName("autoAcceptOrders")
  private String autoAcceptOrders;
  @Expose
  @SerializedName("pickupAddress")
  private CartPickupAddressDetails pickupAddress;
  @Expose
  @SerializedName("storeLogo")
  private StoreLogoDetails storeLogo;
  @Expose
  @SerializedName("_id")
  private String _id;
  @Expose
  @SerializedName("billingAddress")
  private AddressListItemDetails billingAddress;
  @Expose
  @SerializedName("storePlan")
  private OrderHistoryPlanDetails storePlan;
  @Expose
  @SerializedName("status")
  private OrderStatusDetails status;

  public OrderHistStoreOrderDetails(String requestedFor, String orderType,
      String paymentTypeText, String driverTypeId,
      OrderTimeStampDetails timestamps,
      String createdTimeStamp, String orderTypeMsg,
      OrderHistoryAccounting accounting, String paymentType,
      ArrayList<OrderHistProductDetails> products, String requestedForTimeStamp,
      String autoDispatch,
      CartPickupAddressDetails deliveryAddress, String storePhone, String customerId,
      String storeName,
      OrderHistoryCustomerDetails customerDetails, String freeDeliveryLimitPerStore,
      String payByWallet, String storeType, String cartId, String storeId,
      String driverType, String masterOrderId, String storeTypeMsg, String createdDate,
      String storeOrderId, String autoAcceptOrders,
      CartPickupAddressDetails pickupAddress,
      StoreLogoDetails storeLogo, String _id,
      AddressListItemDetails billingAddress,
      OrderHistoryPlanDetails storePlan,
      OrderStatusDetails status) {
    this.requestedFor = requestedFor;
    this.orderType = orderType;
    this.paymentTypeText = paymentTypeText;
    this.driverTypeId = driverTypeId;
    this.timestamps = timestamps;
    this.createdTimeStamp = createdTimeStamp;
    this.orderTypeMsg = orderTypeMsg;
    this.accounting = accounting;
    this.paymentType = paymentType;
    this.products = products;
    this.requestedForTimeStamp = requestedForTimeStamp;
    this.autoDispatch = autoDispatch;
    this.deliveryAddress = deliveryAddress;
    this.storePhone = storePhone;
    this.customerId = customerId;
    this.storeName = storeName;
    this.customerDetails = customerDetails;
    this.freeDeliveryLimitPerStore = freeDeliveryLimitPerStore;
    this.payByWallet = payByWallet;
    this.storeType = storeType;
    this.cartId = cartId;
    this.storeId = storeId;
    this.driverType = driverType;
    this.masterOrderId = masterOrderId;
    this.storeTypeMsg = storeTypeMsg;
    this.createdDate = createdDate;
    this.storeOrderId = storeOrderId;
    this.autoAcceptOrders = autoAcceptOrders;
    this.pickupAddress = pickupAddress;
    this.storeLogo = storeLogo;
    this._id = _id;
    this.billingAddress = billingAddress;
    this.storePlan = storePlan;
    this.status = status;
  }

  protected OrderHistStoreOrderDetails(Parcel in) {
    requestedFor = in.readString();
    orderType = in.readString();
    paymentTypeText = in.readString();
    driverTypeId = in.readString();
    createdTimeStamp = in.readString();
    orderTypeMsg = in.readString();
    accounting = in.readParcelable(OrderHistoryAccounting.class.getClassLoader());
    paymentType = in.readString();
    products = in.createTypedArrayList(OrderHistProductDetails.CREATOR);
    requestedForTimeStamp = in.readString();
    autoDispatch = in.readString();
    storePhone = in.readString();
    customerId = in.readString();
    storeName = in.readString();
    customerDetails = in.readParcelable(OrderHistoryCustomerDetails.class.getClassLoader());
    freeDeliveryLimitPerStore = in.readString();
    payByWallet = in.readString();
    storeType = in.readString();
    cartId = in.readString();
    storeId = in.readString();
    driverType = in.readString();
    masterOrderId = in.readString();
    storeTypeMsg = in.readString();
    createdDate = in.readString();
    storeOrderId = in.readString();
    autoAcceptOrders = in.readString();
    _id = in.readString();
    storePlan = in.readParcelable(OrderHistoryPlanDetails.class.getClassLoader());
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(requestedFor);
    dest.writeString(orderType);
    dest.writeString(paymentTypeText);
    dest.writeString(driverTypeId);
    dest.writeString(createdTimeStamp);
    dest.writeString(orderTypeMsg);
    dest.writeParcelable(accounting, flags);
    dest.writeString(paymentType);
    dest.writeTypedList(products);
    dest.writeString(requestedForTimeStamp);
    dest.writeString(autoDispatch);
    dest.writeString(storePhone);
    dest.writeString(customerId);
    dest.writeString(storeName);
    dest.writeParcelable(customerDetails, flags);
    dest.writeString(freeDeliveryLimitPerStore);
    dest.writeString(payByWallet);
    dest.writeString(storeType);
    dest.writeString(cartId);
    dest.writeString(storeId);
    dest.writeString(driverType);
    dest.writeString(masterOrderId);
    dest.writeString(storeTypeMsg);
    dest.writeString(createdDate);
    dest.writeString(storeOrderId);
    dest.writeString(autoAcceptOrders);
    dest.writeString(_id);
    dest.writeParcelable(storePlan, flags);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<OrderHistStoreOrderDetails> CREATOR =
      new Creator<OrderHistStoreOrderDetails>() {
        @Override
        public OrderHistStoreOrderDetails createFromParcel(Parcel in) {
          return new OrderHistStoreOrderDetails(in);
        }

        @Override
        public OrderHistStoreOrderDetails[] newArray(int size) {
          return new OrderHistStoreOrderDetails[size];
        }
      };

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

  public String getDriverTypeId() {
    return driverTypeId;
  }

  public void setDriverTypeId(String driverTypeId) {
    this.driverTypeId = driverTypeId;
  }

  public OrderTimeStampDetails getTimestamps() {
    return timestamps;
  }

  public void setTimestamps(
      OrderTimeStampDetails timestamps) {
    this.timestamps = timestamps;
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

  public OrderHistoryAccounting getAccounting() {
    return accounting;
  }

  public void setAccounting(
      OrderHistoryAccounting accounting) {
    this.accounting = accounting;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public ArrayList<OrderHistProductDetails> getProducts() {
    return products;
  }

  public void setProducts(
      ArrayList<OrderHistProductDetails> products) {
    this.products = products;
  }

  public String getRequestedForTimeStamp() {
    return requestedForTimeStamp;
  }

  public void setRequestedForTimeStamp(String requestedForTimeStamp) {
    this.requestedForTimeStamp = requestedForTimeStamp;
  }

  public String getAutoDispatch() {
    return autoDispatch;
  }

  public void setAutoDispatch(String autoDispatch) {
    this.autoDispatch = autoDispatch;
  }

  public CartPickupAddressDetails getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(
      CartPickupAddressDetails deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public String getStorePhone() {
    return storePhone;
  }

  public void setStorePhone(String storePhone) {
    this.storePhone = storePhone;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public OrderHistoryCustomerDetails getCustomerDetails() {
    return customerDetails;
  }

  public void setCustomerDetails(
      OrderHistoryCustomerDetails customerDetails) {
    this.customerDetails = customerDetails;
  }

  public String getFreeDeliveryLimitPerStore() {
    return freeDeliveryLimitPerStore;
  }

  public void setFreeDeliveryLimitPerStore(String freeDeliveryLimitPerStore) {
    this.freeDeliveryLimitPerStore = freeDeliveryLimitPerStore;
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

  public String getCartId() {
    return cartId;
  }

  public void setCartId(String cartId) {
    this.cartId = cartId;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getDriverType() {
    return driverType;
  }

  public void setDriverType(String driverType) {
    this.driverType = driverType;
  }

  public String getMasterOrderId() {
    return masterOrderId;
  }

  public void setMasterOrderId(String masterOrderId) {
    this.masterOrderId = masterOrderId;
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

  public String getStoreOrderId() {
    return storeOrderId;
  }

  public void setStoreOrderId(String storeOrderId) {
    this.storeOrderId = storeOrderId;
  }

  public String getAutoAcceptOrders() {
    return autoAcceptOrders;
  }

  public void setAutoAcceptOrders(String autoAcceptOrders) {
    this.autoAcceptOrders = autoAcceptOrders;
  }

  public CartPickupAddressDetails getPickupAddress() {
    return pickupAddress;
  }

  public void setPickupAddress(
      CartPickupAddressDetails pickupAddress) {
    this.pickupAddress = pickupAddress;
  }

  public StoreLogoDetails getStoreLogo() {
    return storeLogo;
  }

  public void setStoreLogo(StoreLogoDetails storeLogo) {
    this.storeLogo = storeLogo;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public AddressListItemDetails getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(
      AddressListItemDetails billingAddress) {
    this.billingAddress = billingAddress;
  }

  public OrderHistoryPlanDetails getStorePlan() {
    return storePlan;
  }

  public void setStorePlan(
      OrderHistoryPlanDetails storePlan) {
    this.storePlan = storePlan;
  }

  public OrderStatusDetails getStatus() {
    return status;
  }

  public void setStatus(OrderStatusDetails status) {
    this.status = status;
  }
}
