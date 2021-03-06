package com.customer.remote.http.model.response.getcart;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CartAccountingDetails implements Parcelable {
  @Expose
  @SerializedName("unitPrice")
  private String unitPrice;
  @Expose
  @SerializedName("offerDiscount")
  private String offerDiscount;
  @Expose
  @SerializedName("taxableAmount")
  private String taxableAmount;
  @Expose
  @SerializedName("deliveryFee")
  private String deliveryFee;
  @Expose
  @SerializedName("finalUnitPrice")
  private String finalUnitPrice;
  @Expose
  @SerializedName("addOnsAmount")
  private String addOnsAmount;
  @Expose
  @SerializedName("tax")
  private ArrayList<CartTaxDetails> tax;
  @Expose
  @SerializedName("subTotal")
  private String subTotal;
  @Expose
  @SerializedName("taxAmount")
  private String taxAmount;
  @Expose
  @SerializedName("promoDiscount")
  private String promoDiscount;
  @Expose
  @SerializedName("finalTotal")
  private String finalTotal;

  public CartAccountingDetails(String unitPrice, String offerDiscount, String taxableAmount,
      String deliveryFee, String finalUnitPrice, String addOnsAmount,
      ArrayList<CartTaxDetails> tax, String subTotal, String taxAmount,
      String promoDiscount, String finalTotal) {
    this.unitPrice = unitPrice;
    this.offerDiscount = offerDiscount;
    this.taxableAmount = taxableAmount;
    this.deliveryFee = deliveryFee;
    this.finalUnitPrice = finalUnitPrice;
    this.addOnsAmount = addOnsAmount;
    this.tax = tax;
    this.subTotal = subTotal;
    this.taxAmount = taxAmount;
    this.promoDiscount = promoDiscount;
    this.finalTotal = finalTotal;
  }

  protected CartAccountingDetails(Parcel in) {
    unitPrice = in.readString();
    offerDiscount = in.readString();
    taxableAmount = in.readString();
    deliveryFee = in.readString();
    finalUnitPrice = in.readString();
    addOnsAmount = in.readString();
    tax = in.createTypedArrayList(CartTaxDetails.CREATOR);
    subTotal = in.readString();
    taxAmount = in.readString();
    promoDiscount = in.readString();
    finalTotal = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(unitPrice);
    dest.writeString(offerDiscount);
    dest.writeString(taxableAmount);
    dest.writeString(deliveryFee);
    dest.writeString(finalUnitPrice);
    dest.writeString(addOnsAmount);
    dest.writeTypedList(tax);
    dest.writeString(subTotal);
    dest.writeString(taxAmount);
    dest.writeString(promoDiscount);
    dest.writeString(finalTotal);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CartAccountingDetails> CREATOR =
      new Creator<CartAccountingDetails>() {
        @Override
        public CartAccountingDetails createFromParcel(Parcel in) {
          return new CartAccountingDetails(in);
        }

        @Override
        public CartAccountingDetails[] newArray(int size) {
          return new CartAccountingDetails[size];
        }
      };

  public String getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getOfferDiscount() {
    return offerDiscount;
  }

  public void setOfferDiscount(String offerDiscount) {
    this.offerDiscount = offerDiscount;
  }

  public String getTaxableAmount() {
    return taxableAmount;
  }

  public void setTaxableAmount(String taxableAmount) {
    this.taxableAmount = taxableAmount;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public String getFinalUnitPrice() {
    return finalUnitPrice;
  }

  public void setFinalUnitPrice(String finalUnitPrice) {
    this.finalUnitPrice = finalUnitPrice;
  }

  public String getAddOnsAmount() {
    return addOnsAmount;
  }

  public void setAddOnsAmount(String addOnsAmount) {
    this.addOnsAmount = addOnsAmount;
  }

  public ArrayList<CartTaxDetails> getTax() {
    return tax;
  }

  public void setTax(ArrayList<CartTaxDetails> tax) {
    this.tax = tax;
  }

  public String getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(String subTotal) {
    this.subTotal = subTotal;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getPromoDiscount() {
    return promoDiscount;
  }

  public void setPromoDiscount(String promoDiscount) {
    this.promoDiscount = promoDiscount;
  }

  public String getFinalTotal() {
    return finalTotal;
  }

  public void setFinalTotal(String finalTotal) {
    this.finalTotal = finalTotal;
  }
}
