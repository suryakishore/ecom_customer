package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ApplyPromoCodeRequest implements Parcelable {

  @Expose
  @SerializedName("email_id")
  private String email_id;
  @Expose
  @SerializedName("payment_method_type")
  private int payment_method_type;
  @Expose
  @SerializedName("service_type")
  private int service_type;
  @Expose
  @SerializedName("currency_symbol")
  private String currency_symbol;

  @Expose
  @SerializedName("promo_code")
  private String promo_code;
  @Expose
  @SerializedName("promo_id")
  private String promo_id;
  @Expose
  @SerializedName("total_purchase_value")
  private float total_purchase_value;
  @Expose
  @SerializedName("cart_id")
  private String cart_id;
  @Expose
  @SerializedName("delivery_fee")
  private float delivery_fee;
  @Expose
  @SerializedName("currency_name")
  private String currency_name;
  @Expose
  @SerializedName("user_id")
  private String user_id;
  @Expose
  @SerializedName("Category")
  private String Category;
  @Expose
  @SerializedName("region_applied_on")
  private String region_applied_on;
  @Expose
  @SerializedName("wallet_state")
  private int wallet_state;
  @Expose
  @SerializedName("currency_details")
  private String currency_details;
  @Expose
  @SerializedName("pickup_state")
  private int pickup_state;
  @Expose
  @SerializedName("products")
  private ArrayList<ApplyPromoProductRequest> products;

  public ApplyPromoCodeRequest(String email_id, int payment_method_type,
      String currency_symbol,String promo_code,String promo_id, float total_purchase_value, String cart_id,
      float delivery_fee, String currency_name, String user_id, String category,
      String region_applied_on, int wallet_state, String currency_details,
      int pickup_state, int service_type, ArrayList<ApplyPromoProductRequest> products) {
    this.email_id = email_id;
    this.payment_method_type = payment_method_type;
    this.currency_symbol = currency_symbol;
    this.promo_code = promo_code;
    this.promo_id=promo_id;
    this.total_purchase_value = total_purchase_value;
    this.cart_id = cart_id;
    this.delivery_fee = delivery_fee;
    this.currency_name = currency_name;
    this.user_id = user_id;
    Category = category;
    this.region_applied_on = region_applied_on;
    this.wallet_state = wallet_state;
    this.currency_details = currency_details;
    this.pickup_state = pickup_state;
    this.service_type = service_type;
    this.products = products;
  }

  protected ApplyPromoCodeRequest(Parcel in) {
    email_id = in.readString();
    payment_method_type = in.readInt();
    service_type = in.readInt();
    currency_symbol = in.readString();
    promo_code = in.readString();
    promo_id = in.readString();
    total_purchase_value = in.readFloat();
    cart_id = in.readString();
    delivery_fee = in.readFloat();
    currency_name = in.readString();
    user_id = in.readString();
    Category = in.readString();
    region_applied_on = in.readString();
    wallet_state = in.readInt();
    currency_details = in.readString();
    pickup_state = in.readInt();
    products = in.createTypedArrayList(ApplyPromoProductRequest.CREATOR);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(email_id);
    dest.writeInt(payment_method_type);
    dest.writeInt(service_type);
    dest.writeString(currency_symbol);
    dest.writeString(promo_code);
    dest.writeString(promo_id);
    dest.writeFloat(total_purchase_value);
    dest.writeString(cart_id);
    dest.writeFloat(delivery_fee);
    dest.writeString(currency_name);
    dest.writeString(user_id);
    dest.writeString(Category);
    dest.writeString(region_applied_on);
    dest.writeInt(wallet_state);
    dest.writeString(currency_details);
    dest.writeInt(pickup_state);
    dest.writeTypedList(products);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ApplyPromoCodeRequest> CREATOR =
      new Creator<ApplyPromoCodeRequest>() {
        @Override
        public ApplyPromoCodeRequest createFromParcel(Parcel in) {
          return new ApplyPromoCodeRequest(in);
        }

        @Override
        public ApplyPromoCodeRequest[] newArray(int size) {
          return new ApplyPromoCodeRequest[size];
        }
      };

  public String getEmail_id() {
    return email_id;
  }

  public void setEmail_id(String email_id) {
    this.email_id = email_id;
  }

  public int getPayment_method_type() {
    return payment_method_type;
  }

  public void setPayment_method_type(int payment_method_type) {
    this.payment_method_type = payment_method_type;
  }

  public String getCurrency_symbol() {
    return currency_symbol;
  }

  public void setCurrency_symbol(String currency_symbol) {
    this.currency_symbol = currency_symbol;
  }

  public String getPromo_code() {
    return promo_code;
  }

  public void setPromo_code(String promo_code) {
    this.promo_code = promo_code;
  }

  public float getTotal_purchase_value() {
    return total_purchase_value;
  }

  public void setTotal_purchase_value(float total_purchase_value) {
    this.total_purchase_value = total_purchase_value;
  }

  public String getCart_id() {
    return cart_id;
  }

  public void setCart_id(String cart_id) {
    this.cart_id = cart_id;
  }

  public float getDelivery_fee() {
    return delivery_fee;
  }

  public void setDelivery_fee(float delivery_fee) {
    this.delivery_fee = delivery_fee;
  }

  public String getCurrency_name() {
    return currency_name;
  }

  public void setCurrency_name(String currency_name) {
    this.currency_name = currency_name;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getCategory() {
    return Category;
  }

  public void setCategory(String category) {
    Category = category;
  }

  public String getRegion_applied_on() {
    return region_applied_on;
  }

  public void setRegion_applied_on(String region_applied_on) {
    this.region_applied_on = region_applied_on;
  }

  public int getWallet_state() {
    return wallet_state;
  }

  public void setWallet_state(int wallet_state) {
    this.wallet_state = wallet_state;
  }

  public String getCurrency_details() {
    return currency_details;
  }

  public void setCurrency_details(String currency_details) {
    this.currency_details = currency_details;
  }

  public int getPickup_state() {
    return pickup_state;
  }

  public void setPickup_state(int pickup_state) {
    this.pickup_state = pickup_state;
  }

  public ArrayList<ApplyPromoProductRequest> getProducts() {
    return products;
  }

  public void setProducts(
      ArrayList<ApplyPromoProductRequest> products) {
    this.products = products;
  }

  public int getService_type() {
    return service_type;
  }

  public void setService_type(int service_type) {
    this.service_type = service_type;
  }

  public String getPromo_id() {
    return promo_id;
  }

  public void setPromo_id(String promo_id) {
    this.promo_id = promo_id;
  }

  @Override
  public String toString() {
    return "ApplyPromoCodeRequest{" +
        "email_id='" + email_id + '\'' +
        ", payment_method_type=" + payment_method_type +
        ", currency_symbol='" + currency_symbol + '\'' +
        ", promo_code='" + promo_code + '\'' +
        ", total_purchase_value=" + total_purchase_value +
        ", cart_id='" + cart_id + '\'' +
        ", delivery_fee=" + delivery_fee +
        ", currency_name='" + currency_name + '\'' +
        ", user_id='" + user_id + '\'' +
        ", Category='" + Category + '\'' +
        ", region_applied_on='" + region_applied_on + '\'' +
        ", wallet_state=" + wallet_state +
        ", currency_details='" + currency_details + '\'' +
        ", pickup_state=" + pickup_state +
        '}';
  }
}
