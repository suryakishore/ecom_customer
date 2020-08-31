package com.customer.remote.http.model.response.newHome;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeOfferDetails implements ValidItem, Parcelable {
  @SerializedName("discountValue")
  @Expose
  private double discountValue;

  @SerializedName("startDateTimeISO")
  @Expose
  private String startDateTimeISO;

  @SerializedName("offerName")
  @Expose
  private String offerName;

  @SerializedName("offerId")
  @Expose
  private String offerId;

  @SerializedName("mobimage")
  @Expose
  private String mobimage;

  public HomeOfferDetails(double discountValue, String startDateTimeISO,
      String offerName, String mobimage, String offerId) {
    this.discountValue = discountValue;
    this.startDateTimeISO = startDateTimeISO;
    this.offerName = offerName;
    this.mobimage = mobimage;
    this.offerId = offerId;
  }

  protected HomeOfferDetails(Parcel in) {
    discountValue = in.readDouble();
    startDateTimeISO = in.readString();
    offerName = in.readString();
    offerId = in.readString();
    mobimage = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(discountValue);
    dest.writeString(startDateTimeISO);
    dest.writeString(offerName);
    dest.writeString(offerId);
    dest.writeString(mobimage);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<HomeOfferDetails> CREATOR = new Creator<HomeOfferDetails>() {
    @Override
    public HomeOfferDetails createFromParcel(Parcel in) {
      return new HomeOfferDetails(in);
    }

    @Override
    public HomeOfferDetails[] newArray(int size) {
      return new HomeOfferDetails[size];
    }
  };

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public double getDiscountValue() {
    return discountValue;
  }

  public void setDiscountValue(double discountValue) {
    this.discountValue = discountValue;
  }

  public String getStartDateTimeISO() {
    return startDateTimeISO;
  }

  public void setStartDateTimeISO(String startDateTimeISO) {
    this.startDateTimeISO = startDateTimeISO;
  }

  public String getOfferName() {
    return offerName;
  }

  public void setOfferName(String offerName) {
    this.offerName = offerName;
  }

  public String getMobimage() {
    return mobimage;
  }

  public void setMobimage(String mobimage) {
    this.mobimage = mobimage;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
