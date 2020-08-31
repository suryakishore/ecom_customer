package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderRequest implements Parcelable {
  public static final Creator<PlaceOrderRequest> CREATOR = new Creator<PlaceOrderRequest>() {
    @Override
    public PlaceOrderRequest createFromParcel(Parcel in) {
      return new PlaceOrderRequest(in);
    }

    @Override
    public PlaceOrderRequest[] newArray(int size) {
      return new PlaceOrderRequest[size];
    }
  };
  @Expose
  @SerializedName("cartId")
  private String cartId;
  @Expose
  @SerializedName("addressId")
  private String addressId;
  @Expose
  @SerializedName("billingAddressId")
  private String billingAddressId;
  @Expose
  @SerializedName("paymentType")
  private int paymentType;
  @Expose
  @SerializedName("cardId")
  private String cardId;
  @Expose
  @SerializedName("payByWallet")
  private boolean payByWallet;
  @Expose
  @SerializedName("coupon")
  private String coupon;
  @Expose
  @SerializedName("promoId")
  private String promo_id;
  @Expose
  @SerializedName("discount")
  private double discount;
  @Expose
  @SerializedName("latitude")
  private double latitude;
  @Expose
  @SerializedName("longitude")
  private double longitude;
  @Expose
  @SerializedName("ipAddress")
  private String ipAddress;
  @Expose
  @SerializedName("extraNote")
  private String extraNote;
  @Expose
  @SerializedName("userId")
  private String userId;
  @Expose
  @SerializedName("storeType")
  private int storeType;
  @Expose
  @SerializedName("orderType")
  private int orderType;

  public PlaceOrderRequest(String cartId, String addressId, String billingAddressId,
      int paymentType, String cardId,
      boolean payByWallet, String coupon, String promoID, double discount, double latitude,
      double longitude,
      String ipAddress, String extraNote, String userId, int storeType, int orderType) {
    this.cartId = cartId;
    this.addressId = addressId;
    this.billingAddressId = billingAddressId;
    this.paymentType = paymentType;
    this.cardId = cardId;
    this.payByWallet = payByWallet;
    this.coupon = coupon;
    this.promo_id = promoID;
    this.discount = discount;
    this.latitude = latitude;
    this.longitude = longitude;
    this.ipAddress = ipAddress;
    this.extraNote = extraNote;
    this.userId = userId;
    this.storeType = storeType;
    this.orderType = orderType;
  }

  protected PlaceOrderRequest(Parcel in) {
    cartId = in.readString();
    addressId = in.readString();
    billingAddressId = in.readString();
    paymentType = in.readInt();
    cardId = in.readString();
    payByWallet = in.readByte() != 0;
    coupon = in.readString();
    discount = in.readDouble();
    latitude = in.readDouble();
    longitude = in.readDouble();
    ipAddress = in.readString();
    extraNote = in.readString();
    userId = in.readString();
    storeType = in.readInt();
    orderType = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(cartId);
    dest.writeString(addressId);
    dest.writeString(billingAddressId);
    dest.writeInt(paymentType);
    dest.writeString(cardId);
    dest.writeByte((byte) (payByWallet ? 1 : 0));
    dest.writeString(coupon);
    dest.writeString(promo_id);
    dest.writeDouble(discount);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
    dest.writeString(ipAddress);
    dest.writeString(extraNote);
    dest.writeString(userId);
    dest.writeInt(storeType);
    dest.writeInt(orderType);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getCartId() {
    return cartId;
  }

  public void setCartId(String cartId) {
    this.cartId = cartId;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getBillingAddressId() {
    return billingAddressId;
  }

  public void setBillingAddressId(String billingAddressId) {
    this.billingAddressId = billingAddressId;
  }

  public int getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(int paymentType) {
    this.paymentType = paymentType;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public boolean getPayByWallet() {
    return payByWallet;
  }

  public void setPayByWallet(boolean payByWallet) {
    this.payByWallet = payByWallet;
  }

  public String getCoupon() {
    return coupon;
  }

  public void setCoupon(String coupon) {
    this.coupon = coupon;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getExtraNote() {
    return extraNote;
  }

  public void setExtraNote(String extraNote) {
    this.extraNote = extraNote;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public int getStoreType() {
    return storeType;
  }

  public void setStoreType(int storeType) {
    this.storeType = storeType;
  }

  public int getOrderType() {
    return orderType;
  }

  public void setOrderType(int orderType) {
    this.orderType = orderType;
  }

  public String getPromo_id() {
    return promo_id;
  }

  public void setPromo_id(String promo_id) {
    this.promo_id = promo_id;
  }
}
