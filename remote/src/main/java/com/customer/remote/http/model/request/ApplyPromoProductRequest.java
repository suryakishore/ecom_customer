package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.getcart.QuantityDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ApplyPromoProductRequest implements Parcelable {

  @Expose
  @SerializedName("product_id")
  private String product_id;
  @Expose
  @SerializedName("name")
  private String name;
  @Expose
  @SerializedName("brandName")
  private String brand_name;
  @Expose
  @SerializedName("price")
  private float price;
  @Expose
  @SerializedName("delivery_fee")
  private float delivery_fee;
  @Expose
  @SerializedName("category_id")
  private ArrayList<String> category_id;

  @Expose
  @SerializedName("centralproduct_id")
  private String centralproduct_id;
  @Expose
  @SerializedName("tax")
  private ArrayList<String> tax;
  @Expose
  @SerializedName("quantity")
  private QuantityDetails quantity;
  @Expose
  @SerializedName("store_id")
  private String store_id;
  @Expose
  @SerializedName("cityId")
  private String cityId;
  @Expose
  @SerializedName("unitPrice")
  private float unitPrice;
  @Expose
  @SerializedName("taxAmount")
  private float taxAmount;

  public ApplyPromoProductRequest() {
  }

  protected ApplyPromoProductRequest(Parcel in) {
    product_id = in.readString();
    name = in.readString();
    brand_name = in.readString();
    price = in.readFloat();
    delivery_fee = in.readFloat();
    category_id = in.createStringArrayList();
    centralproduct_id = in.readString();
    tax = in.createStringArrayList();
    quantity = in.readParcelable(QuantityDetails.class.getClassLoader());
    store_id = in.readString();
    cityId = in.readString();
    unitPrice = in.readFloat();
    taxAmount = in.readFloat();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(product_id);
    dest.writeString(name);
    dest.writeString(brand_name);
    dest.writeFloat(price);
    dest.writeFloat(delivery_fee);
    dest.writeStringList(category_id);
    dest.writeString(centralproduct_id);
    dest.writeStringList(tax);
    dest.writeParcelable(quantity, flags);
    dest.writeString(store_id);
    dest.writeString(cityId);
    dest.writeFloat(unitPrice);
    dest.writeFloat(taxAmount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ApplyPromoProductRequest> CREATOR =
      new Creator<ApplyPromoProductRequest>() {
        @Override
        public ApplyPromoProductRequest createFromParcel(Parcel in) {
          return new ApplyPromoProductRequest(in);
        }

        @Override
        public ApplyPromoProductRequest[] newArray(int size) {
          return new ApplyPromoProductRequest[size];
        }
      };

  public String getProduct_id() {
    return product_id;
  }

  public void setProduct_id(String product_id) {
    this.product_id = product_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand_name() {
    return brand_name;
  }

  public void setBrand_name(String brand_name) {
    this.brand_name = brand_name;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public float getDelivery_fee() {
    return delivery_fee;
  }

  public void setDelivery_fee(int delivery_fee) {
    this.delivery_fee = delivery_fee;
  }



  public ArrayList<String> getCategory_id() {
    return category_id;
  }

  public void setCategory_id(ArrayList<String> category_id) {
    this.category_id = category_id;
  }

  public String getCentralproduct_id() {
    return centralproduct_id;
  }

  public void setCentralproduct_id(String centralproduct_id) {
    this.centralproduct_id = centralproduct_id;
  }

  public ArrayList<String> getTax() {
    return tax;
  }

  public void setTax(ArrayList<String> tax) {
    this.tax = tax;
  }

  public QuantityDetails getQuantity() {
    return quantity;
  }

  public void setQuantity(QuantityDetails quantity) {
    this.quantity = quantity;
  }

  public String getStore_id() {
    return store_id;
  }

  public void setStore_id(String store_id) {
    this.store_id = store_id;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public float getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(float unitPrice) {
    this.unitPrice = unitPrice;
  }

  public float getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(float taxAmount) {
    this.taxAmount = taxAmount;
  }

  public void setDelivery_fee(float delivery_fee) {
    this.delivery_fee = delivery_fee;
  }

}
